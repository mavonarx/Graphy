package ch.zhaw.graphy;

import ch.zhaw.graphy.Algorithms.BreadthFirstSearch;
import ch.zhaw.graphy.Algorithms.Dijkstra;
import ch.zhaw.graphy.Algorithms.MinimumSpanningTree;
import ch.zhaw.graphy.Graph.Edge;

import java.io.File;
import java.io.IOException;
import java.util.*;

import ch.zhaw.graphy.Graph.GraphHandler;
import ch.zhaw.graphy.Graph.Point;
import ch.zhaw.graphy.Graph.Vertex;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This class is responsible for the logic in the MainWindow.
 * It listens to changes from the gui and the model and applies its logic and
 * finally updates
 * the gui or the model.
 * 
 * @author Tanja Aeberhardt, Nicolas Balke, Lukas Gruber, Matthias von Arx
 * @version 13.05.2022
 */
public class MainWindowController {

    GraphHandler handler;
    private BiMap<EdgeGui, Edge> edgeGuiBiMap = HashBiMap.create();
    private BiMap<VertexGui, Vertex> vertexGuiBiMap = HashBiMap.create();
    private Stage stage;
    private Stage oldStage;
    private static int numberOfDrawnUnnamedVertex = 0;
    private ClickAction clickAction = ClickAction.PaintAreaClick;
    private MainWindowModel model;

    @FXML
    private Pane paintArea;
    @FXML
    private TextField fileName;
    @FXML
    private MenuButton algorithmSelectionMenu;
    @FXML
    private Label feedBackLabel;
    @FXML
    private Button clearAll;
    @FXML
    private TextField edgeWeight;
    @FXML
    private TextField vertexName;
    @FXML
    private CheckBox bidirectional;
    @FXML
    private CheckBox selectMode;

