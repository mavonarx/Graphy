package Graphy.Algorithms;

import Graphy.Graph.Edge;
import Graphy.Graph.Graph;
import Graphy.Graph.Vertex;
//import com.google.common.graph.Graph;

import java.util.*;

public class Dijkstra extends  Algorithm{

    private final Map<Vertex, Integer> distances = new HashMap<>();
    private final PriorityQueue<Edge> prioQueue = new PriorityQueue<>();
    private final Map <Vertex, Vertex> predecessors = new HashMap<>();
    private final Set<Vertex> visited = new HashSet<>();
    private LinkedList<Vertex> resultPath = new LinkedList<>();
    private Vertex endVertex;



    /**
     * Executes a dijsktra algorithm, this searches for a shortest path in regards to the weights of the given edges
     * @param graph
     * @param startVertex
     */
    public LinkedList<Vertex> executeDijkstra(Graph graph, Vertex startVertex, Vertex finalVertex) throws IllegalArgumentException{

        // initializing all vertices with a distance of -1 (represent infinite distance)
        for (Vertex vertex: graph.getValueHandler().getGraph().keySet()){
            distances.put(vertex, -1);
        }

        // the source vertex has distance zero to itself and no predecessor
        distances.put(startVertex,0);
        predecessors.put(startVertex,null);

        // first add source to PriorityQueue
        prioQueue.addAll(graph.getValueHandler().getGraph().get(startVertex));

        while (!prioQueue.isEmpty()) {

            // currentEdge is removed from PriorityQueue and has min distance
            Edge currentEdge = prioQueue.poll();

            Vertex currentEnd = currentEdge.getEnd();
            Vertex currentStart = currentEdge.getStart();
            if (visited.contains(currentStart)) continue;

            // add edge to finalized list (visited)
            visited.add(currentStart);

            for (Edge edge : graph.getValueHandler().getGraph().get(currentStart)){

                /*
                if the distance is infinite or the distance stored is higher than the
                new calculated distance update the distance and predecessor
                */
                if (distances.get(currentEnd)==-1 || distances.get(currentEnd)>distances.get(currentStart)+ edge.getWeight()) {
                    distances.put(edge.getEnd(), edge.getWeight() + distances.get(currentStart));
                    predecessors.put(currentEnd,currentStart);
                }
            }

            // if the vertex is an endVertex and is cheaper than the current endVertex update the cheapest endVertex
            if(currentEnd.equals(finalVertex) && (this.endVertex == null || distances.get(this.endVertex)>distances.get(currentEnd))){
                this.endVertex = currentEnd;
            }
            prioQueue.addAll(graph.getValueHandler().getGraph().get(currentEnd));

        }

        // fill the list with all predecessors to the endpoint
        Vertex pointer = this.endVertex;
        while (pointer !=null){
            resultPath.addFirst(pointer);
            pointer = predecessors.get(pointer);
        }

        return resultPath;
    }
}
