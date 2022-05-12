package ch.zhaw.graphy.Graph;

import java.util.Objects;

/**
 * This class represents an edge in the graph. It has a start and an end vertex. And a weight.
 * if no weight is given a default value of 0 is taken.
 * 
 * @author Tanja Aeberhardt, Nicolas Balke, Lukas Gruber, Matthias von Arx
 * @version 13.05.2022
 */
public class Edge implements Comparable<Edge>{
    private final Vertex start;
    private final Vertex end;
    private final int weight;


    /**
     * Constructor for an edge.
     * @param start the start vertex
     * @param end the end vertex
     * @param weight the weight of the edge
     */
    public Edge(Vertex start, Vertex end, int weight){

        if (end ==null || start ==null) throw new IllegalArgumentException("End- or startVertex cannot be null");
        if (weight <= 0){
            weight = (int) Math.sqrt(Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getY() - end.getY() , 2));
        }
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    /**
     * constructor for edge with the default edge weight.
     * @param start start vertex
     * @param end end vertex
     */
    public Edge(Vertex start, Vertex end){
        this( start, end, 0);
    }

    /**
     *
     * @param other the object to be compared.
     * @return >0 if this edge-weight is bigger than the other edge-weight, 0 if equal weights,
     * <0 if this edge-weight is smaller than the other edge-weight
     */
    @Override
    public int compareTo(Edge other) {
        return this.weight-other.weight;
    }

    /**
     * equal iff the positions are equal.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return start.getPosition() == edge.start.getPosition() && end.getPosition() == edge.end.getPosition() && Objects.equals(end, edge.end);
    }

    /**
     * genereated hashcode
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(end, weight);
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
}