    /**
     * Constructor for MainWindowController. Fills in the scene. Sets up, configures
     * and shows the stage.
     * 
     * @param oldStage given stage
     */
    public MainWindowController(Stage oldStage) {
        this.oldStage = oldStage;
        try {
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/ch/zhaw/graphy/MainWindow.fxml"));
            model = new MainWindowModel();
            model.registerListener(modelListener);
            handler = new GraphHandler(model);
            mainLoader.setController(this);
            Stage mainStage = new Stage();
            Pane rootNode = mainLoader.load();
            Scene scene = new Scene(rootNode);
            mainStage.setScene(scene);
            mainStage.setMinWidth(280);
            mainStage.setMinHeight(250);
            this.stage = mainStage;
            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor for MainWindowController if a file is given. Fills in the scene.
     * Sets up, configures and shows the stage.
     * 
     * @param oldStage given stage
     * @param file     given file
     */
    public MainWindowController(Stage oldStage, File file) {
        this.oldStage = oldStage;
        try {
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/ch/zhaw/graphy/MainWindow.fxml"));
            model = new MainWindowModel();
            model.registerListener(modelListener);
            handler = new GraphHandler(model, file);
            mainLoader.setController(this);
            Stage mainStage = new Stage();
            Pane rootNode = mainLoader.load();
            Scene scene = new Scene(rootNode);
            mainStage.setScene(scene);
            mainStage.setMinWidth(280);
            mainStage.setMinHeight(250);
            this.stage = mainStage;
            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Vertex vertex: handler.getGraph().keySet()){
            createVertex(vertex);
            for (Edge edge : handler.getGraph().get(vertex)){
                createEdge(edge);
            }
        }
        numberOfDrawnUnnamedVertex = handler.getGraph().size();
    }

    /**
     * Called to initialize the MainWindowController after its root element has been
     * completely processed.
     */
    @FXML
    void initialize() {
        algorithmSelectionMenu.setText("Choose Algorithm");
        paintArea.setOnMouseClicked(paintAreaClick);
        clearAll.setOnMouseClicked(clearAllClick);

        // force the field to be numeric only
        edgeWeight.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    edgeWeight.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    /**
     * Colorizes all edges from red(large weight) to green(small weight) depending
     * on the weight.
     * 
     * @param event
     */
    @FXML
    void colorize(ActionEvent event) {
        int min = 0;
        int max = 0;

        for (Edge edge : edgeGuiBiMap.inverse().keySet()){
            if (edge.getWeight() > max){
                max = edge.getWeight();
            }
            if (edge.getWeight() < min) {
                min = edge.getWeight();
            }
        }

        double difference = (max-min);
        for (Edge edge : edgeGuiBiMap.inverse().keySet()){
            double percent = edge.getWeight()/difference;
            changeEdgeColor(edge, Color.rgb((int) Math.round(255*percent), (int) Math.round(255-255*percent), 0));
        }
    }

    /**
     * Clears the all parts in the paintArea from the gui and the logic.
     * 
     * @param event
     */
    @FXML
    void clearAll(ActionEvent event) {
        clearAlLMethod();

    }

    private void clearAlLMethod(){
        numberOfDrawnUnnamedVertex = 0;
        model.clear();
    }

    /**
     * Closes the MainWindow.
     * 
     * @param event
     */
    @FXML
    void close(ActionEvent event) {
        stage.close();
    }

    /**
     * Executes the bfs algorithm on the graph from the selected start vertex.
     * 
     * @param event on BFS button press
     */
    @FXML
    void executeBfs(ActionEvent event) {
        algorithmSelectionMenu.setText("BFS");
        if (model.getSelectedVertex().size() != 1){
            feedBackLabel.setStyle("-fx-text-fill: red");
            feedBackLabel.setText("Select 1 vertex for BFS");
            return;
        }

        BreadthFirstSearch bfs = new BreadthFirstSearch();
        bfs.executeBFS(handler, model.getSelectedVertex().get(0));
        for (Vertex vertex : bfs.getVisualMap().keySet()){
            vertexGuiBiMap.inverse().get(vertex).setColor(Color.ORANGE);
            for (Edge edge : edgeGuiBiMap.inverse().keySet()){
                if (edge.getEnd().equals(vertex) && edge.getStart().equals(bfs.getVisualMap().get(vertex))){
                    changeEdgeColor(edge, Color.GREEN);
                }
            }
        }
        model.getSelectedVertex().clear();
        vertexGuiBiMap.inverse().get(model.getSelectedVertex().get(0)).setColor(Color.PURPLE);
        feedBackLabel.setStyle("-fx-text-fill: black");
        feedBackLabel.setText("BFS successful with " + bfs.getVisualMap().size() + " steps");
    }

    /**
     * Executes the dijkstra algorithm on the graph from the selected start vertex.
     * 
     * @param event on dijkstra button press
     */
    @FXML
    void executeDijkstra(ActionEvent event) {
        int weightCounter =0;
        if (model.getSelectedVertex().size() != 2){
            feedBackLabel.setStyle("-fx-text-fill: red");
            feedBackLabel.setText("Select 2 vertices for dijkstra");
            return;
        }
        algorithmSelectionMenu.setText("Dijkstra");
        Dijkstra dijkstra = new Dijkstra();

        Map<Vertex,Vertex> path = dijkstra.executeDijkstra(handler, model.getSelectedVertex().get(0), model.getSelectedVertex().get(1));

        weightCounter = changePathColor(weightCounter, path);
        model.getSelectedVertex().clear();
        feedBackLabel.setStyle("-fx-text-fill: black");
        feedBackLabel.setText("Minimum path cost is: " + weightCounter);
        }



    /**
     * Executes the spanning tree algorithm on the graph from the selected start
     * vertex.
     * 
     * @param event on MST button press
     */
    @FXML
    void executeSpanningTree(ActionEvent event) {
        if (model.getSelectedVertex().size() != 1){
            feedBackLabel.setStyle("-fx-text-fill: red");
            feedBackLabel.setText("Select 1 vertex for MST");
            return;
        }
        algorithmSelectionMenu.setText("Spanning Tree");
        MinimumSpanningTree mst = new MinimumSpanningTree(new BreadthFirstSearch());
        Set<Edge> chosenEdges = new HashSet<>();
        try {
            chosenEdges = mst.executeMST(handler, model.getSelectedVertex().get(0));
        }catch (IllegalArgumentException e){
            feedBackLabel.setStyle("-fx-text-fill: red");
            feedBackLabel.setText("The graph isn't connected to the source");
            return;
        }
        for (Edge mstEdge : chosenEdges){
            for (Edge lineEdge : edgeGuiBiMap.inverse().keySet()){
                if (mstEdge.equals(lineEdge)){
                    changeEdgeColor(lineEdge, Color.GREEN);
                }
            }
        }
        model.getSelectedVertex().clear();
        feedBackLabel.setStyle("-fx-text-fill: black");
        feedBackLabel.setText("MST needs " + chosenEdges.size() + " edges to reach all vertices");
    }

    /**
     * Uses dijkstra twice to get from a source vertex (first in {model.getSelectedVertex})
     * to a destination vertex (third in {model.getSelectedVertex}) via
     * a pass through vertex (second in {model.getSelectedVertex})
     *
     * @param event on dijkstraVia button press
     */
    @FXML
    void executeDijkstraVia(ActionEvent event){
        int weightCounter =0;
        if (model.getSelectedVertex().size() != 3){
            feedBackLabel.setStyle("-fx-text-fill: red");
            feedBackLabel.setText("Select 3 vertices for dijkstra-Via");
            return;
        }
        algorithmSelectionMenu.setText("Dijkstra");
        Dijkstra dijkstra = new Dijkstra();
        Map<Vertex,Vertex> path = dijkstra.executeDijkstra(handler, model.getSelectedVertex().get(0), model.getSelectedVertex().get(1));

        weightCounter = changePathColor(weightCounter, path);
        path = dijkstra.executeDijkstra(handler, model.getSelectedVertex().get(1), model.getSelectedVertex().get(2));

        weightCounter = changePathColor(weightCounter, path);
        changeVertexColor(model.getSelectedVertex().get(1), Color. GREY);
        feedBackLabel.setText("Minimum path cost is: " + weightCounter);
    }

    /**
     * Helper method for the dijkstra buttons to reduce code multiplication
     *
     * @param weightCounter a counter the accumulated weights
     * @param path a map created by a dijkstra
     * @return this int is the new weight counter in the buttons
     */
    private int changePathColor(int weightCounter, Map<Vertex, Vertex> path) {
        for (Vertex vertex : path.keySet()){
            vertexGuiBiMap.inverse().get(vertex).setColor(Color.ORANGE);
            for (Edge edge : edgeGuiBiMap.inverse().keySet()){
                if (edge.getEnd().equals(vertex) && edge.getStart().equals(path.get(vertex))){
                    changeEdgeColor(edge, Color.GREEN);
                    weightCounter+=edge.getWeight();
                }
            }
        }
        return weightCounter;
    }

    /**
     * Prints the drawn graph to a CSV-File.
     * 
     * @param event
     */
    @FXML
    void printToCsv(ActionEvent event) {
        try {
            giveFeedback(handler.convertToCSV(fileName.getText()));
        } catch (IOException e) {
            e.printStackTrace();
            feedBackLabel.setStyle("-fx-text-fill: red");
            feedBackLabel.setText("an Exception has occurred");
        }
    }

    /**
     * Gives a feedback if file has been saved or not.
     * 
     * @param isSavedAsCSV true, if there is a graph to print
     */
    private void giveFeedback(boolean isSavedAsCSV) {
        if (isSavedAsCSV) {
            feedBackLabel.setStyle("-fx-text-fill: green");
            feedBackLabel.setText("File has been saved in the output directory");
        } else {
            feedBackLabel.setStyle("-fx-text-fill: red");
            feedBackLabel.setText("File not saved because the graph is empty");
        }
    }

    /**
     * Goes back to the PreWindow and closes the MainWindow.
     * 
     * @param event
     */
    @FXML
    void backToStart(ActionEvent event) {
        clearAlLMethod();
        PreWindowController preWindowController = new PreWindowController(oldStage);
        preWindowController.getStage().show();
        close(event);
    }

    /**
     * Removes the selected vertices and edges from the paint area.
     * 
     * @param event
     */
    @FXML
    void remove(ActionEvent event) {
        if (model.getSelectedVertex().isEmpty() && model.getSelectedEdge().isEmpty()){
            feedBackLabel.setStyle("-fx-text-fill: blue");
            feedBackLabel.setText("No vertex or Edge has been selected");
        }
        model.removeSelectedDisplayVertex();
        model.removeSelectedDisplayEdge();
    }

    /**
     * Opens HelpWindow and shows helping informations.
     * 
     * @param event
     */
    @FXML
    void showHelp(ActionEvent event) {
        HelpWindowController helpWindowController = new HelpWindowController(false);
        helpWindowController.getStage().show();
    }

    /**
     * Changes the color of a vertex.
     * 
     * @param changeVertex the vertex to be changed
     * @param color        the new color to be set
     */
    private void changeVertexColor(Vertex changeVertex, Color color){
        VertexGui vertexGui = vertexGuiBiMap.inverse().get(changeVertex);
        vertexGui.setColor(color);
    }

    /**
     * Changes the color of an edge.
     * 
     * @param changeEdge the edge to be changed
     * @param color      the new color to be set
     */
    private void changeEdgeColor(Edge changeEdge, Color color){
        EdgeGui edgeGui =  edgeGuiBiMap.inverse().get(changeEdge);
        edgeGui.setColor(color);
    }
    private void setVertexColorStd(){
        for (VertexGui vertexGui : vertexGuiBiMap.keySet()){
            vertexGui.setStdColor();
        }
    }
    private void setEdgeColorStd(){
        for (EdgeGui edgeGui : edgeGuiBiMap.keySet()){
            edgeGui.setStdColor();
        }
    }

    /**
     * Changes the color of all vertices.
     * 
     * @param color the new color to be set
     */
    private void changeVertexColor(Color color){
        for (VertexGui vertexGui : vertexGuiBiMap.keySet()){
            vertexGui.setColor(color);
        }
    }

    /**
     * Creates a new vertex at the given position.
     *
     * @param position given position
     */
    private void createVertex(Point position) {
        String name = vertexName.getText();
        if (vertexName.getText().isBlank()) {
            name = String.valueOf(++numberOfDrawnUnnamedVertex);
        }
        Vertex newVertex = new Vertex(name, position);
        model.addDisplayVertex(newVertex);
    }

    /**
     * Creates a given vertex on the canvas
     *
     * @param vertex the given vertex
     */
    private void createVertex(Vertex vertex){
        VertexGui vertexGui = new VertexGui(vertex, vertexClick);
        vertexGuiBiMap.put(vertexGui, vertex);
        paintArea.getChildren().addAll(vertexGui.getNodes());
    }

    /**
     * Creates a given edge on the canvas
     *
     * @param edge the given edge
     */
    private void createEdge(Edge edge){
        EdgeGui edgeGui = new EdgeGui(edge, edgeClick);
        edgeGuiBiMap.put(edgeGui, edge);
        paintArea.getChildren().addAll(0, edgeGui.getNodes());
    }
  
    private MainWindowModel.MainWindowModelListener modelListener = new MainWindowModel.MainWindowModelListener() {
        @Override
        public void onAddVertex(Vertex newVertex) {
            createVertex(newVertex);
        }

        @Override
        public void onSelectVertex(Vertex selectedVertex) {
            vertexGuiBiMap.inverse().get(selectedVertex).setSelectedColor();
        }

        @Override
        public void onClearVertex() {
            clearPaintArea();
            vertexGuiBiMap.clear();
            edgeGuiBiMap.clear();
        }

        @Override
        public void onClearSelectedVertex() {
            setVertexColorStd();
            setEdgeColorStd();
        }

        @Override
        public void onAddEdge(Edge newEdge){
            createEdge(newEdge);
        }

        @Override
        public void onSelectEdge(Edge changeEdge) {
            edgeGuiBiMap.inverse().get(changeEdge).setSelectedColor();
        }

        @Override
        public void onClearSelectedEdge() {
            setEdgeColorStd();
        }

        @Override
        public void onRemoveSelectedEdge(List<Edge> selectedEdges) {
            for (Edge edge : selectedEdges) {
                EdgeGui edgeGui = edgeGuiBiMap.inverse().get(edge);
                paintArea.getChildren().removeAll(edgeGui.getNodes());
                edgeGuiBiMap.remove(edgeGui, edge);
                for (Vertex vertex : handler.getGraph().keySet()){
                    for (Edge graphEdge : handler.getGraph().get(vertex)){
                        if (edge.equals(graphEdge)){
                            handler.getGraph().get(vertex).remove(graphEdge);
                        }
                    }
                }
            }
        }

        @Override
        public void onRemoveSelectedVertex(List<Vertex> selectedVertex) {
            for (Vertex vertex : selectedVertex) {
                VertexGui vertexGui = vertexGuiBiMap.inverse().get(vertex);
                paintArea.getChildren().removeAll(vertexGui.getNodes());
                vertexGuiBiMap.remove(vertexGui, vertex);
                Iterator<Edge> edgeIterator = edgeGuiBiMap.inverse().keySet().iterator();
                while (edgeIterator.hasNext()){
                    Edge edge = edgeIterator.next();
                    if (edge.getEnd().equals(vertex) || edge.getStart().equals(vertex)){
                        paintArea.getChildren().removeAll(edgeGuiBiMap.inverse().get(edge).getNodes());
                        edgeIterator.remove();
                    }
                }
            }
        }
    };

    /**
     * Clears all visual parts in the paint area.
     */
    private void clearPaintArea() {
        paintArea.getChildren().clear();
    }

    /**
     * Checks if a GraphGuiObject is colored.
     *
     * @return true if at least one GraphGuiObject of the GUI is not colored standart color.
     */
    private Boolean isGraphColored(){
        for (VertexGui vertex : vertexGuiBiMap.keySet()) {
            if(vertex.isColored()){
                return true;
            }
        }
        for (EdgeGui edge : edgeGuiBiMap.keySet()){
            if(edge.isColored()){
                return true;
            }
        }
        return false;
    }

    /**
     * Invoked when a mouse click to clear everything happens.
     */
    private EventHandler<MouseEvent> clearAllClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            model.clearDisplayVertex();
            numberOfDrawnUnnamedVertex=0;
        }
    };

    /**
     * Invoked when a mouse click in the paint area happens.
     */
    private EventHandler<MouseEvent> paintAreaClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.getX() < 0 || event.getY() < 0) {
                return;
            }
            switch (clickAction) {
                case PaintAreaClick:
                    if (model.hasSelectedVertex() || model.hasSelectedEdge()) {
                        if (model.hasSelectedVertex()) {
                            model.clearSelectedVertex();
                        }
                        if (model.hasSelectedEdge()) {
                            model.clearSelectedEdge();
                        }
                    }
                    else if(isGraphColored()){
                        setVertexColorStd();
                        setEdgeColorStd();
                    }
                    else {
                        if (!selectMode.isSelected()) {
                            createVertex(new Point((int) event.getX(), (int) event.getY()));
                        }
                    }
                    break;
                default:
                    clickAction = ClickAction.PaintAreaClick;
                    break;
            }
        }
    };

    /**
     * Invoked when a mouse click on a vertex happens.
     */
    private VertexClickEvent vertexClick = new VertexClickEvent() {
        @Override
        public void handle(VertexGui vertexGui) {
            clickAction = ClickAction.VertexClick;
                switch (model.getSelectedVertex().size()){
                    case 0:
                        model.addSelectedVertex(vertexGuiBiMap.get(vertexGui));
                        break;
                    case 1:
                        if(model.getSelectedVertex().get(0) != vertexGuiBiMap.get(vertexGui)){
                            model.addSelectedVertex(vertexGuiBiMap.get(vertexGui));
                            int weight;
                            if ("".equals(edgeWeight.getText())){
                                weight = 0;
                            } else {
                                try {
                                    weight = Integer.parseInt(edgeWeight.getText());
                                } catch (NumberFormatException e) {
                                    weight = 0;
                                }
                            }
                            if (!selectMode.isSelected()) {
                                Edge newEdge = new Edge(model.getSelectedVertex().get(0),
                                        model.getSelectedVertex().get(1), weight);

                                model.addDisplayEdge(newEdge);
                                if (bidirectional.isSelected()) {
                                    newEdge = new Edge(model.getSelectedVertex().get(1),
                                            model.getSelectedVertex().get(0), weight);
                                    model.addDisplayEdge(newEdge);
                                }
                                model.clearSelectedVertex();
                            }
                        }
                        break;
                    default: model.addSelectedVertex(vertexGuiBiMap.get(vertexGui));
                }
            }
    };

     /**
     * Invoked when a mouse click on an edge happens.
     */
    private EdgeClickEvent edgeClick = new EdgeClickEvent() {
        @Override
        public void handle(EdgeGui edge) {
            clickAction = ClickAction.EdgeClick;
            model.addSelectedEdge(edgeGuiBiMap.get(edge));
        }
    };

    /**
     * Contains the different types of click actions.
     */
    private enum ClickAction {
        PaintAreaClick,
        VertexClick,
        EdgeClick
    }

    /**
     * Returns the stage of the MainWindow.
     * 
     * @return current stage
     */
    public Stage getStage(){
        return stage;
    }
}