package Graphy.Graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

public class ValueHandlerTest {

    ValueHandler handler = new ValueHandler();

    @Mock
    private Vertex mockVertex1;
    private Vertex mockVertex2;
    private Vertex mockVertex3;
    private Edge mockEdge1;
    private Edge mockEdge2;

    @BeforeEach
    private void init(){
        MockitoAnnotations.openMocks(this);
        ValueHandler handler = new ValueHandler();
    }


    @Test
    void addEdgeTest() {
        handler.addEdge(mockEdge1);
        assertEquals(1, handler.graph.size());
    }

    @Test
    void getAdjacencyListTest() {
        handler.addEdge(mockEdge1);
        handler.addEdge(mockEdge2);
        assertEquals(2, handler.graph.get(mockVertex1));
    }
}
