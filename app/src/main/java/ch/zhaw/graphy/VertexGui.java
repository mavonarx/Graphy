package ch.zhaw.graphy;

import ch.zhaw.graphy.Graph.Vertex;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a Vertex in the GUI
 */
public class VertexGui implements GraphGuiObject {
    public static final int VERTEX_SIZE = 10;
    private static final Color STD_VERTEX_COLOR = Color.RED;
    private static final Color STD_VERTEX_SELECTED_COLOR = Color.BLUE;
    private Circle node;
    private Circle clickArea;
    private Label name;
    private VertexClickEvent onVertexClick;

    /**
     * Constructor for VertexGui
     * @param vertex that will be represented by the VertexGui
     * @param clickEvent clickEvent that is triggered when the VertexGui object gets clicked
     */
    public VertexGui(Vertex vertex, VertexClickEvent clickEvent){
        onVertexClick = clickEvent;

        clickArea = new Circle(2*VERTEX_SIZE, Color.TRANSPARENT);
        clickArea.setCenterX(vertex.getX());
        clickArea.setCenterY(vertex.getY());
        clickArea.setOnMouseClicked(vertexClick);
        node = new Circle(VERTEX_SIZE, STD_VERTEX_COLOR);
        node.setCenterX(vertex.getX());
        node.setCenterY(vertex.getY());

        name = new Label(vertex.getName());
        name.setTextFill(STD_VERTEX_COLOR);
        name.setPrefWidth(100);
        name.setPrefHeight(20);
        name.setAlignment(Pos.CENTER);
        name.setFont(new Font("Arial", 16));
        name.relocate(node.getCenterX() - (name.getPrefWidth() / 2), node.getCenterY() - (VERTEX_SIZE + name.getPrefHeight()));
    }

    /**
     * Sets the color of the VertexGui object
     * @param color to set.
     */
    public void setColor(Color color){
        node.setFill(color);
        name.setTextFill(color);
    }

    /**
     * Returns if this object is colored
     * @return true if this object is not colored in standart color.
     */
    @Override
    public boolean isColored() {
        return node.getFill() != STD_VERTEX_COLOR;
    }

    /**
     * Gets the nodes of an VertexGui object.
     * @return all nodes that graphically represents an Vertex
     */
    public List<Node> getNodes(){
        List<Node> nodes = new ArrayList<>();
        nodes.add(node);
        nodes.add(name);
        nodes.add(clickArea);
        return nodes;
    }

    /**
     * Sets the color of this VertexGui object to standart color.
     */
    @Override
    public void setStdColor(){
        node.setFill(STD_VERTEX_COLOR);
        name.setTextFill(STD_VERTEX_COLOR);
    }

    /**
     * Sets the color of this VertexGui object to selected color.
     */
    @Override
    public void setSelectedColor(){
        node.setFill(STD_VERTEX_SELECTED_COLOR);
        name.setTextFill(STD_VERTEX_SELECTED_COLOR);
    }

    /**
     * Gets an Instance of the current VertexGui
     * @return this instance
     */
    private VertexGui getMe(){
        return this;
    }

    /**
     * Mouse event that is triggered when the VertexGui is clicked.
     */
    private EventHandler<MouseEvent> vertexClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            onVertexClick.handle(getMe());
        }
    };
}
