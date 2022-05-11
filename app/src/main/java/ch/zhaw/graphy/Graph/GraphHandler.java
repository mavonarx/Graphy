package ch.zhaw.graphy.Graph;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * This class represents the values that are needed to display the application.
 * The values are represented as one map of vertices pointing to a list of edges which holds the outgoing edges.
 */
public class GraphHandler {

    private static final Logger LOGGER = Logger.getLogger(GraphHandler.class.getCanonicalName());

    boolean isDirected;
    /**
     * The delimiter used in the graph csv file
     */
    private static final String DELIMITER = ",";
    /**
     * The UTF8_Bom - some utf8 files put this at the beginning to identify utf8 encoding
     */
    private static final String UTF8_BOM = "\uFEFF";
    /**
     * if a graph is directed it is represented as a 1 at the start of the csv file
     */
    private static final String IS_DIRECTED = "1";
    private Scanner scan;

    /**
     * The graph is represented as a map from a vertex to a set of edges.
     */
    MapProperty<Vertex, SimpleSetProperty<Edge>> graph = new SimpleMapProperty<>(FXCollections.observableHashMap());


    /**
     * Constructor: creates a graph from an csv-input file
     * @param file input csv file
     * @throws FileNotFoundException if the file cannot be found
     * @throws IOException if the file has not the correct format - the messages displays more information
     */
    public GraphHandler(File file) throws FileNotFoundException, IOException {
        LOGGER.fine("stared graphHandler constructor with file");

        //the .csv extension is required
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
            LOGGER.info("Constructed a graph out of CSV-File");
        }

