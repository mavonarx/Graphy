package ch.zhaw.graphy;

import java.io.File;
import java.io.IOException;

import ch.zhaw.graphy.Graph.GraphHandler;
import javafx.event.ActionEvent;
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
import javafx.stage.Stage;

public class MainWindowController {

    GraphHandler handler;

    private Stage stage;
    private Stage oldStage;

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

    public MainWindowController(File file){
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

    public Stage getStage(){
        return stage;
    }

    @FXML
    void initialize(){
        edgeWeight.setText("Add edge weight");
        vertexName.setText("Add vertex name");
        
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

}

