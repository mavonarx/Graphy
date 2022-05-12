package ch.zhaw.graphy;

import ch.zhaw.graphy.Graph.Edge;
import ch.zhaw.graphy.Graph.GraphHandler;
import ch.zhaw.graphy.Graph.Vertex;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainWindowModel {

    GraphHandler handler;
    List<VertexListener> vertexListeners = new ArrayList<>();
    List<Vertex> displayVertex = new ArrayList<>();
    List<Vertex> selectedVertex = new ArrayList<>();
    private List<Edge> displayEdges = new ArrayList<>();
    private List<Edge> selectedEdge = new ArrayList<>();



    public boolean hasSelectedVertex(){
        return !selectedVertex.isEmpty();
    }
    public boolean hasSelectedEdge(){
        return !selectedEdge.isEmpty();
    }

    public void addDisplayEdge(Edge edge){
        handler.getGraph().get(edge.getStart()).add(edge);
        displayEdges.add(edge);
        notifyOnAddEdge(edge);
    }
    public void addSelectedEdge(Edge edge){
        selectedEdge.add(edge);
        notifyOnSelectEdge(edge);
    }
    public void clearSelectedEdge(){
        selectedEdge.clear();
        notifyOnClearSelectedEdge();
    }

    public MainWindowModel(GraphHandler handler){
        this.handler = handler;
    }

    public void addDisplayVertex(Vertex vertex){
        handler.addVertex(vertex);
        displayVertex.add(vertex);
        notifyOnAddVertex(vertex);
    }

    public void addSelectedVertex(Vertex vertex){
        selectedVertex.add(vertex);
        notifyOnSelectVertex(vertex);
    }
    public void clearDisplayVertex(){
        displayVertex.clear();
        selectedVertex.clear();
        notifyOnClearVertex();
    }
    public void clearSelectedVertex(){
        selectedVertex.clear();
        notifyOnClearSelectedVertex();
    }

    public void registerVertexListener(VertexListener listener){
        vertexListeners.add(listener);
    }

    private void notifyOnAddVertex(Vertex newVertex){
        for (VertexListener listener : vertexListeners){
            listener.onAddVertex(newVertex);
        }
    }

    private void notifyOnSelectVertex(Vertex selectVertex){
        for (VertexListener listener : vertexListeners){
            listener.onSelectVertex(selectVertex);
        }
    }

    private void notifyOnClearVertex(){
        for (VertexListener listener : vertexListeners){
            listener.onClearVertex();
        }
    }

    private void notifyOnClearSelectedVertex(){
        for (VertexListener listener : vertexListeners){
            listener.onClearSelectedVertex();
        }
    }

    private void notifyOnAddEdge(Edge newEdge){
        for (VertexListener listener : vertexListeners){
            listener.onAddEdge(newEdge);
        }
    }

    private void notifyOnSelectEdge(Edge newEdge){
        for (VertexListener listener : vertexListeners){
            listener.onSelectEdge(newEdge);
        }
    }

    private void notifyOnClearSelectedEdge(){
        for (VertexListener listener : vertexListeners){
            listener.onClearSelectedEdge();
        }
    }

    interface VertexListener{
        void onAddVertex(Vertex newVertex);
        void onSelectVertex(Vertex selectedVertex);
        void onClearVertex();
        void onClearSelectedVertex();
        void onAddEdge(Edge newEdge);
        void onSelectEdge(Edge selectedEdge);
        void onClearSelectedEdge();
    }

    public List<Vertex> getSelectedVertex() {
        return selectedVertex;
    }

    public List<Edge> getSelectedEdge() {
        return selectedEdge;
    }

    /*
    public Map<Edge, Line> getEdgeToLineMap() {
        return edgeToLineMap;
    }

    public Map<Vertex, Circle> getVertexToCircleMap() {
        return vertexToCircleMap;
    }*/
}
