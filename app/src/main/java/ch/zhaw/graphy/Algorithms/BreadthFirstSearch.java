package ch.zhaw.graphy.Algorithms;

import java.util.*;

import ch.zhaw.graphy.Graph.GraphHandler;
import ch.zhaw.graphy.Graph.Vertex;

/**
 * Represents the Breadth First Search (BFS) algorithm. The BFS shows the
 * shortest path on a unweighted graph from a start to an end point.
 * 
 * @author Tanja Aeberhardt, Nicolas Balke, Lukas Gruber, Matthias von Arx
 * @version 13.05.2022
 */
public class BreadthFirstSearch {
    // This map is used in the visualisation of the BFS and is set after executing a BFS
    private Map<Vertex, Vertex> visualMap = new HashMap<>();

    /**
     * From the given source gives the distance in amount of edges mapped to the vertex.
     *
     * @param graph a graphHandler containing the graph map
     * @param source the start vertex
     * @return's a map with the vertices for the path and the corresponding predecessor
     */
    public Map<Vertex, Integer> executeBFS(GraphHandler graph, Vertex source) {

        Map<Vertex, Integer> result = new HashMap<>();

        // Mark all the vertices as not visited(By default set as false)
        Set<Vertex> visited = new HashSet<>();
        Map<Vertex, Integer> distances = new HashMap<>();
        Map<Vertex,Vertex> predecessor = new HashMap<>();

        for (Vertex v : graph.getGraph().keySet()) {
            distances.put(v, -1);
        }

        // Create a queue for BFS
        Queue<Vertex> queue = new LinkedList<>();
        distances.put(source, 0);

        // Mark the current node as visited and enqueue it
        visited.add(source);
        queue.add(source);
        predecessor.put(source, null);

        while (!queue.isEmpty()) {
            // Dequeue a vertex from queue and print it
            Vertex current = queue.poll();

            // add all adjacent vertices to the queue
            for (Vertex end : graph.adjacentVertices(current)) {
                if (!visited.contains(end)) {

                    predecessor.put(end, current);
                    queue.add(end);
                    visited.add(end);
                    distances.put(end, 1 + distances.get(predecessor.get(end)));
                    result.put(end, distances.get(end));
                }
            }
        }
        this.visualMap = predecessor;
        return result;
    }

    /**
     * Returns a map with the visual vertices.
     * @return visual map
     */
    public Map<Vertex, Vertex> getVisualMap() {
        return visualMap;
    }
}