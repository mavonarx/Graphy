package ch.zhaw.graphy;

import ch.zhaw.graphy.Graph.Edge;
import ch.zhaw.graphy.Graph.GraphHandler;
import ch.zhaw.graphy.Graph.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * This class models all the data which is needed for the communication between
 * the GraphHandler and MainWindowController.
 * 
 * @author Tanja Aeberhardt, Nicolas Balke, Lukas Gruber, Matthias von Arx
 * @version 13.05.2022
 */
public class MainWindowModel {

    private List<MainWindowModelListener> mainWindowModelListeners = new ArrayList<>();
    private List<Vertex> displayVertex = new ArrayList<>();
    private List<Vertex> selectedVertex = new ArrayList<>();
    private List<Edge> displayEdges = new ArrayList<>();
    private List<Edge> selectedEdge = new ArrayList<>();

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

    /**
     * Removes all selected Vertex
     * clears the selected Vertex list and notifies all Listeners about the remove of selected Vertex.
     */
    public void removeSelectedDisplayVertex(){
        displayVertex.removeAll(selectedVertex);
        notifyOnRemoveSelectedVertex(selectedVertex);
        selectedVertex.clear();
    }

    /**
     * Removes all selected Edges
     * clears the selected Edge list and notifies all Listeners about the remove of selected Edges.
     */
    public void removeSelectedDisplayEdge(){
        displayEdges.removeAll(selectedEdge);
        notifyOnRemoveSelectedEdge(selectedEdge);
        selectedEdge.clear();
    }

    /**
     * Adds a new Edge to display and notifies all listeners about the add of the display edge.
     * @param edge to display.
     */
    public void addDisplayEdge(Edge edge){
        //handler.getGraph().get(edge.getStart()).add(edge);
        displayEdges.add(edge);
        notifyOnAddEdge(edge);
    }

    /**
     * Adds a new Edge to display selected and notifies all listeners about the add of the selected edge.
     * @param edge to select.
     */
    public void addSelectedEdge(Edge edge) {
        selectedEdge.add(edge);
        notifyOnSelectEdge(edge);
    }

    /**
     * Clears all selected edges and notifies about clear selected edge.
     */
    public void clearSelectedEdge(){
        selectedEdge.clear();
        notifyOnClearSelectedEdge();
    }

    /**
     * Adds a new Vertex to display and notifies all listeners about the add of the display vertex.
     * @param vertex to display.
     */
    public void addDisplayVertex(Vertex vertex) {
        //handler.addVertex(vertex);
        displayVertex.add(vertex);
        notifyOnAddVertex(vertex);
    }

    /**
     * Adds a new Vertex to display selected and notifies all listeners about the add of the selected vertex.
     * @param vertex to select.
     */
    public void addSelectedVertex(Vertex vertex) {
        selectedVertex.add(vertex);
        notifyOnSelectVertex(vertex);
    }

    /**
     * Clears all display vertex and edges.
     * Notifies about the clear of the display vertex list.
     */
    public void clearDisplayVertex() {
        displayVertex.clear();
        selectedVertex.clear();
        notifyOnClearVertex();
    }

    /**
     * Clears all selected vertex.
     * Notifies about the clear of the selected vertex list.
     */
    public void clearSelectedVertex() {
        selectedVertex.clear();
        notifyOnClearSelectedVertex();
    }

    /**
     * Register a new listener on the model.
     * @param listener that will be notified.
     */
    public void registerListener(MainWindowModelListener listener) {
        mainWindowModelListeners.add(listener);
    }

    /**
     * Notifies all listeners that a new vertex is added
     * @param newVertex to notify about
     */
    private void notifyOnAddVertex(Vertex newVertex) {
        for (MainWindowModelListener listener : mainWindowModelListeners) {
            listener.onAddVertex(newVertex);
        }
    }

    /**
     * Notifies all listeners that a vertex is selected
     * @param selectVertex to notify about
     */
    private void notifyOnSelectVertex(Vertex selectVertex) {
        for (MainWindowModelListener listener : mainWindowModelListeners) {
            listener.onSelectVertex(selectVertex);
        }
    }

    /**
     * Notifies all listeners that all vertex are removed
     */
    private void notifyOnClearVertex() {
        for (MainWindowModelListener listener : mainWindowModelListeners) {
            listener.onClearVertex();
        }
    }

    /**
     * Notifies all listeners that all selected vertex are removed
     */
    private void notifyOnClearSelectedVertex() {
        for (MainWindowModelListener listener : mainWindowModelListeners) {
            listener.onClearSelectedVertex();
        }
    }

    /**
     * Notifies all listeners that a new edge is added
     * @param newEdge to notify about
     */
    private void notifyOnAddEdge(Edge newEdge) {
        for (MainWindowModelListener listener : mainWindowModelListeners) {
            listener.onAddEdge(newEdge);
        }
    }

    /**
     * Notifies all listeners that a new edge is selected
     * @param selectEdge to notify about
     */
    private void notifyOnSelectEdge(Edge selectEdge) {
        for (MainWindowModelListener listener : mainWindowModelListeners) {
            listener.onSelectEdge(selectEdge);
        }
    }

    /**
     * Notifies all listeners that all selected edges are removed
     */
    private void notifyOnClearSelectedEdge() {
        for (MainWindowModelListener listener : mainWindowModelListeners) {
            listener.onClearSelectedEdge();
        }
    }

    /**
     * Notifies all listeners that a list of selected vertex are removed
     * @param removeVertex vertex to unselect
     */
    private void notifyOnRemoveSelectedVertex(List<Vertex> removeVertex){
        for (MainWindowModelListener listener : mainWindowModelListeners){
            listener.onRemoveSelectedVertex(removeVertex);
        }
    }

    /**
     * Notifies all listeners that a list of selected edges are removed
     * @param removeEdges edges to unselect
     */
    private void notifyOnRemoveSelectedEdge(List<Edge> removeEdges){
        for (MainWindowModelListener listener : mainWindowModelListeners){
            listener.onRemoveSelectedEdge(removeEdges);
        }
    }

    /**
     * Clears all lists the model handles.
     */
    public void clear(){
        displayVertex.clear();
        displayEdges.clear();
        selectedVertex.clear();
        selectedEdge.clear();
        notifyOnClearVertex();
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

    /**
     * Listener that notifies about changes in the model.
     */
    public interface MainWindowModelListener {
        /**
         * Notifies about added Vertex
         * @param newVertex added Vertex
         */
        void onAddVertex(Vertex newVertex);
        /**
         * Notifies about selected Vertex
         * @param selectedVertex selected Vertex
         */
        void onSelectVertex(Vertex selectedVertex);
        /**
         * Notifies about clear of all vertex
         */
        void onClearVertex();
        /**
         * Notifies about clear of all selected vertex
         */
        void onClearSelectedVertex();
        /**
         * Notifies about added Edge
         * @param newEdge added Edge
         */
        void onAddEdge(Edge newEdge);
        /**
         * Notifies about selected Edge
         * @param selectedEdge selected Edge
         */
        void onSelectEdge(Edge selectedEdge);
        /**
         * Notifies about clear of all selected Edges
         */
        void onClearSelectedEdge();
        /**
         * Notifies about remove of selected vertex
         * @param selectedVertex vertex to remove
         */
        void onRemoveSelectedVertex(List<Vertex> selectedVertex);
        /**
         * Notifies about remove of selected edge
         * @param selectedEdges edge to remove
         */
        void onRemoveSelectedEdge(List<Edge> selectedEdges);
    }
}