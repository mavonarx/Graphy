package ch.zhaw.graphy;

import ch.zhaw.graphy.Algorithms.BreadthFirstSearch;
import ch.zhaw.graphy.Algorithms.Dijkstra;
import ch.zhaw.graphy.Algorithms.MinimumSpanningTree;
import ch.zhaw.graphy.Graph.Edge;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class MainWindowController {

    GraphHandler handler;

    private Stage stage;
    private static int numberOfDrawnUnnamedVertex = 0;
    private Stage oldStage;

    private Color stdVertexColor = Color.RED;
    private static final Color stdVertexSelectedColor = Color.BLUE;
    private static final Color stdLineColor = Color.BLACK;
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
        fileName.setText("Enter a filename here");
    }

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
        for (Edge edge : model.getEdgeToLineMap().keySet()){
            if (edge.getWeight() > max){
                max = edge.getWeight();
            }
            if (edge.getWeight() < min){
                min = edge.getWeight();
            }
        }
        double difference = (max-min);
        for (Edge edge : model.getEdgeToLineMap().keySet()){
            double percent = edge.getWeight()/difference;
            changeEdgeColor(edge, Color.rgb((int) Math.round(255*percent), (int) Math.round(255-255*percent), 0));
        }
    }

    @FXML
    void clearAll(ActionEvent event) {
        model.clearDisplayVertex(); //TODO update
        for (Edge edge : model.getEdgeToLineMap().keySet()){
            paintArea.getChildren().remove(model.getEdgeToLineMap().get(edge));
        }

        for (Vertex vertex : model.getVertexToCircleMap().keySet()){
            paintArea.getChildren().remove(model.getVertexToCircleMap().get(vertex));
        }

        handler.getGraph().clear();
        model.getSelectedEdge().clear();
        model.getSelectedVertex().clear();
        lineEdgeMap.clear();
        circleVertexMap.clear();
    }


    @FXML
    void close(ActionEvent event) {
        stage.close();
    }


    @FXML
    void executeBfs(ActionEvent event) {
        algorithmSelectionMenu.setText("BFS");
        if (model.selectedVertex.isEmpty()){
            throw new IllegalArgumentException("Pls select a vertex to start from");
        }
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        bfs.executeBFS(handler, model.selectedVertex.get(0));
        for (Vertex vertex : bfs.getVisualMap().keySet()){
            model.getVertexToCircleMap().get(vertex).setFill(Color.ORANGE);
            for (Edge edge : model.getEdgeToLineMap().keySet()){
                if (edge.getEnd().equals(vertex) && edge.getStart().equals(bfs.getVisualMap().get(vertex))){
                    changeEdgeColor(edge, Color.GREEN);
                }
            }
        }

        model.getVertexToCircleMap().get(model.selectedVertex.get(0)).setFill(Color.PURPLE);
    }

    @FXML
    void executeDijkstra(ActionEvent event) {
        algorithmSelectionMenu.setText("Dijkstra");
        Dijkstra dijkstra = new Dijkstra();
        LinkedList<Vertex> path = dijkstra.executeDijkstra(handler, model.selectedVertex.get(0), model.selectedVertex.get(1));
        for (int i =0; i<path.size()-1;i++){
            for (Vertex vertex : model.getVertexToCircleMap().keySet()){
                if (vertex.equals(path.get(i))){
                    changeVertexColor(vertex, Color.ORANGE);
                }
            }
            for (Edge lineEdge : model.getEdgeToLineMap().keySet()){
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
            for (Edge lineEdge : model.getEdgeToLineMap().keySet()){
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
        PreWindowController preWindowController = new PreWindowController(oldStage);
        preWindowController.getStage().show();
        close(event);
    }

    @FXML
    void remove(ActionEvent event) {
        for (Edge modelEdge : model.getSelectedEdge()){
            paintArea.getChildren().remove(model.getEdgeToLineMap().get(modelEdge));
            model.getEdgeToLineMap().remove(modelEdge);
            lineEdgeMap.remove(lineEdgeMap.inverse().get(modelEdge), modelEdge);
            for (Vertex vertex : handler.getGraph().keySet()){
                for (Edge graphEdge : handler.getGraph().get(vertex)){
                    if (modelEdge.equals(graphEdge)){
                        handler.getGraph().get(vertex).remove(graphEdge);
                    }
                }
            }
        }
        for (Vertex vertex : model.getSelectedVertex()){
            paintArea.getChildren().remove(model.getVertexToCircleMap().get(vertex));
            model.getVertexToCircleMap().remove(vertex);
            handler.getGraph().remove(vertex);
            circleVertexMap.remove(vertex, circleVertexMap.get(vertex));
            for (Edge edge : model.getEdgeToLineMap().keySet()){
                if (edge.getEnd().equals(vertex) || edge.getStart().equals(vertex)){
                    paintArea.getChildren().remove(model.getEdgeToLineMap().get(edge));
                    lineEdgeMap.remove(lineEdgeMap.inverse().get(edge), edge);
                    model.getEdgeToLineMap().remove(edge);
                }
            }
        }
        model.selectedVertex.clear();
        model.getSelectedEdge().clear();
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
                            createVertex(new Point((int)event.getX(), (int)event.getY()));
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
        paintArea.getChildren().add(circle);
        circleVertexMap.put(circle, vertex);
        model.getVertexToCircleMap().put(vertex, circle);
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
        model.getEdgeToLineMap().put(edge, line);
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

