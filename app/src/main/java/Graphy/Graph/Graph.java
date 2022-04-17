package Graphy.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Graph{
    boolean isUndirected;
    boolean hasNegativeWeights = false;
    boolean isWeighted = false; //if any of the weights is not equal to 0
    private final Map<Node, Set<Edge>> adjacencySet = new HashMap<>();

    public Graph(){

    }
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
            if (weight<0) hasNegativeWeights = true;
            if (weight!=0) isWeighted = true;
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
       //TODO iterate over all entries and delete every edge that has the node as start or endpoint.


    }

    public Map<Node, Set<Edge>> getAdjacencySet() {
        return adjacencySet;
    }
}
