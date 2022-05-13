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

    private final Map<Vertex, Integer> distances = new HashMap<>();
    private final PriorityQueue<Edge> prioQueue = new PriorityQueue<>();
    private final Map <Vertex, Vertex> predecessors = new HashMap<>();
    private final Set<Vertex> visited = new HashSet<>();
    //private LinkedList<Vertex> resultPath = new LinkedList<>();
    private Vertex endVertex;

    /**
     * Executes a dijsktra algorithm, this searches for a shortest path in regards to the weights of the given edges
     * @param graph
     * @param startVertex
     */
    public Map<Vertex,Vertex> executeDijkstra(GraphHandler graph, Vertex startVertex, Vertex finalVertex) throws IllegalArgumentException{
        HashMap<Vertex,Vertex> resultMap = new HashMap<>();
        resultMap.put(startVertex,null);
        if (startVertex.equals(finalVertex)){
           return resultMap;
        }

        // initializing all vertices with a distance of -1 (represent infinite distance)
        for (Vertex vertex: graph.getGraph().keySet()){
            distances.put(vertex, -1);
        }

        // the source vertex has distance zero to itself and no predecessor
        distances.put(startVertex,0);
        predecessors.put(startVertex,null);

        // first add source to PriorityQueue
        prioQueue.addAll(graph.getGraph().get(startVertex));

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
                }

                // if the vertex is an endVertex and is cheaper than the current endVertex update the cheapest endVertex

                prioQueue.addAll(graph.getGraph().get(currentEnd));
            }

        // fill the list with all predecessors to the endpoint
        Vertex pointer = finalVertex;
        while (pointer !=null){
            resultMap.put(pointer,predecessors.get(pointer));
            pointer = predecessors.get(pointer);
        }

        if (resultMap.size()==1){
            throw new IllegalArgumentException("The given vertices are not connected");
        }

        for (Vertex vertex: resultMap.keySet()){
            System.out.println(resultMap.get(vertex));
        }
    return resultMap;
    }
}

