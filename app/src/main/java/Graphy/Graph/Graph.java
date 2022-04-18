package Graphy.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Graph{
    boolean isUndirected;
    private final Map<Node, Set<Edge>> adjacencySet = new HashMap<>();

    /**
     * creates an empty graph
     */
    public Graph(){
    }

    /**
     * creates a graph from a input file
     * @param file where an adjacencyList is represented. First line tells wheter its a directed or undirected graph
     *             any further line represents an edge (node, node, weight)
     * @throws FileNotFoundException if the file cannot be found
     * @throws IOException if the file has not the correct format
     */
    public Graph(File file) throws FileNotFoundException, IOException {
        //TODO node without edges cannot be read with this implementation. Extend or change it.

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
            String [] edgeArray = scannedLine.split(",");
            if (edgeArray.length!=3) throw new IOException("File has not the correct format");
            String start = edgeArray[0].strip();
            String end = edgeArray[1].strip();
            int weight = Integer.parseInt(edgeArray[2].strip());

            Node startNode = new Node(start);
            Node endNode = new Node(end);
            Edge edge = new Edge(startNode,endNode,weight);
            addEdge(edge);

            if (isUndirected){
                addEdge(new Edge(endNode,startNode,weight));
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

    void addNode(Node node){
        adjacencySet.put(node,new HashSet<>());
    }

    void removeNode(Node node) {
        adjacencySet.remove(node);
        throw new UnsupportedOperationException();
       //TODO iterate over all entries and delete every edge that has the node as start or endpoint.


    }

    public Map<Node, Set<Edge>> getAdjacencySet() {
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

    public List<Node> shortestPath(Node start, Node destination){
        if (isWeighted()){
            return dijkstra(start, destination);
        }
        else {
            return bfs(start, destination);
        }

    }

    private List<Node> dijkstra(Node start, Node destination){
        throw new UnsupportedOperationException();
    }

    private List<Node> bfs(Node start, Node destination){
        throw new UnsupportedOperationException();
    }

}