        catch (NumberFormatException e){
            System.out.println(e.getMessage());
            String message =  "File has not the correct format - Integer can not be parsed";
            LOGGER.warning(message);
            throw new IOException(message);
        }
        scan.close();
        LOGGER.fine("Constructor with file successfully");
    }

    /**
     * scans the edges of the provided csv file
     * @throws IOException if a vertex is used that has not been initialized by scannVertices
     */
    private void scanEdges() throws IOException {
        LOGGER.fine("started scanning edges");
        while (scan.hasNextLine()){
            String scanEdgeString = scan.nextLine();
            if (scanEdgeString.isBlank()) continue;
            String [] values = scanEdgeString.split(DELIMITER);
            // scanning first vertex of edge
            int x = Integer.parseInt(values[1].strip());
            int y = Integer.parseInt(values[2].strip());
            Vertex v1 = new Vertex(values[0],new Point(x,y));
            if (!graph.containsKey(v1)){
                String message = "start vertex of an edge is not scanned as vertex";
                LOGGER.warning(message);
                throw new IOException(message);
            }

            //scanning second vertex of edge
            x = Integer.parseInt(values[4].strip());
            y = Integer.parseInt(values[5].strip());
            Vertex v2 = new Vertex(values[3],new Point(x,y));
            if (!graph.containsKey(v2)){
                String message = "end vertex of an edge is not scanned as vertex";
                LOGGER.warning(message);
                throw new IOException(message);
            }

            int weight = Integer.parseInt(values[6].strip());
            Edge edge = new Edge(v1,v2,weight);
            addEdge(edge);
            LOGGER.info("added the edge from" + values[0] + " to " + values[3]);
        }
        LOGGER.fine("scanned Edges successfully");
    }

    /**
     * Scans the vertices of the csv file
     * @param nrOfVertices the amount of vertices in the graph
     * @throws IOException if there are not enough vertices to be scanned.
     */
    private void scanVertices(int nrOfVertices) throws IOException {
        LOGGER.fine("stared to scan Vertices");
        for (int i = 0; i< nrOfVertices; i++){
            if (!scan.hasNext()){
                throw new IOException("Not enough vertices provided");
            }
            String scanVertexString = scan.nextLine();
            String [] values = scanVertexString.split(DELIMITER);
            int x = Integer.parseInt(values[1].strip());
            int y = Integer.parseInt(values[2].strip());
            addVertex(new Vertex(values[0],new Point(x,y)));
            LOGGER.info("Added the vertex: " + values[0] + "x:"+ x + "y:" + y);
        }
        LOGGER.fine("scanned vertices successfully");
    }

    /**
     * Scans the amount of vertices (provided at the second line of the csv file)
     * @return the naumber of Vertices
     * @throws IOException if there is no row in the file anymore
     */
    private int getNrOfVertices() throws IOException {
        LOGGER.fine("Stared scanning #nr vertices");
        int nrOfVertices;
        if (!scan.hasNext()){
            String message = "No Nr# vertices is provided";
            LOGGER.warning(message);
            throw new IOException(message);
        }
        String nrOfVerticesString = scan.nextLine();
        nrOfVertices=Integer.parseInt(nrOfVerticesString.split(DELIMITER)[0].strip());
        LOGGER.fine("scanned nr# vertices successfully");
        return nrOfVertices;
    }

    /**
     * reads from csv if the graph is directed or undirected
     * @throws IOException if the file is already at its end
     */
    private void checkIfDirected() throws IOException {
        LOGGER.fine("Started scanning if the graph is directed");
        if (!scan.hasNext()){
            String message = "File is empty";
            LOGGER.warning(message);
            throw new IOException(message);
        }
        String startLine = scan.nextLine();
        if (startLine.startsWith(UTF8_BOM)) {
            LOGGER.fine("UTF8-Bom detected");
            startLine = startLine.substring(1);
        }
        isDirected = Integer.parseInt(startLine.split(DELIMITER)[0].strip())!=0;
        LOGGER.fine("directed scanned successfully");
    }


    /**
     * constructor if no file is provided.
     */
    public GraphHandler(){}

    /**
     * Adds an edge to the graph. If the edge contains a vertex which is not yet part of the graph
     * they will be added as well.
     * @param edge to be added to the graph
     * @throws IllegalArgumentException if the given edge is null.
     */
    public void addEdge(Edge edge){
        if (edge==null) throw new IllegalArgumentException("Edge is null");
        LOGGER.info("Added an edge to the graph");
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
            LOGGER.warning("The vertex is already in the graph");
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
                LOGGER.fine("Change detected");
            }
        });
        graph.put(vertex, adjacentSetProperty);
        LOGGER.info("added a vertex to the graph");
    }

    /**
     * Removes a given edge from the graph. The vertices remain as they were.
     * @param edge to be removed
     * @throws IllegalArgumentException if the edge is null
     */
    public void removeEdge(Edge edge){
        if (edge==null) throw new IllegalArgumentException("Edge is null");
        if (graph.containsKey(edge.getStart())){
            if (graph.get(edge.getStart()).remove(edge)){
                LOGGER.info("removed edge");
            };
        }

    }

    /**
     * Removes the given vertex from the graph. Additionally also deletes every depending on edge.
     * @param vertex to be removed from the graph
     * @throws IllegalArgumentException if the given vertex is null
     */
    public void removeVertex(Vertex vertex){
        if (vertex==null) throw new IllegalArgumentException("Vertex is null");
        if (graph.containsKey(vertex)){
            LOGGER.info("removed vertex" + vertex);
        }
        for (SetProperty<Edge> setProperty :graph.values()){
           setProperty.remove(vertex);
        }

        graph.remove(vertex);

    }


    public ObservableMap<Vertex, SimpleSetProperty<Edge>> getGraph() {
        return graph.get();
    }

    /**
     * checks if the graph is weighted.
     * @return true if the graph is weighted. Else false
     */
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

    /**
     * takes the graph and converts it to a csv file. This file is saved in the output directory
     * (direct subdirectory of the project dir)
     *
     * @return true if the file could be saved, throws an exception otherwise
     * @throws IOException if the file could not be saved.
     */
    public boolean convertToCSV() throws IOException{
        LOGGER.fine("started converToCSV");
        if (graph.isEmpty()) return false;

        File file = initializeDirectoryStructure("Graph.csv");

        try (BufferedWriter br = Files.newBufferedWriter(file.toPath())) {
            file.createNewFile();
            LOGGER.info("created the File (if not yet existent)" + file.getName());

            /*the graph here is displayed as a directed graph.
            (if it was originally undirected it contains edges in both directions)
             */
            LOGGER.fine("writing isDirected");
            br.write(IS_DIRECTED);
            br.write(System.lineSeparator());

            //write vertices
            LOGGER.fine("writing number of edges");
            br.write(graph.keySet().size()+ System.lineSeparator());
            LOGGER.fine("writing vertices");
            for (Vertex vertex: graph.keySet()){

                br.write(String.join(
                                DELIMITER,List.of(
                                        String.valueOf(vertex),
                                        String.valueOf(vertex.getPosition().x()),
                                        String.valueOf(vertex.getPosition().y())
                                )
                        ) + System.lineSeparator()
                );
            }
            LOGGER.fine("vertices written");
            LOGGER.fine("writing edges");

            for (Vertex vertex : graph.keySet()) {
                for (Edge e : graph.get(vertex)) {
                    br.write(String.join(DELIMITER, List.of(
                            String.valueOf(e.getStart()),
                            String.valueOf(e.getStart().getPosition().x()),
                            String.valueOf(e.getStart().getPosition().y()),
                            String.valueOf(e.getEnd()),
                            String.valueOf(e.getEnd().getPosition().x()),
                            String.valueOf(e.getEnd().getPosition().y()),
                            String.valueOf(e.getWeight()))));
                    br.write(System.lineSeparator());
                }
            }
            LOGGER.fine("edges written");
        }
        return true;
    }

    /**
     * Initializes the output directory (if not already present)
     * @param fileName the name of the file that should be created
     * @return returns a FileObject of the file
     */
    private File initializeDirectoryStructure(String fileName){
        File output = new File("output");
        if (output.mkdirs()){
            LOGGER.info("created output directory");
        };

        return new File("output/" + fileName);
    }

    /**
     * Creates a list of vertices adjacent to the given vertex
     * @param vertex the vertex for which u want the adjacent vertices
     * @return a list of all adjacent vertices
     */
    public List<Vertex> adjacentVertices(Vertex vertex){
        List<Vertex> adjacentList = new ArrayList<>();

        for (Edge edge : graph.get(vertex)){
            adjacentList.add(edge.getEnd());
        }
        return adjacentList;
    }
}
