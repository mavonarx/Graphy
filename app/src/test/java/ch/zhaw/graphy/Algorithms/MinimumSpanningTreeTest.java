package ch.zhaw.graphy.Algorithms;

import ch.zhaw.graphy.Graph.ValueHandler;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
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
    private Edge mockEdge13;
    @Mock
    private Edge mockEdge14;
    @Mock
    private Edge mockEdge42;
    @Mock
    private Edge mockEdge23;
    @Mock
    private ValueHandler mockHandler;
    @Mock
    private BreadthFirstSearch mockBFS;

    MapProperty<Vertex, SimpleSetProperty<Edge>> mockGraphMap;
    Map<Vertex, Integer> mockBFSMap;

    @BeforeEach
    private void setUp(){
        MockitoAnnotations.openMocks(this);

        mockGraphMap = new SimpleMapProperty<>(FXCollections.observableHashMap());
        mockBFSMap = new HashMap<>();

        //Implementing a mockGraphMap to work with
        mockGraphMap.put(mockVertex1, new SimpleSetProperty<>(FXCollections.observableSet()));
        mockGraphMap.get(mockVertex1).add(mockEdge13);
        mockGraphMap.get(mockVertex1).add(mockEdge14);
        mockGraphMap.put(mockVertex4, new SimpleSetProperty<>(FXCollections.observableSet()));
        mockGraphMap.get(mockVertex4).add(mockEdge14);
        mockGraphMap.get(mockVertex4).add(mockEdge42);
        mockGraphMap.put(mockVertex2, new SimpleSetProperty<>(FXCollections.observableSet()));
        mockGraphMap.get(mockVertex2).add(mockEdge42);
        mockGraphMap.get(mockVertex2).add(mockEdge23);
        mockGraphMap.put(mockVertex3, new SimpleSetProperty<>(FXCollections.observableSet()));
        mockGraphMap.get(mockVertex3).add(mockEdge13);
        mockGraphMap.get(mockVertex3).add(mockEdge23);

        //Implementing a mockBFSMap
        mockBFSMap.put(mockVertex2, 2);
        mockBFSMap.put(mockVertex3, 1);
        mockBFSMap.put(mockVertex4, 1);

        //SetUp mockEdges
        when(mockEdge13.getWeight()).thenReturn(6);
        when(mockEdge14.getWeight()).thenReturn(1);
        when(mockEdge42.getWeight()).thenReturn(1);
        when(mockEdge23.getWeight()).thenReturn(1);

        //stub the edge getters
        when(mockEdge13.getStart()).thenReturn(mockVertex1);
        when(mockEdge13.getEnd()).thenReturn(mockVertex3);
        when(mockEdge14.getStart()).thenReturn(mockVertex1);
        when(mockEdge14.getEnd()).thenReturn(mockVertex4);
        when(mockEdge42.getStart()).thenReturn(mockVertex4);
        when(mockEdge42.getEnd()).thenReturn(mockVertex2);
        when(mockEdge23.getStart()).thenReturn(mockVertex2);
        when(mockEdge23.getEnd()).thenReturn(mockVertex3);

        //stub compareTo in edge
        when(mockEdge14.compareTo(mockEdge13)).thenReturn(-1);
        when(mockEdge23.compareTo(mockEdge13)).thenReturn(-1);
        when(mockEdge42.compareTo(mockEdge13)).thenReturn(-1);
        when(mockEdge13.compareTo(any())).thenReturn(1);

        //Setup mockHandler
        when(mockHandler.getGraph()).thenReturn(mockGraphMap);

        //Setup mockGraph
        when(mockGraph.getValueHandler()).thenReturn(mockHandler);

        //Setup BFS
        when(mockBFS.executeBFS(mockGraph, mockVertex1)).thenReturn(mockBFSMap);
        //doReturn(mockBFS).when(new BreadthFirstSearch());

    }

    @Test
    void testMSTUnmocked(){ //TODO: unmocked remove before deploy
        MinimumSpanningTree mst = new MinimumSpanningTree(new BreadthFirstSearch());
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

    @Test
    void testMST(){
        MinimumSpanningTree mst = new MinimumSpanningTree(mockBFS);

        Set<Edge> expected = new HashSet<>();
        expected.add(mockEdge14);
        expected.add(mockEdge23);
        expected.add(mockEdge42);

        assertEquals(expected, mst.executeMST(mockGraph, mockVertex1));
    }

    @Test
    void testNonConnectedGraph(){
        MinimumSpanningTree mst = new MinimumSpanningTree(mockBFS);

        mockGraphMap.put(mockVertex5, new SimpleSetProperty<>(FXCollections.observableSet()));

        assertThrows(IllegalArgumentException.class, ()-> mst.executeMST(mockGraph, mockVertex1));
    }
}
