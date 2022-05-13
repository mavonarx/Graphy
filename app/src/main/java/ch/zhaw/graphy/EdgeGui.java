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

import java.util.ArrayList;
import java.util.List;

public class EdgeGui {
    private static final Color stdLineColor = Color.LIGHTGRAY;
    private static final Color stdLineSelectedColor = Color.LIGHTBLUE;
    private static final Color stdTextColor = Color.BLACK;
    private static final int curveWidth = 3;
    private static final int stdPadding = 5;
    private EdgeClickEvent onEdgeClick;

    QuadCurve clickArea;
    QuadCurve curve;
    Label name;
    Line arrowline1;
    Line arrowline2;

    private EdgeGui getMe(){
        return this;
    }

    private Point findCurve(int xStart, int xEnd, int yStart, int yEnd){
        final int CURVE_ROUNDING = 30;
        int x = xEnd-xStart;
        int y = yEnd-yStart;
        double twoNorm =  Math.sqrt(x*x + y*y);
        double xNorm = x/twoNorm;
        double yNorm = y/twoNorm;

        int halfx = xStart+(xEnd-xStart)/2;
        int halfy = yStart+(yEnd-yStart)/2;


        x = (int )(halfx-CURVE_ROUNDING*yNorm);
        y = (int)(halfy +CURVE_ROUNDING*xNorm);
        return new Point(x,y);
    }

    private Point findArrow(int xStart, int xEnd, int yStart, int yEnd, boolean up){
        int vertexSize = VertexGui.VERTEX_SIZE;
        int x = xEnd-xStart;
        int y = yEnd-yStart;
        double twoNorm =  Math.sqrt(x*x + y*y);
        double xNorm = x/twoNorm;
        double yNorm = y/twoNorm;
        if (up){
            x = (int )(xEnd -2*vertexSize*xNorm +vertexSize*yNorm);
            y = (int)(yEnd-2*vertexSize* yNorm -vertexSize*xNorm);
        }
        else {
            x = (int )(xEnd -2*vertexSize*xNorm -vertexSize*yNorm);
            y = (int)(yEnd-2*vertexSize* yNorm +vertexSize*xNorm);
        }
        return new Point(x,y);
    }

    public EdgeGui(Edge edge, EdgeClickEvent event){
        onEdgeClick = event;
        int xStart = edge.getStart().getX();
        int xEnd = edge.getEnd().getX();
        int yStart = edge.getStart().getY();
        int yEnd = edge.getEnd().getY();
        Point pUp = findArrow(xStart,xEnd,yStart,yEnd,true);
        Point pDown = findArrow(xStart,xEnd,yStart,yEnd,false);

        Point curve1 = findCurve(xStart,xEnd,yStart, yEnd);
        curve = new QuadCurve(xStart,yStart,curve1.x(),curve1.y(),xEnd,yEnd);
        curve.setFill(null);
        curve.setStrokeWidth(curveWidth);
        curve.setStroke(stdLineColor);

        clickArea = new QuadCurve(xStart,yStart,curve1.x(),curve1.y(),xEnd,yEnd);
        clickArea.setFill(null);
        clickArea.setStrokeWidth(curveWidth + stdPadding);
        clickArea.setStroke(Color.TRANSPARENT);
        clickArea.setOnMouseClicked(curveClick);

        arrowline1 = new Line(xEnd,yEnd,pUp.x(),pUp.y());
        arrowline2 = new Line(xEnd,yEnd,pDown.x(),pDown.y());
        arrowline1.setStrokeWidth(2);
        arrowline2.setStrokeWidth(2);
        arrowline1.setFill(stdLineColor);
        arrowline2.setFill(stdLineColor);
        arrowline1.setPickOnBounds(false);
        arrowline2.setPickOnBounds(false);

        name = new Label(String.valueOf(edge.getWeight()));
        name.setTextFill(stdTextColor);
        name.relocate(curve1.x(), curve1.y());
    }

    public void setColor(Color color){
        curve.setStroke(color);
    }

    private EventHandler<MouseEvent> curveClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            onEdgeClick.handle(getMe());
        }
    };

    public List<Node> getNodes(){
        List<Node> nodes = new ArrayList<>();
        nodes.add(curve);
        nodes.add(name);
        nodes.add(arrowline1);
        nodes.add(arrowline2);
        nodes.add(clickArea);
        return nodes;
    }
}
