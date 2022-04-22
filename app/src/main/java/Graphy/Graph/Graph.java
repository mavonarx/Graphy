package Graphy.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Graph{
    boolean isUndirected;
    private final Map<Vertex, Set<Edge>> adjacencySet = new HashMap<>();

    /**
     * creates an empty graph
     */
    public Graph(){
    }

    /**
     * creates a graph from a input file
     * @param file where an adjacencyList is represented. First line tells whether it's a directed or undirected graph
     *             any further line represents an edge (vertex, vertex, weight)
     * @throws FileNotFoundException if the file cannot be found
     * @throws IOException if the file has not the correct format
     */
    public Graph(File file) throws FileNotFoundException, IOException {

        Scanner scan = new Scanner(file);
        if (scan.hasNextInt()){
            isUndirected = scan.nextInt() != 0;
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

            if (isUndirected){
                addEdge(new Edge(endVertex, startVertex,weight));
            }

        }
        scan.close();
    }

    public Graph(List<Edge> edges){
        for (Edge edge : edges){
            addEdge(edge);
        }
    }

    void addEdge(Edge edge){
        if (edge==null) throw new IllegalArgumentException("Edge is null");
        if (!adjacencySet.containsKey(edge.getStart())){
            adjacencySet.put(edge.getStart(), new HashSet<>());
        }

        adjacencySet.get(edge.getStart()).add(edge);
    }

    void removeEdge(Edge edge){
        if (edge==null) throw new IllegalArgumentException("Edge is null");
        if (adjacencySet.containsKey(edge.getStart())){
            adjacencySet.get(edge.getStart()).remove(edge);
        }
    }

    void addVertex(Vertex vertex){
        adjacencySet.put(vertex,new HashSet<>());
    }

    void removeVertex(Vertex vertex) {
        adjacencySet.remove(vertex);
        throw new UnsupportedOperationException();
       //TODO iterate over all entries and delete every edge that has the node as start or endpoint.


    }

    public Map<Vertex, Set<Edge>> getAdjacencySet() {
        return adjacencySet;
    }

    public boolean isWeighted() {
        for (Set<Edge> adjacentEdges : getAdjacencySet().values()){
            for (Edge edge : adjacentEdges){
                if (edge.getWeight()!=0) return true;
            }
        }
        return false;
    }


}
