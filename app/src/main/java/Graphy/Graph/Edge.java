package Graphy.Graph;

import java.util.Objects;

public class Edge implements Comparable<Edge>{
    private final Node start;
    private final Node  end;
    private final int weight;
    private static final int DEFAULT_WEIGHT = 0;


    public Edge(Node start, Node  end, int weight){
        if (end ==null || start ==null) throw new IllegalArgumentException("Endnote cannot be null");
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public Edge(Node start, Node end){
        this( start, end, DEFAULT_WEIGHT);
    }


    public int getWeight() {
        return weight;
    }

    public Node getEnd() {
        return end;
    }

    public Node getStart() {
        return start;
    }

    @Override
    public int compareTo(Edge other) {
        return this.weight-other.weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return weight == edge.weight && Objects.equals(end, edge.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(end, weight);
    }
}


