package ch.zhaw.graphy;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainWindowController {

    GraphHandler handler;

    private Stage stage;

    private Color selectedColor = Color.RED;

    private MainWindowModel model;

    public MainWindowController(){
        try{
			FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
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

    public MainWindowController(File file){
        try{
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
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

    public Stage getStage(){
        return stage;
    }

    @FXML
    private Pane paintArea;

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
    void addEdge(ActionEvent event) {

    }

    @FXML
    void changeColor(ActionEvent event) {

    }

    @FXML
    void clearAll(ActionEvent event) {

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
            giveFeedback(handler.convertToCSV());
        }
        catch (IOException e){
            feedBackLabel.setText("an Exception has occurred");
        }
    }

    private void giveFeedback(boolean isSavedAsCSV){
        if(isSavedAsCSV){
            feedBackLabel.setText("File has been saved in the output directory");
        }
       else {
           feedBackLabel.setText("File not saved because the graph is empty");
        }
    }

    @FXML
    void csvMousePressed(MouseEvent event) {
        printToCsv.setStyle("-fx-background-color: white");

    }

    @FXML
    void csvMouseReleased(MouseEvent event) {
        printToCsv.setStyle("-fx-background-color: azure");

    }

    @FXML
    void csvMouseEntered(MouseEvent event) {
        printToCsv.setStyle("-fx-background-color: #dee8e8");

    }

    @FXML
    void csvMouseExited(MouseEvent event) {
        printToCsv.setStyle("-fx-background-color: azure");

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
        Circle vertexCircle =  circleVertexMap.inverse().get(changeVertex);
        vertexCircle.setFill(color);
    }

    private MainWindowModel.VertexListener vertexListener = new MainWindowModel.VertexListener() {
        @Override
        public void onAddVertex(Vertex newVertex) {
            drawVertex(newVertex);
        }

        @Override
        public void onSelectVertex(Vertex selectedVertex) {
            changeVertexColor(selectedVertex, Color.BLUE);
        }
    };

    @FXML
    public void initialize() {
        model = new MainWindowModel();
        model.registerVertexListener(vertexListener);
        paintArea.setOnMouseClicked(paintAreaClick);
        remove.setOnMouseClicked(removeClick);
    }

    private EventHandler<MouseEvent> removeClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            clearVertex();
        }
    };

    private EventHandler<MouseEvent> paintAreaClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(model.selectedVertex.isEmpty()){
                switch (clickAction){
                    case NoAction:
                        break;
                    case AddVertex:
                        createVertex("test", new Point((int)event.getX(), (int)event.getY()));
                        break;
                    case AddEdge:
                        break;
                }
            }
        }
    };

    private EventHandler<MouseEvent> vertexClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(event.getSource() instanceof Circle){
                Circle clickedCircle = (Circle) event.getSource();
                model.addSelectedVertex(circleVertexMap.get(clickedCircle));
                //clickedCircle.setFill(Color.BLUE);
            }
        }
    };

    private ClickAction clickAction = ClickAction.AddVertex;

    private enum ClickAction{
        NoAction,
        AddVertex,
        AddEdge

    }
    // A vertex is represented as a circle in the gui. This is the mapping of circle to vertex.
    private BiMap<Circle, Vertex> circleVertexMap = HashBiMap.create();
    private void createVertex(String value, Point position){
        Vertex newVertex = new Vertex(value, position);
        model.addDisplayVertex(newVertex);
    }
    private static final int VERTEX_SIZE = 5;
    private void clearVertex(){
        paintArea.getChildren().clear();
    }
    private void drawVertex(Vertex vertex){
        Circle circle = new Circle(VERTEX_SIZE, selectedColor);
        circle.relocate(vertex.getX(), vertex.getY());
        circle.setOnMouseClicked(vertexClick);
        paintArea.getChildren().add(circle);
        circleVertexMap.put(circle, vertex);
    }
}

