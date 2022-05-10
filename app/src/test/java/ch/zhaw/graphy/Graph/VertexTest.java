package ch.zhaw.graphy.Graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ch.zhaw.graphy.Graph.Edge;
import ch.zhaw.graphy.Graph.Vertex;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class VertexTest {

    @Mock
    Edge mockEdge;


    @BeforeEach
    private void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEqualsVertex() {
        Vertex vertex1 = new Vertex("1", new Point(1, 2));
        Vertex vertex2 = new Vertex("2", new Point(1, 2));
        Vertex vertex3 = new Vertex("1", new Point(2, 2));
        Vertex vertex4 = new Vertex("1", new Point(1, 2));
        
        assertNotEquals(vertex1, vertex3);
        assertEquals(vertex1, vertex1);
        assertEquals(vertex1, vertex4);
        assertNotEquals(vertex1, mockEdge);
    }


}