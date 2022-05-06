package ch.zhaw.graphy.Algorithms;

import java.util.*;

import ch.zhaw.graphy.Graph.Edge;
import ch.zhaw.graphy.Graph.Graph;
import ch.zhaw.graphy.Graph.Vertex;

public class MinimumSpanningTree extends Algorithm{

    public MinimumSpanningTree(BreadthFirstSearch bfs){
        super(bfs);
    }



    public Set<Edge> executeMST(Graph graph, Vertex start){
        //Use BFS to make sure the graph is fully connected

        checkIfConnected(graph, start);

        Set<Vertex> visited= new HashSet<>();
        Set<Edge> chosenEdges = new HashSet<>();
        visited.add(start);
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();

        priorityQueue.addAll(graph.getValueHandler().getGraph().get(start));

        while (visited.size()< graph.getValueHandler().getGraph().size()){
            Edge current = priorityQueue.poll();
            if (current==null){
                throw new IllegalStateException("chosen Edge is null");
            }
            chosenEdges.add(current);
            visited.add(current.getEnd());
            for(Edge edge : graph.getValueHandler().getGraph().get(current.getEnd())){
                if (!visited.contains(edge.getEnd()) && (!priorityQueue.contains(edge))){
                    priorityQueue.add(edge);
                }
            }
        }
        return chosenEdges;

    }
}