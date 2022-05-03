package Graphy.Algorithms;

import Graphy.Graph.Edge;
import Graphy.Graph.Graph;
import Graphy.Graph.Vertex;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;

public class BreadthFirstSearchTest {




    @Test
    void BFSTest(){
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        Graph graph = new Graph();
        LinkedList<Vertex> expected = new LinkedList<>();

        Vertex vertex1 = new Vertex("1");
        Vertex vertex2 = new Vertex("2");
        Vertex vertex3 = new Vertex("3");
        Vertex vertex4 = new Vertex("4");

        graph.getValueHandler().addVertex(vertex1);
        graph.getValueHandler().addVertex(vertex2);
        graph.getValueHandler().addVertex(vertex3);
        graph.getValueHandler().addVertex(vertex4);

        expected.add(vertex1);
        expected.add(vertex4);
        expected.add(vertex2);
        expected.add(vertex3);

        graph.getValueHandler().addEdge(new Edge(vertex1, vertex3, 6));
        graph.getValueHandler().addEdge(new Edge(vertex1, vertex4, 1));
        graph.getValueHandler().addEdge(new Edge(vertex4, vertex2, 1));
        graph.getValueHandler().addEdge(new Edge(vertex2, vertex3, 1));


        assertEquals(expected, bfs.executeBFS(graph, vertex1, vertex3));
    }
}
