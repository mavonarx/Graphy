package ch.zhaw.graphy.Graph;


import java.awt.*;
import java.util.Objects;

public class Vertex {

    String value;
    Point position = new Point(0,0);

    public Vertex(String value){
        this.value=value;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(value, vertex.value);
    }

    public String getValue() {
        return value;
    }

    public int getX(){
        return position.x;
    }
    public int getY(){
        return position.y;
    }

    public Point getPosition(){
        return this.position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return value;
    }
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
