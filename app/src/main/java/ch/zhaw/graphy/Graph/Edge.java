package ch.zhaw.graphy.Graph;

import java.util.Objects;

public class Edge implements Comparable<Edge>{
    private final Vertex start;
    private final Vertex end;
    private final int weight;
    private static final int DEFAULT_WEIGHT = 0;


    public Edge(Vertex start, Vertex end, int weight){

        if (end ==null || start ==null) throw new IllegalArgumentException("End- or startVertex cannot be null");
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public Edge(Vertex start, Vertex end){

        this( start, end, DEFAULT_WEIGHT);
    }


    public int getWeight() {
        return weight;
    }

    public Vertex getEnd() {
        return end;
    }

    public Vertex getStart() {
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


