package ch.zhaw.graphy.Algorithms;

import ch.zhaw.graphy.Graph.Graph;
import ch.zhaw.graphy.Graph.Vertex;

public abstract class Algorithm {

    BreadthFirstSearch bfs;

    public Algorithm(BreadthFirstSearch bfs){
        this.bfs = bfs;
    }

    void checkIfConnected(Graph graph, Vertex start) {

        if (bfs.executeBFS(graph, start).size() != graph.getValueHandler().getGraph().size() - 1) { //TODO might run into problems when graph is only 1 Vertex
            throw new IllegalArgumentException("The graph for this start vertex isn't fully connected. " +
                    "MinimumSpanningTree can only be used on fully connected graphs");
        }
    }
}
