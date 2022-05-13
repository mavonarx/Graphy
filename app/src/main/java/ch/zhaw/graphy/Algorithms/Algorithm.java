package ch.zhaw.graphy.Algorithms;


import ch.zhaw.graphy.Graph.GraphHandler;
import ch.zhaw.graphy.Graph.Vertex;

/**
 * @author Tanja Aeberhardt, Nicolas Balke, Lukas Gruber, Matthias von Arx
 * @version 13.05.2022
 */
public abstract class Algorithm {

    BreadthFirstSearch bfs;

    /**
     * Constructor for algorithm.
     * @param bfs given bfs algorithm
     */
    public Algorithm(BreadthFirstSearch bfs){
        this.bfs = bfs;
    }

    void checkIfConnected(GraphHandler graph, Vertex start) {

        if (bfs.executeBFS(graph, start).size() != graph.getGraph().size() - 1) { //TODO might run into problems when graph is only 1 Vertex
            throw new IllegalArgumentException("The graph for this start vertex isn't fully connected. " +
                    "MinimumSpanningTree can only be used on fully connected graphs");
        }
    }
}
