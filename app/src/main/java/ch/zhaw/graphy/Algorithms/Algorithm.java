package ch.zhaw.graphy.Algorithms;


import ch.zhaw.graphy.Graph.GraphHandler;
import ch.zhaw.graphy.Graph.Vertex;

/**
 * A algorithm superClass mostly for future use in algorithm with overlapping needs
 * @author Tanja Aeberhardt, Nicolas Balke, Lukas Gruber, Matthias von Arx
 * @version 13.05.2022
 */
public abstract class Algorithm {

    BreadthFirstSearch bfs;

    /**
     * Constructor for algorithm.
     *
     * @param bfs given bfs algorithm
     */
    public Algorithm(BreadthFirstSearch bfs){
        this.bfs = bfs;
    }

    /**
     * Uses a BFS algorithm to check if the graph is connected from that source
     * e.g. if all vertices are reachable from the source
     *
     * @param graph a graphHandler containing a graph map
     * @param source the vertex from which all other vertices should be reachable
     */
    void checkIfConnected(GraphHandler graph, Vertex source) {

        if (bfs.executeBFS(graph, source).size() != graph.getGraph().size() - 1) {
            throw new IllegalArgumentException("The graph for this source vertex isn't fully connected. " +
                    "MinimumSpanningTree can only be used on fully connected graphs");
        }
    }
}
