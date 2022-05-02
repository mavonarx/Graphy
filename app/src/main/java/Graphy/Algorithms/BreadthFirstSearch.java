package Graphy.Algorithms;

import Graphy.Graph.Edge;
import Graphy.Graph.Graph;
import Graphy.Graph.ValueHandler;
import Graphy.Graph.Vertex;

import java.util.*;

public class BreadthFirstSearch {
    private LinkedList<Vertex> adj[];
    private ValueHandler handler = new ValueHandler();


    // prints BFS traversal from a given source s
    void BFS(Graph graph, Vertex source, Vertex finalVertex) {

        // Mark all the vertices as not visited(By default set as false)
        Set<Vertex> visited = new HashSet<>();
        Map<Vertex, Integer> distances = new HashMap<>();
        Map<Vertex,Vertex> predecessor = new HashMap<>();

        for (Vertex v : graph.getValueHandler().getGraph().keySet()){
            distances.put(v,-1);
        }

        // Create a queue for BFS
        Queue<Vertex> queue = new LinkedList<>();
        distances.put(source,0);
        // Mark the current node as visited and enqueue it
        visited.add(source);
        queue.add(source);
        predecessor.put(source,null);

        while (!queue.isEmpty()) {
            // Dequeue a vertex from queue and print it
            Vertex current  = queue.poll();

            //add all adjacent vertices to the queue
            for (Vertex end : graph.getValueHandler().adjacentVertices(current)){
                if (!visited.contains(end)){

                    predecessor.put(end,current);
                    queue.add(end);
                    visited.add(end);
                    distances.put(end,1+ distances.get(predecessor.get(end)));
                    System.out.println("visited: " + end + "with distance: " + distances.get(end));
                }
            }


        }
    }
}
