package ch.zhaw.graphy.Algorithms;

import ch.zhaw.graphy.Graph.GraphHandler;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;

import ch.zhaw.graphy.Graph.Edge;
import ch.zhaw.graphy.Graph.Vertex;

import static org.mockito.Mockito.*;

import java.util.*;

public class BreadthFirstSearchTest {
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
    private GraphHandler mockHandler;
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

        //MockLists used for adjacentVertex method
        List<Vertex> mockList1 = new ArrayList<>();
        List<Vertex> mockList2 = new ArrayList<>();
        List<Vertex> mockList3 = new ArrayList<>();
        List<Vertex> mockList4 = new ArrayList<>();
        mockList1.add(mockVertex3);
        mockList1.add(mockVertex4);
        mockList2.add(mockVertex3);
        mockList4.add(mockVertex2);

        //Setup mockHandler getGraph method
        when(mockHandler.getGraph()).thenReturn(mockMap);

        //Setup mockHandler adjacentVertex method
        when(mockHandler.adjacentVertices(mockVertex1)).thenReturn(mockList1);
        when(mockHandler.adjacentVertices(mockVertex2)).thenReturn(mockList2);
        when(mockHandler.adjacentVertices(mockVertex4)).thenReturn(mockList4);
    }

    @Test
    void testBFS(){
        BreadthFirstSearch bfs = new BreadthFirstSearch();

        Map<Vertex, Integer> expected = new HashMap<>();

        expected.put(mockVertex2, 2);
        expected.put(mockVertex3, 1);
        expected.put(mockVertex4, 1);

        assertEquals(expected, bfs.executeBFS(mockHandler, mockVertex1));
    }

    @Test
    void countAdjacentVerticesCalls(){
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        bfs.executeBFS(mockHandler, mockVertex1);

        verify(mockHandler, times(1)).adjacentVertices(mockVertex1);
        verify(mockHandler, times(1)).adjacentVertices(mockVertex2);
        verify(mockHandler, times(1)).adjacentVertices(mockVertex3);
        verify(mockHandler, times(1)).adjacentVertices(mockVertex4);
        verify(mockHandler, times(0)).adjacentVertices(mockVertex5);
    }

    @Test
    void testResultMap(){
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        bfs.executeBFS(mockHandler, mockVertex1);

        Map<Vertex, Vertex> expected = new HashMap<>();
        expected.put(mockVertex1, null);
        expected.put(mockVertex4, mockVertex1);
        expected.put(mockVertex3, mockVertex1);
        expected.put(mockVertex2, mockVertex4);

        assertEquals(expected, bfs.getVisualMap());
    }

    @Test
    void withVertexNotConnected(){
        BreadthFirstSearch bfs = new BreadthFirstSearch();

        mockMap.put(mockVertex5, new SimpleSetProperty<>(FXCollections.observableSet()));

        Map<Vertex, Integer> expected = new HashMap<>();

        expected.put(mockVertex2, 2);
        expected.put(mockVertex3, 1);
        expected.put(mockVertex4, 1);

        assertEquals(expected, bfs.executeBFS(mockHandler, mockVertex1));
    }

    @Test
    void only1Vertex(){
        BreadthFirstSearch bfs = new BreadthFirstSearch();

        MapProperty<Vertex, SimpleSetProperty<Edge>> singleVertexMap = new SimpleMapProperty<>(FXCollections.observableHashMap());
        singleVertexMap.put(mockVertex1, new SimpleSetProperty<>(FXCollections.observableSet()));
        List<Vertex> singleVertexList = new ArrayList<>();

        when(mockHandler.getGraph()).thenReturn(singleVertexMap);
        when(mockHandler.adjacentVertices(mockVertex1)).thenReturn(singleVertexList);

        Map<Vertex, Integer> expected = new HashMap<>();

        assertEquals(expected, bfs.executeBFS(mockHandler, mockVertex1));
    }
}
