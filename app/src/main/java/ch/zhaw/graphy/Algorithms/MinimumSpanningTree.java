package ch.zhaw.graphy.Algorithms;

import java.util.*;

import ch.zhaw.graphy.Graph.Edge;
import ch.zhaw.graphy.Graph.GraphHandler;
import ch.zhaw.graphy.Graph.Vertex;

/**
 * Represents the Spanning Tree algorithm.
 * 
 * @author Tanja Aeberhardt, Nicolas Balke, Lukas Gruber, Matthias von Arx
 * @version 13.05.2022
 */
public class MinimumSpanningTree extends Algorithm{

    /**
     * The constructor for a mst (extending Algorithm superClass)
     *
     * @param bfs a {@link BreadthFirstSearch} most of the time uses a new object
     */
    public MinimumSpanningTree(BreadthFirstSearch bfs){
        super(bfs);
    }

    /**
     * Executes a MSt algorithm (prim). This looks for a collection of edges which connect all vertices and
     * with the lowest collective path cost
     *
     * @param graph a graphHandler containing a graph map
     * @param source a vertex from which you would like the mst to start
     * @return the set of edges connecting all vertices
     */
    public Set<Edge> executeMST(GraphHandler graph, Vertex source){

        //Use BFS to make sure the graph is fully connected e.g. if all vertices are reachable from the given source
        checkIfConnected(graph, source);

        Set<Vertex> visited= new HashSet<>();
        Set<Edge> chosenEdges = new HashSet<>();
        visited.add(source);
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();

        priorityQueue.addAll(graph.getGraph().get(source));

        while (visited.size()< graph.getGraph().size()){
            Edge current = priorityQueue.poll();
            if (current==null){
                throw new IllegalStateException("chosen Edge is null");
            }
            chosenEdges.add(current);
            visited.add(current.getEnd());
            for(Edge edge : graph.getGraph().get(current.getEnd())){
                if (!visited.contains(edge.getEnd()) && (!priorityQueue.contains(edge))){
                    priorityQueue.add(edge);
                }
            }
        }
        return chosenEdges;
    }
}
