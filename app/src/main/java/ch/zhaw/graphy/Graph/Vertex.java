package ch.zhaw.graphy.Graph;


import java.util.Objects;

/**
 *
 */
public class Vertex {

    public static final int VERTEX_SIZE = 10;

    private Point position;
    String value;

    public void setPosition(Point position) {
        this.position = position;
    }

    public Vertex(String value, Point position){
        this.value=value;
        this.position = position;
    }

    public Vertex(String value){
        this.value=value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        return position.equals(vertex.position);
    }


    /*
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex otherVertex = (Vertex) o;
        if ((otherVertex.position != this.position)){
            return false;
        }
        return true;
    }*/

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
    @Override
    public int hashCode() {
        return Objects.hash(value);
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
