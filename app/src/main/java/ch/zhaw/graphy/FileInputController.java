package ch.zhaw.graphy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FileInputController {
    private Stage stage;

    public FileInputController(){
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

    public Stage getStage() {
        return stage;
    }

    @FXML
    private Button close;

    @FXML
    private TextArea textArea;

    @FXML
    void DragDropped(DragEvent event) {
    }

    @FXML
    void close(ActionEvent event) {

    }

}
