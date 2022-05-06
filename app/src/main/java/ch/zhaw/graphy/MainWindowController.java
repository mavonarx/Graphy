package ch.zhaw.graphy;

import ch.zhaw.graphy.Graph.Vertex;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.awt.*;

public class MainWindowController {

    private Color selectedColor = Color.RED;

    @FXML
    private Pane paintArea;

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

    }

    @FXML
    void executeDijkstra(ActionEvent event) {

    }

    @FXML
    void executeSpanningTree(ActionEvent event) {

    }

    @FXML
    void printToCsv(ActionEvent event) {

    }

    @FXML
    void remove(ActionEvent event) {

    }

    @FXML
    void showHelp(ActionEvent event) {
        
    }

    @FXML
    public void initialize() {
        paintArea.setOnMouseClicked(paintAreaClick);
    }

    private EventHandler<MouseEvent> paintAreaClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            switch (clickAction){
                case NoAction:
                    break;
                case AddVertex:
                    createVertex("test", new Point ((int)event.getX(), (int)event.getY()));
                    break;
                case AddEdge:
                    break;
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
        Vertex newVertex = new Vertex(value);
        // todo: add vertex to vertex-list
        drawVertex(newVertex);
    }

    private void drawVertex(Vertex vertex){
        Circle circle = new Circle(50, selectedColor);
        circle.relocate(vertex.getX(), vertex.getY());
        paintArea.getChildren().add(circle);
    }

}

