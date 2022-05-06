package ch.zhaw.graphy;

import ch.zhaw.graphy.Graph.Vertex;
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


public class MainWindowController {

    private Stage stage;

    private Color selectedColor = Color.RED;

    private MainWindowModel model;

    public MainWindowController(){
        try{
			FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
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
        Stage stage = (Stage) close.getScene().getWindow();
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

    }

    @FXML
    void remove(ActionEvent event) {

    }

    @FXML
    void showHelp(ActionEvent event) {
        HelpWindowController helpWindowController = new HelpWindowController(false);
        helpWindowController.getStage().show();
    }

    @FXML
    public void initialize() {
        model = new MainWindowModel();
        model.registerVertexListener((Vertex newVertex) -> {
            drawVertex(newVertex);
        });
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
    };

    private EventHandler<MouseEvent> vertexClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(event.getSource() instanceof Circle){
                Circle clickedCircle = (Circle) event.getSource();
                clickedCircle.setFill(Color.BLUE);
            }
        }
    };

    private ClickAction clickAction = ClickAction.AddVertex;

    private enum ClickAction{
        NoAction,
        AddVertex,
        AddEdge

    }

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
    }
}

