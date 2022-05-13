package ch.zhaw.graphy;

import ch.zhaw.graphy.Graph.Edge;
import ch.zhaw.graphy.Graph.Point;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents an Edge in the GUI
 */
public class EdgeGui implements GraphGuiObject {
    private record ArrowInfo(int x, int y, int xEnd, int yEnd){}
    private static final Color STD_EDGE_COLOR = Color.LIGHTGRAY;
    private static final Color STD_EDGE_SELECTED_COLOR = Color.AQUA;
    private static final int CURVE_WIDTH = 3;
    private static final int CLICK_WIDTH = 20;
    private EdgeClickEvent onEdgeClick;

    private QuadCurve clickArea;
    private QuadCurve curve;
    private Label name;
    private Line arrowline1;
    private Line arrowline2;

    /**
     * Constructor for EdgeGui
     *
     * @param edge that will be represented by the EdgeGui
     * @param event clickEvent that is triggered when the EdgeGui object gets clicked
     */
    public EdgeGui(Edge edge, EdgeClickEvent event){
        onEdgeClick = event;
        int xStart = edge.getStart().getX();
        int xEnd = edge.getEnd().getX();
        int yStart = edge.getStart().getY();
        int yEnd = edge.getEnd().getY();
        ArrowInfo pUp = findArrow(xStart,xEnd,yStart,yEnd,true);
        ArrowInfo pDown = findArrow(xStart,xEnd,yStart,yEnd,false);

        Point curve1 = findCurve(xStart,xEnd,yStart, yEnd);
        curve = new QuadCurve(xStart,yStart,curve1.x(),curve1.y(),xEnd,yEnd);
        curve.setFill(null);
        curve.setStrokeWidth(CURVE_WIDTH);
        curve.setStroke(STD_EDGE_COLOR);

        clickArea = new QuadCurve(xStart,yStart,curve1.x(),curve1.y(),xEnd,yEnd);
        clickArea.setFill(null);
        clickArea.setStrokeWidth(CLICK_WIDTH);
        clickArea.setStroke(Color.TRANSPARENT);
        clickArea.setOnMouseClicked(curveClick);

        arrowline1 = new Line(pUp.xEnd,pUp.yEnd,pUp.x,pUp.y);
        arrowline2 = new Line(pDown.xEnd,pDown.yEnd,pDown.x,pDown.y);
        arrowline1.setStrokeWidth(CURVE_WIDTH);
        arrowline2.setStrokeWidth(CURVE_WIDTH);
        arrowline1.setStroke(STD_EDGE_COLOR);
        arrowline2.setStroke(STD_EDGE_COLOR);
        arrowline1.setPickOnBounds(false);
        arrowline2.setPickOnBounds(false);

        name = new Label(String.valueOf(edge.getWeight()));
        name.setTextFill(Color.BLACK);
        name.setFont(new Font("Arial" ,16));
        name.relocate(curve1.x(), curve1.y());
    }

    /**
     * Sets the color of the EdgeGui object
     *
     * @param color to set.
     */
    public void setColor(Color color){
        curve.setStroke(color);
        arrowline1.setStroke(color);
        arrowline2.setStroke(color);
        name.setTextFill(color);
    }

    /**
     * Sets the color of this EdgeGui object to standart color.
     */
    public void setStdColor(){
        curve.setStroke(STD_EDGE_COLOR);
        arrowline1.setStroke(STD_EDGE_COLOR);
        arrowline2.setStroke(STD_EDGE_COLOR);
        name.setTextFill(Color.BLACK);
    }

    /**
     * Sets the color of this EdgeGui object to selected color.
     */
    public void setSelectedColor(){
        curve.setStroke(STD_EDGE_SELECTED_COLOR);
        arrowline1.setStroke(STD_EDGE_SELECTED_COLOR);
        arrowline2.setStroke(STD_EDGE_SELECTED_COLOR);
        name.setTextFill(STD_EDGE_SELECTED_COLOR);
    }

