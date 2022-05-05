package Graphy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MainWindowController {

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

}

