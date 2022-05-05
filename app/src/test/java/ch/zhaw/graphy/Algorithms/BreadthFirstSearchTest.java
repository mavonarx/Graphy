package ch.zhaw.graphy.Algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;

import ch.zhaw.graphy.Algorithms.BreadthFirstSearch;
import ch.zhaw.graphy.Graph.Edge;
import ch.zhaw.graphy.Graph.Graph;
import ch.zhaw.graphy.Graph.Vertex;

import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BreadthFirstSearchTest {




    @Test
    void BFSTest(){
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        Graph graph = new Graph();
        Map<Vertex, Integer> expected = new HashMap<>();

        Vertex vertex1 = new Vertex("1");
        Vertex vertex2 = new Vertex("2");
        Vertex vertex3 = new Vertex("3");
        Vertex vertex4 = new Vertex("4");
        Vertex vertex5 = new Vertex("5");

        graph.getValueHandler().addVertex(vertex1);
        graph.getValueHandler().addVertex(vertex2);
        graph.getValueHandler().addVertex(vertex3);
        graph.getValueHandler().addVertex(vertex4);

        expected.put(vertex2, 2);
        expected.put(vertex3, 1);
        expected.put(vertex4, 1);

        graph.getValueHandler().addEdge(new Edge(vertex1, vertex3, 6));
        graph.getValueHandler().addEdge(new Edge(vertex1, vertex4, 1));
        graph.getValueHandler().addEdge(new Edge(vertex4, vertex2, 1));
        graph.getValueHandler().addEdge(new Edge(vertex2, vertex3, 1));

        assertEquals(expected, bfs.executeBFS(graph, vertex1));
    }
}