    /**
     * Returns if this object is colored
     *
     * @return true if this object is not colored in standart color.
     */
    @Override
    public boolean isColored() {
        return curve.getStroke() != STD_EDGE_COLOR;
    }

    /**
     * Gets the nodes of an EdgeGui object.
     *
     * @return all nodes that graphically represents an Edge
     */
    public List<Node> getNodes(){
        List<Node> nodes = new ArrayList<>();
        nodes.add(curve);
        nodes.add(name);
        nodes.add(arrowline1);
        nodes.add(arrowline2);
        nodes.add(clickArea);
        return nodes;
    }

    /**
     * Gets an Instance of the current EdgeGui
     *
     * @return this instance
     */
    private EdgeGui getMe(){
        return this;
    }

    /**
     * Calculates a point in the middle of the two vertices where the line should curve to.
     *
     * @param xStart x-coordinate of start point
     * @param xEnd x-coordinate of the end Point
     * @param yStart y-coordinate of the start point
     * @param yEnd y-coordinate of the end point
     * @return a point representing the curving point
     */
    private Point findCurve(int xStart, int xEnd, int yStart, int yEnd){
        final double CURVE_ROUNDING = 30.0/200;
        final double MAXFACTOR = 200;
        int x = xEnd-xStart;
        int y = yEnd-yStart;
        double twoNorm =  Math.sqrt(x*x + y*y);
        double xNorm = x/twoNorm;
        double yNorm = y/twoNorm;

        int halfx = xStart+(xEnd-xStart)/2;
        int halfy = yStart+(yEnd-yStart)/2;


        double factor = Math.min(twoNorm, MAXFACTOR);

        x = (int )(halfx-CURVE_ROUNDING*yNorm*factor);
        y = (int)(halfy +CURVE_ROUNDING*xNorm*factor);
        return new Point(x,y);
    }

    /**
     * Method to calculate an arrow leg of an edge. Because an edge is slightly curved -
     * the arrow legs are rotated correspondingly
     *
     * @param xStart x-coordinate of start point
     * @param xEnd x-coordinate of end point
     * @param yStart y-coordinate of end point
     * @param yEnd y-coorinate of end point
     * @param up boolean if the leg is the upper or lower leg.
     * @return returns {@ArrowInfo} which is a quadruple of int values representing 2 points start and endpoint of the
     * arrow leg.
     */
    private ArrowInfo findArrow(int xStart, int xEnd, int yStart, int yEnd, boolean up){
        int vertexSize = VertexGui.VERTEX_SIZE;
        int x = xEnd-xStart;
        int y = yEnd-yStart;
        double twoNorm =  Math.sqrt(x*x + y*y);
        double xNorm = x/twoNorm;
        double yNorm = y/twoNorm;
        final int ANGEL_GRAD = -12;
        final double ANGEL_RAD = ANGEL_GRAD*Math.PI/180;
        xNorm = xNorm*Math.cos(ANGEL_RAD)-yNorm*Math.sin(ANGEL_RAD);
        yNorm = yNorm*Math.cos(ANGEL_RAD)+xNorm*Math.sin(ANGEL_RAD);

        if (up){
            x = (int )(xEnd -2*vertexSize*xNorm +vertexSize*yNorm);
            y = (int)(yEnd-2*vertexSize* yNorm -vertexSize*xNorm);
        }
        else {
            x = (int )(xEnd -2*vertexSize*xNorm -vertexSize*yNorm);
            y = (int)(yEnd-2*vertexSize* yNorm +vertexSize*xNorm);
        }
        return new ArrowInfo(x,y,(int)(xEnd-vertexSize*xNorm),(int)(yEnd-vertexSize*yNorm));
    }

    /**
     * Mouse event that is triggered when the EdgeGui is clicked.
     */
    private EventHandler<MouseEvent> curveClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            onEdgeClick.handle(getMe());
        }
    };
}
