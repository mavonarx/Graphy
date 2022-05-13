package ch.zhaw.graphy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class is responsible for the logic in the PreWindow.
 * It listens to changes from the gui and applies its logic and finally updates the gui.
 * 
 * @author Tanja Aeberhardt, Nicolas Balke, Lukas Gruber, Matthias von Arx
 * @author 13.05.2022
 */
public class PreWindowController {
    Stage stage;

    @FXML
    private Button drawGraph;

    @FXML
    private Button providedGraph;

    @FXML
    private MenuItem showHelp;

    /**
     * Constructor for PreWindowController. Fills in the scene. Sets up, configures
     * and shows the stage.
     * 
     * @param preStage the given stage
     */
    public PreWindowController(Stage preStage) {
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

    /**
     * If the user wants to draw a graph on his own, this method opens the MainWindow and closes the PreWindow.
     * @param event
     */
    @FXML
    void drawOwnGraph(ActionEvent event) {
        MainWindowController mainWindowController = new MainWindowController(stage);
        mainWindowController.getStage().show();
        stage.close();

    }

    /**
     * If the user wants to provide a graph from a file, this method opens the FileInput-Window and closes the PreWindow.
     * @param event
     */
    @FXML
    void providedGraph(ActionEvent event) {
        FileInputController fileInputController = new FileInputController(stage);
        fileInputController.getStage().show();
        stage.close();
    }

    /**
     * Opens HelpWindow and shows helping informations.
     * @param event
     */
    @FXML
    void showHelp(ActionEvent event) {
        HelpWindowController helpWindowController = new HelpWindowController(true);
        helpWindowController.getStage().show();
    }

    /**
     * Returns the stage of the PreWindow.
     * @return current stage
     */
    public Stage getStage() {
        return stage;
    }

}
