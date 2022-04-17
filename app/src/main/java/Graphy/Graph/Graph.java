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
            if (!adjacencySet.containsKey(startNode)){
                adjacencySet.put(startNode, new HashSet<>());
            }
            adjacencySet.get(startNode).add(edge);
        }
        scan.close();

    }

    public Map<Node, Set<Edge>> getAdjacencySet() {
        return adjacencySet;
    }
}
