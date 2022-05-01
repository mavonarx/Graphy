package Graphy.Graph;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {

    @Mock
    private Vertex start;
    private Vertex end;
    private Vertex end2;

    Edge edge1 = new Edge(start,  end, 5);
    Edge edge2 = new Edge(start, end2, 1);

    @BeforeEach
    private void setUp(){
        MockitoAnnotations.openMocks(this);
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
        assertEquals(-4, edge1.compareTo(edge2));
    }
}