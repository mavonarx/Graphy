package ch.zhaw.graphy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class FileInputController {
    private Stage stage;
    private Stage oldStage;
    File lauchFile;
    private static final String PROMPT = "DRAG ONE GRAPH FILE HERE\n";

    public FileInputController(Stage oldStage){
        this.oldStage= oldStage;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FileInput.fxml"));
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
        textArea.setText(PROMPT);
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
    private TextArea textArea;

    @FXML
    void DragDropped(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();

        if (files.size()!=1){

            textArea.setText(PROMPT +  "\nYou should only provide one file");
            launch.setDisable(true);
            return;

        }
        if (files.get(0) == null){
            throw new IllegalArgumentException("The file is null");
        }
        textArea.setText("The chosen file is: \n" + files.get(0).getPath().toUpperCase());
        lauchFile = files.get(0);
        launch.setDisable(false);


    }


    @FXML
    void close(ActionEvent event) {
        stage.close();
    }

    @FXML
    void launch(ActionEvent event){
        MainWindowController mainWindowController = new MainWindowController(lauchFile);
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
