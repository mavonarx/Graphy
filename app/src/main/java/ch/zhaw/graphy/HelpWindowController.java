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

    public HelpWindowController(boolean isPreWindow) {
        this.isPreWindow = isPreWindow;
        try {
            FXMLLoader helpLoader = new FXMLLoader(getClass().getResource("/ch/zhaw/graphy/HelpWindow.fxml"));
            helpLoader.setController(this);
            Stage helpStage = new Stage();
            Pane rootNode = helpLoader.load();
            Scene scene = new Scene(rootNode);
            helpStage.setScene(scene);
            helpStage.setMinWidth(280);
            helpStage.setMinHeight(250);
            this.stage = helpStage;
            helpStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        setText();
        textArea.setEditable(false);
    }

    public Stage getStage() {
        return stage;
    }

    @FXML
    private Button close;

    @FXML
    private TextArea textArea;

    @FXML
    void close(ActionEvent event) {
        stage.close();
    }

    private void setText() {
        if (isPreWindow) {
            textArea.setText("You can choose between the two following options:" + System.lineSeparator()
                    + System.lineSeparator()
                    + "Provided Graph:" + System.lineSeparator()
                    + "You have to provide a graph file. Then the graph will be created using this file."
                    + System.lineSeparator() + System.lineSeparator()
                    + "Draw own Graph:" + System.lineSeparator()
                    + "You can draw your own graph in the following window."
                    + System.lineSeparator() + System.lineSeparator() + System.lineSeparator()
                    + "The help button in the following window has more informations about how the tool exactly works.");
        } else {
            textArea.setText("If you provided the graph from a CSV-File, the graph is shown." + System.lineSeparator()
                    + "Otherwise you can now create the graph by hand. A mouseclick adds a vertex."
                    + System.lineSeparator()
                    + "With the described functions below you can customise the graph as you like. "
                    + System.lineSeparator() + "As soon as the graph is complete you can execute different alogrithms."
                    + System.lineSeparator() + "All functions are described below." + System.lineSeparator()
                    + System.lineSeparator() + System.lineSeparator()
                    + System.lineSeparator()
                    + "Add edge:" + System.lineSeparator()
                    + "Adds an edge between the two selected vertices."
                    + System.lineSeparator() + System.lineSeparator()
                    + "Clear all:" + System.lineSeparator()
                    + "Removes the whole graph."
                    + System.lineSeparator() + System.lineSeparator()
                    + "Remove:" + System.lineSeparator()
                    + "Removes only the vertices that are selected"
                    + System.lineSeparator() + System.lineSeparator()
                    + "Change color:" + System.lineSeparator()
                    + "Changes the color of the selected edges."
                    + System.lineSeparator() + System.lineSeparator()
                    + "Select alogrithm:" + System.lineSeparator()
                    + "You can selected one of the following algorithms." + System.lineSeparator()
                    + System.lineSeparator()
                    + "Dijkstra: Illustrates the shortest path for weighted graphs from the selected start vertex to the selected end vertex."
                    + System.lineSeparator()
                    + "BFS: Illustrates the shortest path for unweighted graphs from the selected start vertex to the selected end vertex."
                    + System.lineSeparator()
                    + "Spanning Tree: Illustrates the minimum spanning tree over the graph."
                    + System.lineSeparator() + System.lineSeparator()
                    + "Print to CSV:" + System.lineSeparator()
                    + "Export the displayed graph as a CSV-File."
                    + System.lineSeparator() + System.lineSeparator()
                    + "Close:" + System.lineSeparator()
                    + "Close the window and leave the tool."
                    + System.lineSeparator() + System.lineSeparator());
        }
    }

}
