package ch.zhaw.graphy.Graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GraphHandlerTest {

    GraphHandler handler = new GraphHandler();

    @Mock
    private Vertex mockVertex1;
    @Mock
    private Vertex mockVertex2;
    @Mock
    private Vertex mockVertex3;
    @Mock
    private Edge mockEdge1;
    @Mock
    private Edge mockEdge2;

    @BeforeEach
    private void init(){
        MockitoAnnotations.openMocks(this);
        mockEdge1 = new Edge(mockVertex1, mockVertex2);
        mockEdge2 = new Edge(mockVertex1, mockVertex3);
    }


    @Test
    void addEdgeTest() {
        handler.addEdge(mockEdge1);
        assertEquals(2, handler.graph.size());
    }

    @Test
    void getAdjacencyListTest() {
        handler.addEdge(mockEdge1);
        handler.addEdge(mockEdge2);

        Set<Edge> testSet = new HashSet<>();
        testSet.add(mockEdge1);
        testSet.add(mockEdge2);
        assertEquals(testSet, handler.graph.get(mockVertex1));
    }
}
