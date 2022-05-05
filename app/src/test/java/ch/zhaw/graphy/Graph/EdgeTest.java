package ch.zhaw.graphy.Graph;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ch.zhaw.graphy.Graph.Edge;
import ch.zhaw.graphy.Graph.Vertex;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {

    @Mock
    private Vertex start;
    @Mock
    private Vertex end;
    @Mock
    private Vertex end2;

    Edge edge1;
    Edge edge2;


    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        edge2 = new Edge(start, end2, 1);
        edge1 = new Edge(start, end, 5);
    }

    @Test
    void getWeight() {
        assertEquals(5, edge1.getWeight());
    }

    @Test
    void getEnd() {
        assertEquals(end, edge1.getEnd());
    }

    @Test
    void compareTo() {
        assertEquals(4, edge1.compareTo(edge2));
    }
}