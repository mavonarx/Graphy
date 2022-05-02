package Graphy.Graph;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * This class represents the values that are needed to display the application.
 * The values are represented as one map of vertices pointing to a list of edges which holds the outgoing edges.
 */
public class ValueHandler {

    MapProperty<Vertex, SimpleSetProperty<Edge>> graph = new SimpleMapProperty<>(FXCollections.observableHashMap());

    /**
     * Adds an edge to the graph. If the edge contains a vertex which is not yet part of the graph
     * they will be added as well.
     * @param edge to be added to the graph
     * @throws IllegalArgumentException if the given edge is null.
     */
    public void addEdge(Edge edge){
        if (edge==null) throw new IllegalArgumentException("Edge is null");
        addVertex(edge.getStart());
        addVertex(edge.getEnd());
        graph.get(edge.getStart()).add(edge);
    }

    /**
     * Adds a given vertex to the graph. The vertex is initialized with an empty adjacencyListProperty
     * If the graph already has the vertex nothing is done.
     * @param vertex to be added to the graph
     * @throws IllegalArgumentException if the given vertex is null
     */
    public void addVertex(Vertex vertex){
        if (vertex==null) throw new IllegalArgumentException("Vertex is null");

        if (graph.containsKey(vertex)){
            return;
        }

            SimpleSetProperty<Edge> adjacentSetProperty = new SimpleSetProperty<>(FXCollections.observableSet());
            adjacentSetProperty.addListener(new ChangeListener<ObservableSet<Edge>>() {
                /*if an adjacencyList  is changed the map representing the graph should also recognize a change.
                  To reach that goal we shortly change the list to null and then set it with the new value.
                  Then the graph will listen to changes to the lists as well.
                 */
                @Override
                public void changed(ObservableValue<? extends ObservableSet<Edge>> observable, ObservableSet<Edge> oldValue, ObservableSet<Edge> newValue) {
                    graph.put(vertex,null);
                    graph.put(vertex,new SimpleSetProperty<>(newValue));
                }
            });
            graph.put(vertex, adjacentSetProperty);
    }

    /**
     * Removes a given edge from the graph. The vertices remain as they were.
     * @param edge to be removed
     * @throws IllegalArgumentException if the edge is null
     */
    public void removeEdge(Edge edge){
        if (edge==null) throw new IllegalArgumentException("Edge is null");
        if (graph.containsKey(edge.getStart())){
            graph.get(edge.getStart()).remove(edge);
        }
    }

    /**
     * Removes the given vertex from the graph. Additionally also deletes every depending on edge.
     * @param vertex to be removed from the graph
     * @throws IllegalArgumentException if the given vertex is null
     */
    public void removeVertex(Vertex vertex){
        if (vertex==null) throw new IllegalArgumentException("Vertex is null");
        throw new UnsupportedOperationException();
    }


    public ObservableMap<Vertex, SimpleSetProperty<Edge>> getGraph() {
        return graph.get();
    }

    /**
     * Creates a list of vertices adjacent to the given vertex
     * @param vertex the vertex for which u want the adjacent vertices
     * @return's a list of all adjacent vertices
     */
    public List<Vertex> adjacentVertices(Vertex vertex){
        List<Vertex> adjacentList = new ArrayList<>();

        for (Edge edge : graph.get(vertex)){
            if (edge.getEnd().equals(vertex)) {
                adjacentList.add(edge.getStart());
            }else {adjacentList.add(edge.getEnd());
        }
    }
        return adjacentList;
    }
}
