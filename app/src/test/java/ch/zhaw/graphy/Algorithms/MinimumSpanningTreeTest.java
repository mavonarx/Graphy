package ch.zhaw.graphy.Algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;

import ch.zhaw.graphy.Algorithms.MinimumSpanningTree;
import ch.zhaw.graphy.Graph.Edge;
import ch.zhaw.graphy.Graph.Graph;
import ch.zhaw.graphy.Graph.Vertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.*;

public class MinimumSpanningTreeTest {

    @Test
    void testMST(){
        MinimumSpanningTree mst = new MinimumSpanningTree();
        Graph graph = new Graph();
        Set<Edge> expected = new HashSet<>();

        Vertex vertex1 = new Vertex("1");
        Vertex vertex2 = new Vertex("2");
        Vertex vertex3 = new Vertex("3");
        Vertex vertex4 = new Vertex("4");
        Vertex vertex5 = new Vertex("5");

        graph.getValueHandler().addVertex(vertex1);
        graph.getValueHandler().addVertex(vertex2);
        graph.getValueHandler().addVertex(vertex3);
        graph.getValueHandler().addVertex(vertex4);
        graph.getValueHandler().addVertex(vertex5);

        graph.getValueHandler().addEdge(new Edge(vertex1, vertex3, 6));
        graph.getValueHandler().addEdge(new Edge(vertex1, vertex4, 1));
        graph.getValueHandler().addEdge(new Edge(vertex4, vertex2, 1));
        graph.getValueHandler().addEdge(new Edge(vertex2, vertex3, 1));

        assertThrows(IllegalArgumentException.class, ()-> mst.executeMST(graph, vertex1));
        //assertEquals(expected, mst.executeMST(graph, vertex1));
    }
}
