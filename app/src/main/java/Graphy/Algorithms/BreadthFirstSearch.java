package Graphy.Algorithms;

import Graphy.Graph.ValueHandler;
import Graphy.Graph.Vertex;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class BreadthFirstSearch {
    private LinkedList<Vertex> adj[];
    private ValueHandler handler = new ValueHandler();


    // prints BFS traversal from a given source s
    void BFS(Vertex source, Vertex finalVertex) {

        // Mark all the vertices as not visited(By default set as false)
        Map<Vertex, Boolean> visited = new HashMap<>();
        for (Vertex vertex : handler.getGraph().keySet()){
            visited.put(vertex, false);
        }

        // Create a queue for BFS
        LinkedList<Vertex> queue = new LinkedList<Vertex>();

        // Mark the current node as visited and enqueue it
        visited.put(source, true);
        queue.add(source);

        while (queue.size() != 0)
        {
            // Dequeue a vertex from queue and print it
            source = queue.poll();
            System.out.print(source+" ");

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<Vertex> i = adj[source].listIterator();
            while (i.hasNext())
            {
                Vertex next = i.next();
                if (!visited.get(next))
                {
                    visited.put(next, true);
                    queue.add(next);
                }
            }
        }
    }
}
