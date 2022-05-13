package ch.zhaw.graphy.Graph;

import ch.zhaw.graphy.GraphObject;

import java.util.Objects;

/**
 * This class represents a vertex of a graph. It has a position and a name.
 * 
 * @author Tanja Aeberhardt, Nicolas Balke, Lukas Gruber, Matthias von Arx
 * @version 13.05.2022
 */
public class Vertex implements GraphObject {

    private Point position;
    private String name;

    /**
     * constructor of vertex with name and position
     * 
     * @param name
     * @param position
     */
    public Vertex(String name, Point position) {
        this.name = name;
        this.position = position;
    }

    /**
     * New equals. A vertex is equal if the position is equal
     *
     * @param other another object (preferably a vertex)
     * @return's a boolean true if the other object is a non-null vertex
     * and the position of the other vertex is the same
     */
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;

        Vertex vertex = (Vertex) other;

        return position.equals(vertex.position);
    }

    /**
     * ToString method of Vertex
     * 
     * @return the name of the vertex
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * generated hasCode
     * 
     * @return the hashcode value of this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return position.x();
    }

    public int getY() {
        return position.y();
    }

    public Point getPosition() {
        return position;
    }

}