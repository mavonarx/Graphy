package ch.zhaw.graphy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class is responsible for the logic in the HelpWindow.
 * It listens to changes from the gui and applies its logic and finally updates
 * the gui.
 * 
 * @author Tanja Aeberhardt, Nicolas Balke, Lukas Gruber, Matthias von Arx
 * @version 13.05.2022
 */
public class HelpWindowController {

    private Stage stage;
    private boolean isPreWindow;

    @FXML
    private TextArea textArea;

    /**
     * Constructor for FileInputController. Fills in the scene. Sets up, configures and shows the stage.
     * @param isPreWindow true, if the PreWindow loads the HelpWindow.
     */
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

    /**
     * Called to initialize the HelpWindowController after its root element has been completely processed.
     */
    @FXML
    public void initialize() {
        setText();
        textArea.setEditable(false);
    }

    /**
     * Closes the HelpWindow.
     * @param event
     */
    @FXML
    void close(ActionEvent event) {
        stage.close();
    }

    /**
     * Fills the textArea with a help message depending on which controller loads the HelpWindowController.
     */
    private void setText() {
        if (isPreWindow) {
            textArea.setText("You can choose between the two following options:" + System.lineSeparator()
                    + System.lineSeparator()
                    + "Provided Graph:" + System.lineSeparator()
                    + "You have to provide a CSV-File. Then the graph will be created using this file."
                    + System.lineSeparator() + System.lineSeparator()
                    + "Draw own Graph:" + System.lineSeparator()
                    + "You can draw your own graph in the following window."
                    + System.lineSeparator() + System.lineSeparator() + System.lineSeparator()
                    + "The help button in the following window has more informations about how the tool exactly works.");
        } else {
            textArea.setText("If you provided the graph from a CSV-File, the graph is shown." + System.lineSeparator()
                    + "Otherwise you can now create the graph by hand. A mouseclick adds a vertex." + System.lineSeparator() 
                    + "If you want to name the vertex you can write the name into the text field 'Vertex name' before you create the vertex with a mouse click." + System.lineSeparator() 
                    + "As soon as you select two vertices an edge will be drawn between them. The drawn edge has a random weight." + System.lineSeparator()
                    + "If you want to add an own weight, you can fill in the number into the text field 'Edge Weight' before you select the two vertices." + System.lineSeparator()
                    + "There are two check boxes. If you select 'Bidirectional' the edge will be drawn in both directions, not only one." + System.lineSeparator()
                    + "If you select 'Select mode' you can't draw any edges and vertices, only select them."
                    + System.lineSeparator()
                    + "As soon as the graph is complete you can execute different alogrithms."
                    + System.lineSeparator() + "With the buttons you can customise the graph as you like. All functions of the buttons are described below."
                    + System.lineSeparator() + System.lineSeparator()
                    + "Clear all:" + System.lineSeparator()
                    + "Removes the whole graph."
                    + System.lineSeparator() + System.lineSeparator()
                    + "Remove:" + System.lineSeparator()
                    + "Removes only the vertices and edges that are selected."
                    + System.lineSeparator() + System.lineSeparator()
                    + "Colorize:" + System.lineSeparator()
                    + "Colorizes all edges from red(large weight) to green(small weight) depending on the weight."
                    + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() 
                    + "Select alogrithm: You can select one of the following algorithms." + System.lineSeparator()
                    + System.lineSeparator()
                    + "Dijkstra:" + System.lineSeparator()
                    + "Illustrates the shortest path for weighted graphs from the start vertex (the first selected vertex) to the end vertex (the second select vertex)."
                    + System.lineSeparator() + System.lineSeparator()
                    + "BFS:" + System.lineSeparator()
                    + "Illustrates the shortest path from the selected start vertex to all other vertices."
                    + System.lineSeparator() + System.lineSeparator()
                    + "Spanning Tree:" + System.lineSeparator()
                    + "Illustrates the minimum spanning tree over the graph."
                    + System.lineSeparator() + System.lineSeparator()
                    + "Dijkstra via:" + System.lineSeparator()
                    + "Illustrates the shortest path for weighted graphs from the start vertex via a specific vertex to the end vertex."
                    + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() 
                    + "Save as CSV:" + System.lineSeparator()
                    + "Exports the displayed graph as a CSV-File into an output folder. In the textfield next to this button you can name the file."
                    + System.lineSeparator() + System.lineSeparator()
                    + "Back to start:" + System.lineSeparator()
                    + "Closes this window and goes back to the start. The designed graph will be removed."
                    + System.lineSeparator() + System.lineSeparator()
                    + "Close:" + System.lineSeparator()
                    + "Closes the window and leaves the tool."
                    + System.lineSeparator() + System.lineSeparator());
        }
    }

    /**
     * Returns the stage of the HelpWindow.
     * @return current stage
     */
    public Stage getStage() {
        return stage;
    }
}
