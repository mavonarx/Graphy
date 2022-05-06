package ch.zhaw.graphy;

import ch.zhaw.graphy.Graph.GraphHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;

public class MainWindowController {

    GraphHandler handler;

    private Stage stage;

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

}

