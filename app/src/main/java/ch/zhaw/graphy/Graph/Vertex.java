package ch.zhaw.graphy.Graph;


import ch.zhaw.graphy.graphObject;

import java.util.Objects;

/**
 *This class represents a vertex of a graph. It has a position and a name.
 */
public class Vertex implements graphObject {

    private Point position;
    private String name;

    /**
     * constructor of vertex with name and position
     * @param name
     * @param position
     */
    public Vertex(String name, Point position){
        this.name = name;
        this.position = position;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        return position.equals(vertex.position);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * ToString method of Vertex
     * @return the name of the vertex
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * generated hasCode
     * @return the hashcode value of this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public int getX(){
        return position.x();
    }

    public int getY(){
        return position.y();
    }

    public Point getPosition() {
        return position;
    }


}
