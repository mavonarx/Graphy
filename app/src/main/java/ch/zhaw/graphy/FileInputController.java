package ch.zhaw.graphy;

import ch.zhaw.graphy.Graph.GraphHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for the logic in the FileInput-Window.
 * It listens to changes from the gui and applies its logic and finally updates
 * the gui.
 * 
 * @author Tanja Aeberhardt, Nicolas Balke, Lukas Gruber, Matthias von Arx
 * @version 13.05.2022
 */
public class FileInputController {
    private Stage stage;
    private Stage oldStage;
    File launchFile;
    private static final String PROMPT = "Drag a graph file below";

    @FXML
    private Button launch;

    @FXML
    private Label title;

    @FXML
    private TextArea textArea;

    /**
     * Constructor for FileInputController. Fills in the scene. Sets up, configures
     * and shows the stage.
     * 
     * @param oldStage the given stage
     */
    public FileInputController(Stage oldStage) {
        this.oldStage = oldStage;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ch/zhaw/graphy/FileInput.fxml"));
            loader.setController(this);
            Stage stage = new Stage();
            Pane rootNode = loader.load();
            Scene scene = new Scene(rootNode);
            stage.setScene(scene);
            stage.setMinWidth(280);
            stage.setMinHeight(250);
            this.stage = stage;
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called to initialize the FileInputController after its root element has been
     * completely processed.
     */
    @FXML
    private void initialize() {
        textArea.setOnDragOver((event) -> {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        });
        title.setText(PROMPT);
        textArea.setStyle("-fx-font-size: 20");
        textArea.setEditable(false);
        launch.setDisable(true);
    }

    /**
     * Files can be dragged and dropped into the textArea. If you drop more then one
     * file there will be a hint and you can't provide the graph.
     * 
     * @param event
     */
    @FXML
    void DragDropped(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();

        if (files.size() != 1) {
            textArea.setText("You should only provide one file");
            launch.setDisable(true);
            return;
        }
        if (files.get(0) == null) {
            throw new IllegalArgumentException("The file is null");
        }
        textArea.setText("The chosen file is: \n" + files.get(0).getPath());
        launchFile = files.get(0);

        try {
            new GraphHandler(null, launchFile);
        } catch (IOException e) {
            textArea.setText(e.getMessage());
            launch.setDisable(true);
            return;
        }
        launch.setDisable(false);
    }

    /**
     * Starts the MainWindow with the dropped file and closes the FileInput-Window.
     * 
     * @param event
     */
    @FXML
    void launch(ActionEvent event) {
        MainWindowController mainWindowController = new MainWindowController(stage, launchFile);
        mainWindowController.getStage().show();
        close(event);
    }

    /**
     * Goes back to the PreWindow and closes the FileInput-Window.
     * 
     * @param event
     */
    @FXML
    void goBack(ActionEvent event) {
        PreWindowController preWindowController = new PreWindowController(oldStage);
        preWindowController.getStage().show();
        close(event);
    }

    /**
     * Closes the FileInput-Window.
     * 
     * @param event
     */
    @FXML
    void close(ActionEvent event) {
        stage.close();
    }

    /**
     * Returns the stage of the FileInput-Window.
     * 
     * @return current stage
     */
    public Stage getStage() {
        return stage;
    }

}
