package ch.zhaw.graphy;

import ch.zhaw.graphy.Algorithms.BreadthFirstSearch;
import ch.zhaw.graphy.Algorithms.Dijkstra;
import ch.zhaw.graphy.Algorithms.MinimumSpanningTree;
import ch.zhaw.graphy.Graph.Edge;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MainWindowController {

    GraphHandler handler;

    private Stage stage;
    private static int numberOfDrawnUnnamedVertex = 0;
    private Stage oldStage;

    private Color stdVertexColor = Color.RED;
    private static final Color stdVertexSelectedColor = Color.BLUE;
    private static final Color stdLineColor = Color.LIGHTGRAY;
    private static final Color stdLineSelectedColor = Color.LIGHTBLUE;

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
    public MainWindowController(Stage oldStage){
        this.oldStage = oldStage;
        try{
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
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    public MainWindowController(Stage oldStage, File file){
        this.oldStage=oldStage;
        try{
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
        } catch(Exception e){
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

    @FXML
    void initialize(){
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

    @FXML
    void addEdge(ActionEvent event) {

    }

    @FXML
    void addEdgeWeight(ActionEvent event) {

    }

    @FXML
    void addVertexName(ActionEvent event) {

    }
    @FXML
    void colorize(ActionEvent event) {
        int min = 0;
        int max = 0;

        for (Edge edge : edgeGuiBiMap.inverse().keySet()){
            if (edge.getWeight() > max){
                max = edge.getWeight();
            }
            if (edge.getWeight() < min){
                min = edge.getWeight();
            }
        }
        double difference = (max-min);
        for (Edge edge : edgeGuiBiMap.inverse().keySet()){
            double percent = edge.getWeight()/difference;
            changeEdgeColor(edge, Color.rgb((int) Math.round(255*percent), (int) Math.round(255-255*percent), 0));
        }
    }

    @FXML
    void clearAll(ActionEvent event) {
        model.clear();
        paintArea.getChildren().clear();
        handler.getGraph().clear();
        edgeGuiBiMap.clear();
        vertexGuiBiMap.clear();
    }


    @FXML
    void close(ActionEvent event) {
        stage.close();
    }


    @FXML
    void executeBfs(ActionEvent event) {
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
    }

    @FXML
    void executeDijkstra(ActionEvent event) {
        algorithmSelectionMenu.setText("Dijkstra");
        Dijkstra dijkstra = new Dijkstra();
        Map<Vertex,Vertex> path = dijkstra.executeDijkstra(handler, model.getSelectedVertex().get(0), model.getSelectedVertex().get(1));


        for (int i =0; i<path.keySet().size();i++){
            for (Vertex vertex : vertexGuiBiMap.inverse().keySet()){
                if (vertex.equals(path.get(i))){
                    changeVertexColor(vertex, Color.ORANGE);
                }
            }
            for (Edge lineEdge : edgeGuiBiMap.inverse().keySet()){
                if (lineEdge.getStart().equals(path.get(i)) && lineEdge.getEnd().equals(path.get(i+1)));
                changeEdgeColor(lineEdge, Color.GREEN);
            }
        }
    }

    @FXML
    void executeSpanningTree(ActionEvent event) {
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
    }

    @FXML
    void printToCsv(ActionEvent event) {
        try {
            giveFeedback(handler.convertToCSV(fileName.getText()));
        }
        catch (IOException e){
            e.printStackTrace();
            feedBackLabel.setStyle("-fx-text-fill: red");
            feedBackLabel.setText("an Exception has occurred");
        }
    }

    private void giveFeedback(boolean isSavedAsCSV){
        if(isSavedAsCSV){
            feedBackLabel.setStyle("-fx-text-fill: green");
            feedBackLabel.setText("File has been saved in the output directory");
        }
       else {
           feedBackLabel.setStyle("-fx-text-fill: red");
           feedBackLabel.setText("File not saved because the graph is empty");
        }
    }

    
    @FXML
    void backToStart(ActionEvent event) {
        clearAll(event);
        PreWindowController preWindowController = new PreWindowController(oldStage);
        preWindowController.getStage().show();
        close(event);
    }

    @FXML
    void remove(ActionEvent event) {
        //first we remove the selected edges from the paint area and from the LineEdgeMap
        for (Edge modelEdge : model.getSelectedEdge()){

            //now we need to remove the edges from the list associated with a vertex
            for (Vertex vertex : handler.getGraph().keySet()){
                for (Edge graphEdge : handler.getGraph().get(vertex)){
                    if (modelEdge.equals(graphEdge)){
                        handler.getGraph().get(vertex).remove(graphEdge);
                    }
                }
            }
        }

        //next we remove the selected vertices
        for (Vertex vertex : model.getSelectedVertex()){
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
        model.removeSelectedDisplayVertex();
        model.removeSelectedDisplayEdge();
    }

    @FXML
    void showHelp(ActionEvent event) {
        HelpWindowController helpWindowController = new HelpWindowController(false);
        helpWindowController.getStage().show();
    }

    private void changeVertexColor(Vertex changeVertex, Color color){
        VertexGui vertexGui = vertexGuiBiMap.inverse().get(changeVertex);
        vertexGui.setColor(color);
    }

    private void changeEdgeColor(Edge changeEdge, Color color){
        EdgeGui edgeGui =  edgeGuiBiMap.inverse().get(changeEdge);
        edgeGui.setColor(color);
    }

    private void changeEdgeColor(Color color){
        for (EdgeGui edgeGui : edgeGuiBiMap.keySet()){
            edgeGui.setColor(color);
        }
    }

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
        }

        @Override
        public void onAddEdge(Edge newEdge){
            createEdge(newEdge);
        }

        @Override
        public void onSelectEdge(Edge changeEdge){
            changeEdgeColor(changeEdge, stdLineSelectedColor);
        }

        @Override
        public void onClearSelectedEdge(){
            changeEdgeColor(stdLineColor);
        }

        @Override
        public void onRemoveSelectedEdge(List<Edge> selectedEdges) {
            for (Edge edge : selectedEdges) {
                EdgeGui edgeGui = edgeGuiBiMap.inverse().get(edge);
                paintArea.getChildren().removeAll(edgeGui.getNodes());
                edgeGuiBiMap.remove(edgeGui, edge);
            }
        }

        @Override
        public void onRemoveSelectedVertex(List<Vertex> selectedVertex) {
            for (Vertex vertex : selectedVertex) {
                VertexGui vertexGui = vertexGuiBiMap.inverse().get(vertex);
                paintArea.getChildren().removeAll(vertexGui.getNodes());
                vertexGuiBiMap.remove(vertexGui, vertex);
            }
        }
    };

    private void clearPaintArea(){
        paintArea.getChildren().removeAll();
    }

    private EventHandler<MouseEvent> clearAllClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            model.clearDisplayVertex();
            numberOfDrawnUnnamedVertex=0;
        }
    };

    private EventHandler<MouseEvent> paintAreaClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(event.getX() < 0 || event.getY() < 0){
                return;
            }
                switch (clickAction){
                    case PaintAreaClick:
                        if(model.hasSelectedVertex() || model.hasSelectedEdge()){
                            if(model.hasSelectedVertex()){
                                model.clearSelectedVertex();
                            }
                            if(model.hasSelectedEdge()){
                                model.clearSelectedEdge();
                            }
                        }
                        else {

                            if(!selectMode.isSelected()) {
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
                                Edge newEdge = new Edge(model.getSelectedVertex().get(0), model.getSelectedVertex().get(1), weight);

                                model.addDisplayEdge(newEdge);
                                if (bidirectional.isSelected()) {
                                    newEdge = new Edge(model.getSelectedVertex().get(1), model.getSelectedVertex().get(0), weight);
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

    private EdgeClickEvent edgeClick = new EdgeClickEvent() {
        @Override
        public void handle(EdgeGui edge) {
            clickAction = ClickAction.EdgeClick;
            model.addSelectedEdge(edgeGuiBiMap.get(edge));
        }
    };

    private ClickAction clickAction = ClickAction.PaintAreaClick;

    private enum ClickAction{
        PaintAreaClick,
        VertexClick,
        EdgeClick
    }

    private void createVertex(Point position){
        String name = vertexName.getText();
        if(vertexName.getText().isBlank()){
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
    public Stage getStage(){
        return stage;
    }

    private BiMap<EdgeGui, Edge> edgeGuiBiMap = HashBiMap.create();
    private BiMap<VertexGui, Vertex> vertexGuiBiMap = HashBiMap.create();
}

