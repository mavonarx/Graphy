package Graphy.Algorithms;

import Graphy.Graph.Edge;
import Graphy.Graph.Graph;
import Graphy.Graph.ValueHandler;
import Graphy.Graph.Vertex;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;


class DijkstraTest{

    @Mock
    private Graph mockGraph;
    @Mock
    private Vertex mockVertex1;
    @Mock
    private Vertex mockVertex2;
    @Mock
    private Vertex mockVertex3;
    @Mock
    private Vertex mockVertex4;
    @Mock
    private Vertex mockVertex5;
    @Mock
    private Edge mockEdge1;
    @Mock
    private Edge mockEdge2;
    @Mock
    private Edge mockEdge3;
    @Mock
    private Edge mockEdge4;
    @Mock
    private ValueHandler mockHandler;

    @BeforeEach
    private void setUp(){
        MockitoAnnotations.openMocks(this);

        MapProperty<Vertex, SimpleSetProperty<Edge>> mockMap = new SimpleMapProperty<>(FXCollections.observableHashMap());

        //Implementing a mockMap to work with
        mockMap.put(mockVertex1, new SimpleSetProperty<>(FXCollections.observableSet()));
        mockMap.get(mockVertex1).add(mockEdge1);
        mockMap.get(mockVertex1).add(mockEdge2);
        mockMap.put(mockVertex4, new SimpleSetProperty<>(FXCollections.observableSet()));
        mockMap.get(mockVertex4).add(mockEdge2);
        mockMap.get(mockVertex4).add(mockEdge3);
        mockMap.put(mockVertex2, new SimpleSetProperty<>(FXCollections.observableSet()));
        mockMap.get(mockVertex2).add(mockEdge3);
        mockMap.get(mockVertex2).add(mockEdge4);
        mockMap.put(mockVertex3, new SimpleSetProperty<>(FXCollections.observableSet()));
        mockMap.get(mockVertex3).add(mockEdge1);
        mockMap.get(mockVertex3).add(mockEdge4);
        mockMap.put(mockVertex5, new SimpleSetProperty<>(FXCollections.observableSet()));

        //SetUp mockEdges
        when(mockEdge1.getWeight()).thenReturn(6);
        when(mockEdge2.getWeight()).thenReturn(1);
        when(mockEdge3.getWeight()).thenReturn(1);
        when(mockEdge4.getWeight()).thenReturn(1);

        when(mockEdge1.getStart()).thenReturn(mockVertex1);
        when(mockEdge1.getEnd()).thenReturn(mockVertex3);
        when(mockEdge2.getStart()).thenReturn(mockVertex1);
        when(mockEdge2.getEnd()).thenReturn(mockVertex4);
        when(mockEdge3.getStart()).thenReturn(mockVertex4);
        when(mockEdge3.getEnd()).thenReturn(mockVertex2);
        when(mockEdge4.getStart()).thenReturn(mockVertex2);
        when(mockEdge4.getEnd()).thenReturn(mockVertex3);

        //Setup mockHandler
        when(mockHandler.getGraph()).thenReturn(mockMap);

        //Setup mockGraph
        when(mockGraph.getValueHandler()).thenReturn(mockHandler);

    }

    @Test
    void executeDijkstraTest() throws IOException {  //TODO this isnt stubbed/mocked to be removed before deploy

        Dijkstra dijkstra = new Dijkstra();
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

        LinkedList<Vertex> result = dijkstra.executeDijkstra(graph, vertex1, vertex3);

        assertEquals(expected, result);
    }

    @Test
    void dijkstraMockedTest(){
        Dijkstra dijkstra = new Dijkstra();
        LinkedList<Vertex> result = dijkstra.executeDijkstra(mockGraph, mockVertex1, mockVertex3);

        LinkedList<Vertex> testResult = new LinkedList<>();
        testResult.add(mockVertex1);
        testResult.add(mockVertex4);
        testResult.add(mockVertex2);
        testResult.add(mockVertex3);

        assertEquals(testResult, result);
    }

    @Test
    void verticesNotConnected(){
        Dijkstra dijkstra = new Dijkstra();

        assertThrows(IllegalArgumentException.class, ()-> dijkstra.executeDijkstra(mockGraph, mockVertex5, mockVertex1));
    }

    @Test
    void startEqualsEndVertex(){
        Dijkstra dijkstra = new Dijkstra();
        LinkedList<Vertex> result = dijkstra.executeDijkstra(mockGraph, mockVertex1, mockVertex1);

        LinkedList<Vertex> testResult = new LinkedList<>();
        testResult.add(mockVertex1);

        assertEquals(testResult, result);
    }
}