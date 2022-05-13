package ch.zhaw.graphy;

import ch.zhaw.graphy.Algorithms.BreadthFirstSearch;
import ch.zhaw.graphy.Algorithms.Dijkstra;
import ch.zhaw.graphy.Algorithms.MinimumSpanningTree;
import ch.zhaw.graphy.Graph.Edge;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private static int numberOfDrawnUnnamedVertex = 0;
    private Stage oldStage;
    private Color stdVertexColor = Color.RED;
    private static final Color stdVertexSelectedColor = Color.BLUE;
    private static final Color stdLineColor = Color.LIGHTGRAY;
    private static final Color stdLineSelectedColor = Color.LIGHTBLUE;

    private static final int VERTEX_SIZE = 12;

    @FXML
    private Pane paintArea;
    @FXML
    private TextField fileName;
    @FXML
    private MenuButton algorithmSelectionMenu;
    @FXML
    private Button addEdge;
    @FXML
    private Label feedBackLabel;
    @FXML
    private Button colorize;
    @FXML
    private Button clearAll;
    @FXML
    private Button close;
    @FXML
    private TextField edgeWeight;
    @FXML
    private MenuItem executeDijkstra;
    @FXML
    private MenuItem executeSpanningTree;
    @FXML
    private Button printToCsv;
    @FXML
    private Button remove;
    @FXML
    private Label selectAlgorithm;
    @FXML
    private MenuItem showHelp;
    @FXML
    private TextField vertexName;
    @FXML
    private CheckBox bidirectional;
    @FXML
    private CheckBox selectMode;

    private MainWindowModel model;

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
            handler = new GraphHandler();
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
            handler = new GraphHandler(file);
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
        model = new MainWindowModel(handler);
        model.registerVertexListener(vertexListener);
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
        model.clear();
        paintArea.getChildren().clear();
        handler.getGraph().clear();
        edgeGuiBiMap.clear();
        vertexGuiBiMap.clear();
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
     * @param event
     */
    @FXML
    void executeBfs(ActionEvent event) {
        if (model.getSelectedVertex().isEmpty() && model.getSelectedEdge().isEmpty()){
            feedBackLabel.setStyle("-fx-text-fill: red");
            feedBackLabel.setText("No source vertex has been selected");
        }
        algorithmSelectionMenu.setText("BFS");
        if (!model.hasSelectedVertex()) {
            throw new IllegalArgumentException("Pls select a vertex to start from");
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
        vertexGuiBiMap.inverse().get(model.getSelectedVertex().get(0)).setColor(Color.PURPLE);
        feedBackLabel.setText("BFS successful with " + bfs.getVisualMap().size() + " steps");
    }

    /**
     * Executes the dijkstra algorithm on the graph from the selected start vertex.
     * 
     * @param event
     */
    @FXML
    void executeDijkstra(ActionEvent event) {
        int weightCounter =0;
        if (model.getSelectedVertex().isEmpty() && model.getSelectedEdge().isEmpty()){
            feedBackLabel.setStyle("-fx-text-fill: red");
            feedBackLabel.setText("Select 2 vertices for dijkstra");
        }
        algorithmSelectionMenu.setText("Dijkstra");
        Dijkstra dijkstra = new Dijkstra();

        Map<Vertex,Vertex> path = dijkstra.executeDijkstra(handler, model.getSelectedVertex().get(0), model.getSelectedVertex().get(1));

        for (Vertex vertex : path.keySet()){
            vertexGuiBiMap.inverse().get(vertex).setColor(Color.ORANGE);
            for (Edge edge : edgeGuiBiMap.inverse().keySet()){
                if (edge.getEnd().equals(vertex) && edge.getStart().equals(path.get(vertex))){
                    changeEdgeColor(edge, Color.GREEN);
                    weightCounter+=edge.getWeight();
                }
            }
        }
        feedBackLabel.setText("Minimum path cost is: " + weightCounter);
        }



    /**
     * Executes the spanning tree algorithm on the graph from the selected start
     * vertex.
     * 
     * @param event
     */
    @FXML
    void executeSpanningTree(ActionEvent event) {
        if (model.getSelectedVertex().isEmpty() && model.getSelectedEdge().isEmpty()){
            feedBackLabel.setStyle("-fx-text-fill: red");
            feedBackLabel.setText("No source vertex has been selected");
        }
        algorithmSelectionMenu.setText("Spanning Tree");
        MinimumSpanningTree mst = new MinimumSpanningTree(new BreadthFirstSearch());
        Set<Edge> chosenEdges = mst.executeMST(handler, model.getSelectedVertex().get(0));
        for (Edge mstEdge : chosenEdges){
            for (Edge lineEdge : edgeGuiBiMap.inverse().keySet()){
                if (mstEdge.equals(lineEdge)){
                    changeEdgeColor(lineEdge, Color.GREEN);
                }
            }
        }
        feedBackLabel.setText("MST needs " + chosenEdges.size() + " edges to reach all vertices");
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
        clearAll(event);
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

    /**
     * Changes the color of all edges.
     * 
     * @param color the new color to be set
     */
    private void changeEdgeColor(Color color){
        for (EdgeGui edgeGui : edgeGuiBiMap.keySet()){
            edgeGui.setColor(color);
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

    private MainWindowModel.VertexListener vertexListener = new MainWindowModel.VertexListener() {
        @Override
        public void onAddVertex(Vertex newVertex) {
            createVertex(newVertex);
        }

        @Override
        public void onSelectVertex(Vertex selectedVertex) {
            changeVertexColor(selectedVertex, stdVertexSelectedColor);
        }

        @Override
        public void onClearVertex() {
            clearPaintArea();
        }

        @Override
        public void onClearSelectedVertex() {
            changeVertexColor(stdVertexColor);
            changeEdgeColor(Color.BLACK);
        }

        @Override
        public void onAddEdge(Edge newEdge){
            createEdge(newEdge);
        }

        @Override
        public void onSelectEdge(Edge changeEdge) {
            changeEdgeColor(changeEdge, stdLineSelectedColor);
        }

        @Override
        public void onClearSelectedEdge() {
            changeEdgeColor(stdLineColor);
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
                handler.getGraph().remove(vertex);
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
        paintArea.getChildren().removeAll();
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
                        else {

                            if(!selectMode.isSelected()) {
                                createVertex(new Point((int) event.getX(), (int) event.getY()));
                            }
                      }
                    } else {
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

    private ClickAction clickAction = ClickAction.PaintAreaClick;

    /**
     * Contains the different types of click actions.
     */
    private enum ClickAction {
        PaintAreaClick,
        VertexClick,
        EdgeClick
    }

    /**
     * Creates a new vertex at the given position.
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

    private void createVertex(Vertex vertex){
        VertexGui vertexGui = new VertexGui(vertex, vertexClick);
        vertexGuiBiMap.put(vertexGui, vertex);
        paintArea.getChildren().addAll(vertexGui.getNodes());
    }
    private void createEdge(Edge edge){
        EdgeGui edgeGui = new EdgeGui(edge, edgeClick);
        edgeGuiBiMap.put(edgeGui, edge);
        paintArea.getChildren().addAll(0, edgeGui.getNodes());
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