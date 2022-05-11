package ch.zhaw.graphy.Graph;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import javax.swing.text.Position;
import java.awt.*;
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
    private static final String UTF8_BOM = "\uFEFF";
    private static final String IS_DIRECTED = "1";
    Scanner scan;

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

        if (!file.getPath().endsWith(".csv")) {
            throw new IOException("Please provide a csv file");
        }

        scan = new Scanner(file);
        int nrOfVertices =0;
        try {
            checkIfDirected();
            nrOfVertices = getNrOfVertices();
            scanVertices(nrOfVertices);
            scanEdges();
        }

        catch (NumberFormatException e){
            System.out.println(e.getMessage());
            throw new IOException("File has not the correct format - Integer can not be parsed");
        }
        scan.close();
    }

    private void scanEdges() {
        while (scan.hasNextLine()){
            String scanEdgeString = scan.nextLine();
            if (scanEdgeString.isBlank()) continue;
            String [] values = scanEdgeString.split(DELIMITER);
            // scanning first vertex of edge
            int x = Integer.parseInt(values[1].strip());
            int y = Integer.parseInt(values[2].strip());
            Vertex v1 = new Vertex(values[0],new Point(x,y));

            //scanning second vertex of edge
            x = Integer.parseInt(values[4].strip());
            y = Integer.parseInt(values[5].strip());
            Vertex v2 = new Vertex(values[3],new Point(x,y));

            int weight = Integer.parseInt(values[6].strip());
            System.out.println(weight);
            Edge edge = new Edge(v1,v2,weight);
            addEdge(edge);
        }
    }

    private void scanVertices(int nrOfVertices) throws IOException {
        for (int i = 0; i< nrOfVertices; i++){
            if (!scan.hasNext()){
                throw new IOException("Not enough vertices provided");
            }
            String scanVertexString = scan.nextLine();
            String [] values = scanVertexString.split(DELIMITER);
            int x = Integer.parseInt(values[1].strip());
            int y = Integer.parseInt(values[2].strip());
            addVertex(new Vertex(values[0],new Point(x,y)));
        }
    }

    private int getNrOfVertices() throws IOException {
        int nrOfVertices;
        if (!scan.hasNext()){
            throw new IOException("No Nr# vertices is provided");
        }
        String nrOfVerticesString = scan.nextLine();
        nrOfVertices=Integer.parseInt(nrOfVerticesString.split(DELIMITER)[0].strip());
        return nrOfVertices;

    }

    private void checkIfDirected() throws IOException {
        if (!scan.hasNext()){
            throw new IOException("File is empty");
        }
        String startLine = scan.nextLine();
        if (startLine.startsWith(UTF8_BOM)) {
            startLine = startLine.substring(1);
        }
        isDirected = Integer.parseInt(startLine.split(DELIMITER)[0].strip())!=0;
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
        //TODO PLS SUPPORT ME
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
            br.write(IS_DIRECTED);
            br.write(System.lineSeparator());

            //write vertices
            for (Vertex vertex: graph.keySet()){
                br.write(graph.keySet().size()+ System.lineSeparator());
                br.write(String.join(
                                DELIMITER,List.of(
                                        String.valueOf(vertex),
                                        String.valueOf(vertex.getPosition().getX()),
                                        String.valueOf(vertex.getPosition().getY())
                                )
                        ) + System.lineSeparator()
                );
            }

            for (Vertex vertex : graph.keySet()) {
                for (Edge e : graph.get(vertex)) {
                    br.write(String.join(DELIMITER, List.of(
                            String.valueOf(e.getStart()),
                            String.valueOf(e.getStart().getPosition().getX()),
                            String.valueOf(e.getStart().getPosition().getY()),
                            String.valueOf(e.getEnd()),
                            String.valueOf(e.getEnd().getPosition().getX()),
                            String.valueOf(e.getEnd().getPosition().getY()),
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
