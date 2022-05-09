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

public class FileInputController {
    private Stage stage;
    private Stage oldStage;
    File lauchFile;
    private static final String PROMPT = "Drag a graph file below";

    public FileInputController(Stage oldStage){
        this.oldStage= oldStage;
        try{
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
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize(){
        textArea.setOnDragOver((event)-> {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        });
        title.setText(PROMPT);
        textArea.setStyle("-fx-font-size: 20");
        textArea.setEditable(false);
        launch.setDisable(true);
    }

    public Stage getStage() {
        return stage;
    }

    public Stage getOldStage() {
        return oldStage;
    }

    @FXML
    private Button close;

    @FXML
    private Button goBack;

    @FXML
    private Button launch;

    @FXML
    private Label title;

    @FXML
    private TextArea textArea;

    @FXML
    void DragDropped(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();

        if (files.size()!=1){
            textArea.setText("You should only provide one file");
            launch.setDisable(true);
            return;
        }
        if (files.get(0) == null){
            throw new IllegalArgumentException("The file is null");
        }


        //title.setText("The chosen file is:" + files.get(0).getPath().toUpperCase());
        textArea.setText("The chosen file is: \n" + files.get(0).getPath());
        lauchFile = files.get(0);
        try{
            new GraphHandler(lauchFile);
        }
        catch (IOException  e){
            textArea.setText(e.getMessage());
            launch.setDisable(true);
            return;
        }
        launch.setDisable(false);
    }


    @FXML
    void close(ActionEvent event) {
        stage.close();
    }

    @FXML
    void launch(ActionEvent event){
        MainWindowController mainWindowController = new MainWindowController(stage,lauchFile);
        mainWindowController.getStage().show();
        close(event);
    }

    @FXML
    void goBack(ActionEvent event){
        PreWindowController preWindowController = new PreWindowController(oldStage);
        preWindowController.getStage().show();
      close(event);


    }

}
