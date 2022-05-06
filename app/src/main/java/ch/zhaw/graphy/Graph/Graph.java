package ch.zhaw.graphy.Graph;

import javafx.beans.property.MapProperty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * This class represents the graph. The values of the graph are stored inside the valueHandler-class.
 * A graph can be directed or undirected. There are different ways of creating a graph.
 * 1. Creating a completely empty Graph
 * 2. With a properly filled out Graph-file
 * 3. With a list of edges.
 * For every
 */
public class Graph{
    boolean isDirected;
    ValueHandler valueHandler = new ValueHandler();
    Map<Vertex, List<Vertex>> adjacentVertices = new HashMap<>();

    /**
     * creates an empty graph
     */
    public Graph(){
    }

    /**
     * creates a graph from an input file
     * @param file where an adjacencyList is represented. First line tells whether
     *             it's a directed (!=0) or undirected (=0) graph
     *             any further line represents an edge (vertex, vertex, weight)
     * @throws FileNotFoundException if the file cannot be found
     * @throws IOException if the file has not the correct format
     */
    public Graph(File file) throws FileNotFoundException, IOException {

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
                valueHandler.addVertex(new Vertex(edgeArray[0]));
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
            valueHandler.addEdge(edge);

            if (!isDirected){
                valueHandler.addEdge(new Edge(endVertex, startVertex,weight));
            }

        }
        scan.close();
    }

    public Graph(List<Edge> edges){
        for (Edge edge : edges){
            valueHandler.addEdge(edge);
        }
    }

    public boolean isWeighted(){
       for (Vertex vertex : valueHandler.graph.keySet()){
           for (Edge edge : valueHandler.graph.get(vertex)){
               if (edge.getWeight()!=0){
                   return true;
               }
           }
       }
       return false;
    }

    public ValueHandler getValueHandler() {
        return valueHandler;
    }
}
