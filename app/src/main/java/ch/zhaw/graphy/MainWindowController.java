package ch.zhaw.graphy;

import ch.zhaw.graphy.Graph.Edge;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import ch.zhaw.graphy.Graph.GraphHandler;
import ch.zhaw.graphy.Graph.Vertex;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class MainWindowController {

    GraphHandler handler;

    private Stage stage;
    private Stage oldStage;

    private Color stdVertexColor = Color.RED;
    private static final Color stdVertexSelectedColor = Color.BLANCHEDALMOND;
    private static final Color stdLineColor = Color.BLACK;
    private static final Color stdLineSelectedColor = Color.AQUA;


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
    }

    @FXML
    private Pane paintArea;

    @FXML
    void initialize(){
        model = new MainWindowModel(handler);
        model.registerVertexListener(vertexListener);
        edgeWeight.setText("Add edge weight");
        vertexName.setText("Add vertex name");
        algorithmSelectionMenu.setText("Choose Algorithm");
        paintArea.setOnMouseClicked(paintAreaClick);
        clearAll.setOnMouseClicked(clearAllClick);
    }

    @FXML
    private MenuButton algorithmSelectionMenu;

    @FXML
    private Button addEdge;

    @FXML
    private Label feedBackLabel;

    @FXML
    private Button changeColor;

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
    void addEdge(ActionEvent event) {

    }

    @FXML
    void addEdgeWeight(ActionEvent event) {

    }

    @FXML
    void addVertexName(ActionEvent event) {

    }

    @FXML
    void changeColor(ActionEvent event) {

    }

    @FXML
    void clearAll(ActionEvent event) {
        model.clearDisplayVertex();
    }

    @FXML
    void clickVertexNameField(MouseEvent event) {
        if(vertexName.getText().equals("Add vertex name"))
            vertexName.setText("");
    }

    @FXML
    void close(ActionEvent event) {
        stage.close();
    }

    @FXML
    void exitVertexNameField(MouseEvent event) {
        if(vertexName.getText().equals("")){
            vertexName.setText("Add vertex name");
            }
    }

    @FXML
    void exitEdgeWeightField(MouseEvent event) {
        if(edgeWeight.getText().equals("")){
        edgeWeight.setText("Add edge weight");
        }
    }

    @FXML
    void executeBfs(ActionEvent event) {
        algorithmSelectionMenu.setText("BFS");

    }

    @FXML
    void executeDijkstra(ActionEvent event) {
        algorithmSelectionMenu.setText("Dijkstra");
    }

    @FXML
    void executeSpanningTree(ActionEvent event) {
        algorithmSelectionMenu.setText("Spanning Tree");
    }

    @FXML
    void clickEdgeWeightField(MouseEvent event) {
        if(edgeWeight.getText().equals("Add edge weight"))
            edgeWeight.setText("");
    }

    @FXML
    void printToCsv(ActionEvent event) {
        try {
            giveFeedback(handler.convertToCSV());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
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
        PreWindowController preWindowController = new PreWindowController(oldStage);
        preWindowController.getStage().show();
        close(event);
    }

    @FXML
    void remove(ActionEvent event) {

    }

    @FXML
    void showHelp(ActionEvent event) {
        HelpWindowController helpWindowController = new HelpWindowController(false);
        helpWindowController.getStage().show();
    }

    private void changeVertexColor(Vertex changeVertex, Color color){
        Circle circle =  circleVertexMap.inverse().get(changeVertex);
        circle.setFill(color);
    }

    private void changeEdgeColor(Edge changeEdge, Color color){
        Line line =  lineEdgeMap.inverse().get(changeEdge);
        line.setStroke(color);
    }

    private void changeEdgeColor(Color color){
        for (Line line : lineEdgeMap.keySet()){
            line.setStroke(color);
        }
    }

    private void changeVertexColor(Color color){
        for (Circle circle : circleVertexMap.keySet()){
            circle.setFill(color);
        }
    }

    private MainWindowModel.VertexListener vertexListener = new MainWindowModel.VertexListener() {
        @Override
        public void onAddVertex(Vertex newVertex) {
            drawVertex(newVertex);
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
            drawEdge(newEdge);
        }

        @Override
        public void onSelectEdge(Edge changeEdge){
            changeEdgeColor(changeEdge, stdLineSelectedColor);
        }

        @Override
        public void onClearSelectedEdge(){
            changeEdgeColor(stdLineColor);
        }
    };

    private void clearPaintArea(){
        paintArea.getChildren().removeAll();
    }

    private EventHandler<MouseEvent> clearAllClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            model.clearDisplayVertex();
        }
    };

    private EventHandler<MouseEvent> paintAreaClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
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
                            createVertex(UUID.randomUUID().toString(), new Point((int)event.getX(), (int)event.getY()));
                        }
                        break;
                    default:
                        clickAction = ClickAction.PaintAreaClick;
                        break;
                }
        }
    };

    private EventHandler<MouseEvent> vertexClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            clickAction = ClickAction.VertexClick;
            if(event.getSource() instanceof Circle){
                Circle clickedCircle = (Circle) event.getSource();
                switch (model.getSelectedVertex().size()){
                    case 0:
                        model.addSelectedVertex(circleVertexMap.get(clickedCircle));
                        break;
                    case 1:
                        model.addSelectedVertex(circleVertexMap.get(clickedCircle));
                        Edge newEdge = new Edge(model.getSelectedVertex().get(0), model.getSelectedVertex().get(1));
                        model.addDisplayEdge(newEdge);
                        model.clearSelectedVertex();
                        break;
                }
            }
        }
    };

    private EventHandler<MouseEvent> edgeClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            clickAction = ClickAction.EdgeClick;
            if(event.getSource() instanceof Line){
                Line clickedLine = (Line) event.getSource();
                model.addSelectedEdge(lineEdgeMap.get(clickedLine));
            }
        }
    };

    private ClickAction clickAction = ClickAction.PaintAreaClick;

    private enum ClickAction{
        PaintAreaClick,
        VertexClick,
        EdgeClick

    }
    // A vertex is represented as a circle in the gui. This is the mapping of circle to vertex.
    private BiMap<Circle, Vertex> circleVertexMap = HashBiMap.create();
    private BiMap<Line, Edge> lineEdgeMap = HashBiMap.create();
    private void createVertex(String value, Point position){
        Vertex newVertex = new Vertex(value, position);
        model.addDisplayVertex(newVertex);
    }
    private static final int VERTEX_SIZE = 10;

    private void drawVertex(Vertex vertex){
        Circle circle = new Circle(VERTEX_SIZE, stdVertexColor);
        circle.setCenterX(vertex.getX());
        circle.setCenterY(vertex.getY());
        circle.setOnMouseClicked(vertexClick);
        paintArea.getChildren().add(circle);
        circleVertexMap.put(circle, vertex);
    }
    private void drawEdge(Edge edge){
        Line line = new Line(edge.getStart().getX(), edge.getStart().getY(),
                                edge.getEnd().getX(), edge.getEnd().getY());
        line.setFill(stdLineColor);
        line.setStrokeWidth(5);
        line.setOnMouseClicked(edgeClick);
        paintArea.getChildren().add(0, line);

        lineEdgeMap.put(line, edge);
    }

    public Stage getStage(){
        return stage;
    }
}

