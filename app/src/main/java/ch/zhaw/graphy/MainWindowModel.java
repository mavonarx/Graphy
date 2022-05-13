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

/**
 * This class models all the data which is needed for the communication between
 * the GraphHandler and MainWindowController.
 * 
 * @author Tanja Aeberhardt, Nicolas Balke, Lukas Gruber, Matthias von Arx
 * @version 13.05.2022
 */
public class MainWindowModel {

    private GraphHandler handler;
    private List<VertexListener> vertexListeners = new ArrayList<>();
    private List<Vertex> displayVertex = new ArrayList<>();
    private List<Vertex> selectedVertex = new ArrayList<>();
    private List<Edge> displayEdges = new ArrayList<>();
    private List<Edge> selectedEdge = new ArrayList<>();

    /**
     * Constructor for MainWindowModel. 
     * @param handler given handler
     */
    public MainWindowModel(GraphHandler handler) {
        this.handler = handler;
    }

    /**
     * Verifies if the selected vertex isn't empty.
     * @return true, if vertex isn't empty
     */
    public boolean hasSelectedVertex() {
        return !selectedVertex.isEmpty();
    }

    /**
     * Verifies if the selected edge isn't empty.
     * @return true, if edge isn't empty
     */
    public boolean hasSelectedEdge() {
        return !selectedEdge.isEmpty();
    }

    public void removeSelectedDisplayVertex(){
        displayVertex.removeAll(selectedVertex);
        notifyOnRemoveSelectedVertex(selectedVertex);
        selectedVertex.clear();
    }
    public void removeSelectedDisplayEdge(){
        displayEdges.removeAll(selectedEdge);
        notifyOnRemoveSelectedEdge(selectedEdge);
        selectedEdge.clear();
    }

    public void addDisplayEdge(Edge edge){
        handler.getGraph().get(edge.getStart()).add(edge);
        displayEdges.add(edge);
        notifyOnAddEdge(edge);
    }

    public void addSelectedEdge(Edge edge) {
        selectedEdge.add(edge);
        notifyOnSelectEdge(edge);
    }

    public void clearSelectedEdge(){
        selectedEdge.clear();
        notifyOnClearSelectedEdge();
    }

    public void addDisplayVertex(Vertex vertex) {
        handler.addVertex(vertex);
        displayVertex.add(vertex);
        notifyOnAddVertex(vertex);
    }

    public void addSelectedVertex(Vertex vertex) {
        selectedVertex.add(vertex);
        notifyOnSelectVertex(vertex);
    }

    public void clearDisplayVertex() {
        displayVertex.clear();
        selectedVertex.clear();
        notifyOnClearVertex();
    }

    public void clearSelectedVertex() {
        selectedVertex.clear();
        notifyOnClearSelectedVertex();
    }


    public void registerVertexListener(VertexListener listener) {
        vertexListeners.add(listener);
    }

    private void notifyOnAddVertex(Vertex newVertex) {
        for (VertexListener listener : vertexListeners) {
            listener.onAddVertex(newVertex);
        }
    }

    private void notifyOnSelectVertex(Vertex selectVertex) {
        for (VertexListener listener : vertexListeners) {
            listener.onSelectVertex(selectVertex);
        }
    }

    private void notifyOnClearVertex() {
        for (VertexListener listener : vertexListeners) {
            listener.onClearVertex();
        }
    }

    private void notifyOnClearSelectedVertex() {
        for (VertexListener listener : vertexListeners) {
            listener.onClearSelectedVertex();
        }
    }

    private void notifyOnAddEdge(Edge newEdge) {
        for (VertexListener listener : vertexListeners) {
            listener.onAddEdge(newEdge);
        }
    }

    private void notifyOnSelectEdge(Edge newEdge) {
        for (VertexListener listener : vertexListeners) {
            listener.onSelectEdge(newEdge);
        }
    }

    private void notifyOnClearSelectedEdge() {
        for (VertexListener listener : vertexListeners) {
            listener.onClearSelectedEdge();
        }
    }

    private void notifyOnRemoveSelectedVertex(List<Vertex> removeVertex){
        for (VertexListener listener : vertexListeners){
            listener.onRemoveSelectedVertex(removeVertex);
        }
    }

    private void notifyOnRemoveSelectedEdge(List<Edge> removeEdges){
        for (VertexListener listener : vertexListeners){
            listener.onRemoveSelectedEdge(removeEdges);
        }
    }

    public void clear(){
        displayVertex.clear();
        displayEdges.clear();
        selectedVertex.clear();
        selectedEdge.clear();
    }

    interface VertexListener{
        void onAddVertex(Vertex newVertex);
        void onSelectVertex(Vertex selectedVertex);
        void onClearVertex();
        void onClearSelectedVertex();
        void onAddEdge(Edge newEdge);
        void onSelectEdge(Edge selectedEdge);
        void onClearSelectedEdge();
        void onRemoveSelectedVertex(List<Vertex> selectedVertex);
        void onRemoveSelectedEdge(List<Edge> selectedEdges);
    }

    /**
     * Returns a list of the selected vertices.
     * @return selected vertex list
     */
    public List<Vertex> getSelectedVertex() {
        return selectedVertex;
    }

    /**
     * Returns a list of the selected edges.
     * @return selected edges list
     */
    public List<Edge> getSelectedEdge() {
        return selectedEdge;
    }

    /*
     * public Map<Edge, Line> getEdgeToLineMap() {
     * return edgeToLineMap;
     * }
     * 
     * public Map<Vertex, Circle> getVertexToCircleMap() {
     * return vertexToCircleMap;
     * }
     */
}