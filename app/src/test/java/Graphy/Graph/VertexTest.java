package Graphy.Graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        Vertex vertex1 = new Vertex("1");
        Vertex vertex2 = new Vertex("2");
        Vertex vertex3 = new Vertex("1");

        assertNotEquals(vertex1, vertex2);
        assertEquals(vertex1, vertex3);
        assertEquals(vertex1, vertex1);
        assertNotEquals(vertex1, mockEdge);
    }
}