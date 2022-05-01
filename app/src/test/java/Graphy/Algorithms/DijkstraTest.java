package Graphy.Algorithms;

import Graphy.Graph.Edge;
import Graphy.Graph.Graph;
import Graphy.Graph.ValueHandler;
import Graphy.Graph.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.FileHandler;


class DijkstraTest{

    //File testGraph = new File("DijkstraTestGraph");

    @Mock
    private Graph mockGraph;
    @Mock
    private Vertex mockVertex;
    @Mock
    private Edge mockEdge;
    @Mock
    private ValueHandler mockHandler;

    @BeforeEach
    private void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executeDijkstraTest() throws IOException {

        Dijkstra dijkstra = new Dijkstra();
        Graph graph = new Graph();
        ValueHandler handler = new ValueHandler();
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

        LinkedList<Vertex> result = dijkstra.executeDijkstra(graph, vertex1, vertex3);

        assertEquals(expected, result);
    }
}
