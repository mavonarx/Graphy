package ch.zhaw.graphy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HelpWindowController {

    private Stage stage;
    private boolean isPreWindow;

    public HelpWindowController(boolean isPreWindow){
        this.isPreWindow = isPreWindow;
        try{
			FXMLLoader helpLoader = new FXMLLoader(getClass().getResource("/HelpWindow.fxml"));
			helpLoader.setController(this);
			Stage helpStage = new Stage();
			Pane rootNode = helpLoader.load();
			Scene scene = new Scene(rootNode);
			helpStage.setScene(scene);
			helpStage.setMinWidth(280);
			helpStage.setMinHeight(250);
			this.stage = helpStage;
			helpStage.show();
		} catch(Exception e){
		   e.printStackTrace();
		}
    }

    @FXML
    public void initialize(){
          setText();
          textArea.setEditable(false);
    }

    public Stage getStage(){
        return stage;
    }

    @FXML
    private Button close;

    @FXML
    private TextArea textArea;

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    private void setText(){
        if(isPreWindow){
            textArea.setText("You can choose between the two following options:" + System.lineSeparator()
            + "Provided Graph: You have to provide a files" + System.lineSeparator()
            + "Draw own Graph: You can draw your own graph in the main window. The help How it exactly works you wil");
        } else{
            textArea.setText("");
        }
    }

}


