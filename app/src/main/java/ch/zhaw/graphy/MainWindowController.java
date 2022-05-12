package ch.zhaw.graphy;

import ch.zhaw.graphy.Graph.Edge;

import java.io.File;
import java.io.IOException;

import ch.zhaw.graphy.Graph.GraphHandler;
import ch.zhaw.graphy.Graph.Point;
import ch.zhaw.graphy.Graph.Vertex;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.swing.*;

public class MainWindowController {

    GraphHandler handler;

    private Stage stage;
    private static int numberOfDrawnUnnamedVertex = 0;
    private Stage oldStage;

    private Color stdVertexColor = Color.RED;
    private static final Color stdVertexSelectedColor = Color.BLUE;
    private static final Color stdLineColor = Color.LIGHTGRAY;
    private static final Color stdLineSelectedColor = Color.LIGHTBLUE;


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
            drawVertex(vertex);
            for (Edge edge : handler.getGraph().get(vertex)){
                drawEdge(edge);
            }
        }
    }

    @FXML
    private Pane paintArea;

    @FXML
    private TextField fileName;

    @FXML
    void initialize(){
        model = new MainWindowModel(handler);
        model.registerVertexListener(vertexListener);
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
    private CheckBox bidirectional;

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
    void close(ActionEvent event) {
        stage.close();
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
        Circle circle = guiVertexMap.getCircleVertexList().inverse().get(changeVertex);
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
        for (Circle circle : guiVertexMap.getCircleVertexList().keySet()){
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
                            //todo value = name der in der gui textbox steht.
                            createVertex("", new Point((int)event.getX(), (int)event.getY()));
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
                        model.addSelectedVertex(guiVertexMap.getCircleVertexList().get(clickedCircle));
                        break;
                    case 1:
                        if(model.getSelectedVertex().get(0) != guiVertexMap.getCircleVertexList().get(clickedCircle)){
                            model.addSelectedVertex(circleVertexMap.get(clickedCircle));
                        int weight;
                        if ("".equals(edgeWeight.getText())){
                            weight = 0;
                        }
                        else {
                            try {
                                weight = Integer.parseInt(edgeWeight.getText());
                            }
                            catch (NumberFormatException e){
                                weight = 0;
                            }
                        }



                        Edge newEdge = new Edge(model.getSelectedVertex().get(0), model.getSelectedVertex().get(1),weight);

                        model.addDisplayEdge(newEdge);
                        if (bidirectional.isSelected()){
                            newEdge = new Edge(model.getSelectedVertex().get(1),model.getSelectedVertex().get(0),weight);
                            model.addDisplayEdge(newEdge);
                        }
                          model.clearSelectedVertex();
                        }
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
    //private BiMap<Circle, Vertex> circleVertexMap = HashBiMap.create();
    private OwnBiMap guiVertexMap = new OwnBiMap();
    private BiMap<Line, Edge> lineEdgeMap = HashBiMap.create();
    private void createVertex(Point position){
        String name = vertexName.getText();
        if(vertexName.getText().isBlank()){
            name = String.valueOf(++numberOfDrawnUnnamedVertex);
        }
        Vertex newVertex = new Vertex(name, position);
        model.addDisplayVertex(newVertex);
    }
    private static final int VERTEX_SIZE = 10;

    private void drawVertex(Vertex vertex){
        Circle circle = new Circle(VERTEX_SIZE, stdVertexColor);
        circle.setCenterX(vertex.getX());
        circle.setCenterY(vertex.getY());
        circle.setOnMouseClicked(vertexClick);
        Label label = new Label(vertex.getName());
        label.setTextFill(Color.BLACK);
        label.setPrefWidth(100);
        label.setPrefHeight(20);
        //label.setCenterShape(true);
        label.setAlignment(Pos.CENTER);
        //label.setTextAlignment(TextAlignment.CENTER);
        label.relocate(circle.getCenterX() - (label.getPrefWidth() / 2), circle.getCenterY() - (VERTEX_SIZE + label.getPrefHeight()));
        paintArea.getChildren().add(circle);
        paintArea.getChildren().add(label);
        guiVertexMap.put(circle, label, vertex);
    }
    private void drawEdge(Edge edge){
        int xStart = edge.getStart().getX();
        int xEnd = edge.getEnd().getX();
        int yStart = edge.getStart().getY();
        int yEnd = edge.getEnd().getY();
        Point pUp = findArrow(xStart,xEnd,yStart,yEnd,true);
        Point pDown = findArrow(xStart,xEnd,yStart,yEnd,false);


        Line line = new Line(xStart, yStart,
                                xEnd, yEnd);
        line.setStroke(stdLineColor);
        Line arrowline1 = new Line(xEnd,yEnd,pUp.x(),pUp.y());
        Line arrowline2 = new Line(xEnd,yEnd,pDown.x(),pDown.y());
        arrowline1.setStrokeWidth(5);
        arrowline2.setStrokeWidth(5);
        arrowline1.setFill(stdLineColor);
        arrowline2.setFill(stdLineColor);
        arrowline1.setPickOnBounds(false);
        arrowline2.setPickOnBounds(false);
        paintArea.getChildren().add(arrowline1);
        paintArea.getChildren().add(arrowline2);


        line.setFill(stdLineColor);
        line.setStrokeWidth(5);
        line.setOnMouseClicked(edgeClick);
        paintArea.getChildren().add(0, line);

        lineEdgeMap.put(line, edge);
    }


    private Point findArrow(int xStart, int xEnd, int yStart, int yEnd, boolean up){
        int x = xEnd-xStart;
        int y = yEnd-yStart;
        double twoNorm =  Math.sqrt(x*x + y*y);
        double xNorm = x/twoNorm;
        double yNorm = y/twoNorm;

        if (up){
            x = (int )(xEnd -2*VERTEX_SIZE*xNorm +VERTEX_SIZE*yNorm);
            y = (int)(yEnd-2*VERTEX_SIZE* yNorm -VERTEX_SIZE*xNorm);
        }
        else {
            x = (int )(xEnd -2*VERTEX_SIZE*xNorm -VERTEX_SIZE*yNorm);
            y = (int)(yEnd-2*VERTEX_SIZE* yNorm +VERTEX_SIZE*xNorm);
        }






        return new Point(x,y);
    }



    public Stage getStage(){
        return stage;
    }
}

class OwnBiMap{
    public BiMap<Circle, Vertex> getCircleVertexList() {
        return circleVertexList;
    }
    public BiMap<Circle, Label> getCircleLabelList() {
        return circleLabelList;
    }
    private BiMap<Circle, Vertex> circleVertexList = HashBiMap.create();
    private BiMap<Circle, Label> circleLabelList = HashBiMap.create();
    void put(Circle circle, Label label, Vertex vertex){
        circleVertexList.put(circle, vertex);
        circleLabelList.put(circle, label);
    }
}

