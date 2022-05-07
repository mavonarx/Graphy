package ch.zhaw.graphy.Graph;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents the values that are needed to display the application.
 * The values are represented as one map of vertices pointing to a list of edges which holds the outgoing edges.
 */
public class GraphHandler {

    boolean isDirected;
    private static final String DELIMITER = ",";
    MapProperty<Vertex, SimpleSetProperty<Edge>> graph = new SimpleMapProperty<>(FXCollections.observableHashMap());


    /**
     * creates a graph from an input file
     * @param file where an adjacencyList is represented. First line tells whether
     *             it's a directed (!=0) or undirected (=0) graph
     *             any further line represents an edge (vertex, vertex, weight)
     * @throws FileNotFoundException if the file cannot be found
     * @throws IOException if the file has not the correct format
     */
    public GraphHandler(File file) throws FileNotFoundException, IOException {

        Scanner scan = new Scanner(file);
        if (scan.hasNextInt()){
            isDirected = scan.nextInt() != 0;
            scan.nextLine();
        }

        else {
            throw new IOException("File has not the correct format");
        }
        while(scan.hasNextLine()){
            String scannedLine = scan.nextLine();
            String [] edgeArray = scannedLine.split(",",3);

            //only a node is added to the graph
            if (edgeArray.length == 1){
                addVertex(new Vertex(edgeArray[0]));
                continue;
            }

            //an edge is added to the graph
            if (edgeArray.length!=3) throw new IOException("File has not the correct format");
            String start = edgeArray[0].strip();
            String end = edgeArray[1].strip();
            int weight = Integer.parseInt(edgeArray[2].strip());

            Vertex startVertex = new Vertex(start);
            Vertex endVertex = new Vertex(end);
            Edge edge = new Edge(startVertex, endVertex,weight);
            addEdge(edge);

            if (!isDirected){
                addEdge(new Edge(endVertex, startVertex,weight));
            }

        }
        scan.close();
    }

    //Todo can be removed if not used
    public GraphHandler(List<Edge> edges){
        for (Edge edge : edges){
            addEdge(edge);
        }
    }

    public GraphHandler(){}

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

    public boolean isWeighted(){
        for (Vertex vertex : graph.keySet()){
            for (Edge edge : graph.get(vertex)){
                if (edge.getWeight()!=0){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean convertToCSV() throws IOException{

        if (graph.isEmpty()) return false;

        File file = initializeDirectoryStructure("Graph.csv");

        try (BufferedWriter br = Files.newBufferedWriter(file.toPath())) {
            file.createNewFile();

            /*the graph here is displayed as a directed graph.
            (if it was originally undirected it contains edges in both directions)
             */
            br.write("1");
            br.write(System.lineSeparator());

            for (Vertex vertex : graph.keySet()) {
                if (graph.get(vertex).isEmpty()){
                    br.write((vertex + System.lineSeparator()));
                    continue;
                }
                for (Edge e : graph.get(vertex)) {


                    br.write(String.join(DELIMITER, List.of(
                            String.valueOf(e.getStart()),
                            String.valueOf(e.getEnd()),
                            String.valueOf(e.getWeight()))));
                    br.write(System.lineSeparator());
                }
            }
        }
        return true;
    }

    private File initializeDirectoryStructure(String fileName){
        File output = new File("app/src/output");
        output.mkdir();
        File file = new File("app/src/output/" + fileName);
        return file;
    }


    /**
     * Creates a list of vertices adjacent to the given vertex
     * @param vertex the vertex for which u want the adjacent vertices
     * @return's a list of all adjacent vertices
     */
    public List<Vertex> adjacentVertices(Vertex vertex){
        List<Vertex> adjacentList = new ArrayList<>();

        for (Edge edge : graph.get(vertex)){
            adjacentList.add(edge.getEnd());
    }
        return adjacentList;
    }
}
