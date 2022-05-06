package ch.zhaw.graphy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class PreWindowController {

    @FXML
    private Button drawGraph;

    @FXML
    private Button providedGraph;

    @FXML
    private MenuItem showHelp;

    @FXML
    void drawOwnGraph(ActionEvent event) {
        MainWindowController mainWindowController = new MainWindowController();
        mainWindowController.getStage().show();
    }

    @FXML
    void providedGraph(ActionEvent event) {
        //MainWindowController mainWindowController = new MainWindowController();
        FileInputController fileInputController = new FileInputController();
        fileInputController.getStage().show();
        //mainWindowController.getStage().show();
    }

    @FXML
    void showHelp(ActionEvent event) {
        HelpWindowController helpWindowController = new HelpWindowController(true);
        helpWindowController.getStage().show();
        
    }

}

