package ch.zhaw.graphy.Algorithms;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;

import ch.zhaw.graphy.Algorithms.Dijkstra;
import ch.zhaw.graphy.Graph.Edge;
import ch.zhaw.graphy.Graph.Graph;
import ch.zhaw.graphy.Graph.ValueHandler;
import ch.zhaw.graphy.Graph.Vertex;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.*;


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
    private Edge mockEdge13;
    @Mock
    private Edge mockEdge14;
    @Mock
    private Edge mockEdge42;
    @Mock
    private Edge mockEdge23;
    @Mock
    private ValueHandler mockHandler;

    MapProperty<Vertex, SimpleSetProperty<Edge>> mockMap;

    @BeforeEach
    private void setUp(){
        MockitoAnnotations.openMocks(this);

        mockMap = new SimpleMapProperty<>(FXCollections.observableHashMap());

        //Implementing a mockMap to work with
        mockMap.put(mockVertex1, new SimpleSetProperty<>(FXCollections.observableSet()));
        mockMap.get(mockVertex1).add(mockEdge13);
        mockMap.get(mockVertex1).add(mockEdge14);
        mockMap.put(mockVertex4, new SimpleSetProperty<>(FXCollections.observableSet()));
        mockMap.get(mockVertex4).add(mockEdge14);
        mockMap.get(mockVertex4).add(mockEdge42);
        mockMap.put(mockVertex2, new SimpleSetProperty<>(FXCollections.observableSet()));
        mockMap.get(mockVertex2).add(mockEdge42);
        mockMap.get(mockVertex2).add(mockEdge23);
        mockMap.put(mockVertex3, new SimpleSetProperty<>(FXCollections.observableSet()));
        mockMap.get(mockVertex3).add(mockEdge13);
        mockMap.get(mockVertex3).add(mockEdge23);
        mockMap.put(mockVertex5, new SimpleSetProperty<>(FXCollections.observableSet()));

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
        when(mockHandler.getGraph()).thenReturn(mockMap);

        //Setup mockGraph
        when(mockGraph.getValueHandler()).thenReturn(mockHandler);
    }

    @Test
    void dijkstraMockedTest(){
        Dijkstra dijkstra = new Dijkstra();

        LinkedList<Vertex> expected = new LinkedList<>();
        expected.add(mockVertex1);
        expected.add(mockVertex4);
        expected.add(mockVertex2);
        expected.add(mockVertex3);

        assertEquals(expected, dijkstra.executeDijkstra(mockGraph, mockVertex1, mockVertex3));
    }

    @Test
    void verticesNotConnected(){
        Dijkstra dijkstra = new Dijkstra();

        assertThrows(IllegalArgumentException.class, ()-> dijkstra.executeDijkstra(mockGraph, mockVertex5, mockVertex1));
    }

    @Test
    void startEqualsEndVertex(){
        Dijkstra dijkstra = new Dijkstra();

        LinkedList<Vertex> expected = new LinkedList<>();
        expected.add(mockVertex1);

        assertEquals(expected, dijkstra.executeDijkstra(mockGraph, mockVertex1, mockVertex1));
    }

    @Test
    void singleVertexTest(){
        Dijkstra dijkstra = new Dijkstra();

        MapProperty<Vertex, SimpleSetProperty<Edge>> mockMap = new SimpleMapProperty<>(FXCollections.observableHashMap());
        mockMap.put(mockVertex1, new SimpleSetProperty<>(FXCollections.observableSet()));

        LinkedList<Vertex> expected = new LinkedList<>();
        expected.add(mockVertex1);

        assertEquals(expected, dijkstra.executeDijkstra(mockGraph, mockVertex1, mockVertex1));
    }

    @Test
    void disconnectedGraph(){
        Dijkstra dijkstra = new Dijkstra();

        MapProperty<Vertex, SimpleSetProperty<Edge>> mockMap = new SimpleMapProperty<>(FXCollections.observableHashMap());
        mockMap.put(mockVertex1, new SimpleSetProperty<>(FXCollections.observableSet()));
        mockMap.put(mockVertex5, new SimpleSetProperty<>(FXCollections.observableSet()));

        LinkedList<Vertex> expected = new LinkedList<>();
        expected.add(mockVertex1);
        assertEquals(expected, dijkstra.executeDijkstra(mockGraph, mockVertex1, mockVertex1));
    }

    @Test
    void backwardsRouteOnDirectedGraph(){
        Dijkstra dijkstra = new Dijkstra();

        LinkedList<Vertex> expected = new LinkedList<>();

        assertThrows(IllegalArgumentException.class, ()-> dijkstra.executeDijkstra(mockGraph, mockVertex3, mockVertex1));
    }
}