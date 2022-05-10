package ch.zhaw.graphy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PreWindowController {
    Stage stage;

    public PreWindowController(){
    }
    public PreWindowController(Stage preStage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ch/zhaw/graphy/PreWindow.fxml"));
            loader.setController(this);
            Pane rootNode = loader.load();
            Scene scene = new Scene(rootNode);
            preStage.setScene(scene);
            preStage.setMinWidth(280);
            preStage.setMinHeight(250);
            preStage.show();
            stage = preStage;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Stage getStage() {
        return stage;
    }

    @FXML
    private Button drawGraph;

    @FXML
    private Button providedGraph;

    @FXML
    private MenuItem showHelp;

    @FXML
    private TextArea helloArea;

    @FXML
    void initialize(){

    }

    @FXML
    void drawOwnGraph(ActionEvent event) {
        MainWindowController mainWindowController = new MainWindowController(stage);
        mainWindowController.getStage().show();
        stage.close();

    }

    @FXML
    void providedGraph(ActionEvent event) {
        FileInputController fileInputController = new FileInputController(stage);
        fileInputController.getStage().show();
        stage.close();
    }

    @FXML
    void showHelp(ActionEvent event) {
        HelpWindowController helpWindowController = new HelpWindowController(true);
        helpWindowController.getStage().show();
        
    }

}

