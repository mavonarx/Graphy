package ch.zhaw.graphy.Algorithms;

import java.util.*;

import ch.zhaw.graphy.Graph.Edge;
import ch.zhaw.graphy.Graph.GraphHandler;
import ch.zhaw.graphy.Graph.Vertex;

/**
 * Represents the Dijkstra algorithm. The Dijkstra shows the
 * shortest path on a weighted graph from a start to an end point.
 * 
 * @author Tanja Aeberhardt, Nicolas Balke, Lukas Gruber, Matthias von Arx
 * @version 13.05.2022
 */
public class Dijkstra{

    /**
     * Executes a Dijsktra algorithm, this searches for a shortest path in regards to the weights of the given edges
     * @param graph a graphHandler containing the graph map
     * @param source the vertex from which the dijkstra starts
     * @param finalVertex the vertex to which we path
     */
    public static Map<Vertex,Vertex> executeDijkstra(GraphHandler graph, Vertex source, Vertex finalVertex) throws IllegalArgumentException{
          Map<Vertex, Integer> distances = new HashMap<>();
          PriorityQueue<Edge> prioQueue = new PriorityQueue<>();
          Map <Vertex, Vertex> predecessors = new HashMap<>();
          Set<Vertex> visited = new HashSet<>();

        HashMap<Vertex,Vertex> resultMap = new HashMap<>();
        resultMap.put(source,null);
        if (source.equals(finalVertex)){
           return resultMap;
        }

        // initializing all vertices with a distance of -1 (represent infinite distance)
        for (Vertex vertex: graph.getGraph().keySet()){
            distances.put(vertex, -1);
        }

        // the source vertex has distance zero to itself and no predecessor
        distances.put(source,0);
        predecessors.put(source,null);

        // first add source to PriorityQueue
        prioQueue.addAll(graph.getGraph().get(source));

            while (!prioQueue.isEmpty()) {

                // currentEdge is removed from PriorityQueue and has min distance
                Edge currentEdge = prioQueue.poll();

                Vertex currentEnd = currentEdge.getEnd();
                Vertex currentStart = currentEdge.getStart();
                if (visited.contains(currentStart)) continue;

                // add edge to finalized list (visited)
                visited.add(currentStart);
                for (Edge edge : graph.getGraph().get(currentStart)) {

                /*
                if the distance is infinite or the distance stored is higher than the
                new calculated distance update the distance and predecessor
                */
                    if (distances.get(edge.getEnd()) == -1 || distances.get(edge.getEnd()) > distances.get(edge.getStart()) + edge.getWeight()) {
                        distances.put(edge.getEnd(), edge.getWeight() + distances.get(edge.getStart()));
                        predecessors.put(edge.getEnd(), edge.getStart());
                    }
                    prioQueue.addAll(graph.getGraph().get(edge.getEnd()));
                }
            }

        // fill the list with all predecessors to the endpoint
        Vertex pointer = finalVertex;
        while (pointer !=null && !(predecessors.get(finalVertex) == null)){
            resultMap.put(pointer,predecessors.get(pointer));
            pointer = predecessors.get(pointer);

        }
        if (resultMap.size()==1){
            throw new IllegalArgumentException("The given vertices are not connected");
        }
    return resultMap;
    }
}

