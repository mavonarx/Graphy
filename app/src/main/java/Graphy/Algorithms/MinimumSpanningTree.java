package Graphy.Algorithms;

import Graphy.Graph.Edge;
import Graphy.Graph.Graph;
import Graphy.Graph.ValueHandler;
import Graphy.Graph.Vertex;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.ObservableMap;

import java.util.*;

public class MinimumSpanningTree {
    public static Set<Edge> run(ObservableMap<Vertex, SimpleSetProperty<Edge>> graph, Vertex start){
        Set<Vertex> visited= new HashSet<>();
        Set<Edge> chosenEdges = new HashSet<>();
        visited.add(start);
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();

        priorityQueue.addAll(graph.get(start));


        //TODO: check if the graph is connected.

        while (visited.size()< graph.size()){
            Edge current = priorityQueue.poll();
            if (current==null){
                throw new IllegalStateException("chosen Edge is null");
            }
            chosenEdges.add(current);
            visited.add(current.getEnd());
            for(Edge edge : graph.get(current.getEnd())){
                if (!visited.contains(edge.getEnd()) && (!priorityQueue.contains(edge))){
                    priorityQueue.add(edge);
                }
            }


        }


        return chosenEdges;

    }
}
