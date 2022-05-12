package ch.zhaw.graphy;

import ch.zhaw.graphy.Graph.Edge;
import ch.zhaw.graphy.Graph.Vertex;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

class OwnBiMap{
    private BiMap<Circle, Vertex> circleVertexList = HashBiMap.create();
    private BiMap<Circle, Label> circleLabelList = HashBiMap.create();
    private BiMap<Line, Edge> lineEdgeBiMap = HashBiMap.create();
    private BiMap<Line, Label> lineLabelList = HashBiMap.create();
    void put(Circle circle, Label label, Vertex vertex){
        circleVertexList.put(circle, vertex);
        circleLabelList.put(circle, label);
    }

    public BiMap<Circle, Vertex> getCircleVertexList() {
        return circleVertexList;
    }
    public BiMap<Circle, Label> getCircleLabelList() {
        return circleLabelList;
    }
    public BiMap<Line, Edge> getLineEdgeBiMap() {
        return lineEdgeBiMap;
    }
    public BiMap<Line, Label> getLineLabelList() {
        return lineLabelList;
    }
}