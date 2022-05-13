package ch.zhaw.graphy;

import ch.zhaw.graphy.Graph.Vertex;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class VertexGui {
    private Circle node;
    private Circle clickArea;
    private Label name;

    private static final Color stdCircleColor = Color.RED;
    private static final Color stdTextColor = Color.BLACK;
    public static final int VERTEX_SIZE = 10;

    private VertexClickEvent onVertexClick;
    private VertexGui getMe(){
        return this;
    }
    private EventHandler<MouseEvent> vertexClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            onVertexClick.handle(getMe());
        }
    };
    public void setColor(Color color){
        node.setFill(color);
    }

    public VertexGui(Vertex vertex, VertexClickEvent clickEvent){
        onVertexClick = clickEvent;

        clickArea = new Circle(2*VERTEX_SIZE, Color.TRANSPARENT);
        clickArea.setCenterX(vertex.getX());
        clickArea.setCenterY(vertex.getY());
        clickArea.setOnMouseClicked(vertexClick);
        node = new Circle(VERTEX_SIZE, stdCircleColor);
        node.setCenterX(vertex.getX());
        node.setCenterY(vertex.getY());

        name = new Label(vertex.getName());
        name.setTextFill(stdTextColor);
        name.setPrefWidth(100);
        name.setPrefHeight(20);
        name.setAlignment(Pos.CENTER);
        name.relocate(node.getCenterX() - (name.getPrefWidth() / 2), node.getCenterY() - (VERTEX_SIZE + name.getPrefHeight()));
    }

    public List<Node> getNodes(){
        List<Node> nodes = new ArrayList<>();
        nodes.add(node);
        nodes.add(name);
        nodes.add(clickArea);
        return nodes;
    }
}
