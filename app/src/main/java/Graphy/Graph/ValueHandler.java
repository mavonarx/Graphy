package Graphy.Graph;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import java.util.HashSet;

public class ValueHandler {
    MapProperty<Vertex, SimpleSetProperty<Edge>> graph = new SimpleMapProperty<>(FXCollections.observableHashMap());

    int counter;
    public ValueHandler(){


        graph.addListener(new ChangeListener<ObservableMap<Vertex, SimpleSetProperty<Edge>>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableMap<Vertex, SimpleSetProperty<Edge>>> observable, ObservableMap<Vertex, SimpleSetProperty<Edge>> oldValue, ObservableMap<Vertex, SimpleSetProperty<Edge>> newValue) {
                System.out.println("Map changed" + counter);
            }
        });

    }

    public void addEdge(Edge edge){
        counter ++;
        if (edge==null) throw new IllegalArgumentException("Edge is null");
        if (!graph.containsKey(edge.getStart())){
            addVertex(edge.getStart());
        }

        graph.get(edge.getStart()).add(edge);
    }


    public void addVertex(Vertex vertex){
        if (!graph.containsKey(vertex)){
            SimpleSetProperty<Edge> setProperty = new SimpleSetProperty<>(FXCollections.observableSet());
            setProperty.addListener(new ChangeListener<ObservableSet<Edge>>() {
                @Override
                public void changed(ObservableValue<? extends ObservableSet<Edge>> observable, ObservableSet<Edge> oldValue, ObservableSet<Edge> newValue) {
                    graph.put(vertex,null);
                    graph.put(vertex,new SimpleSetProperty<>(newValue));
                }
            });
            graph.put(vertex, setProperty);
        }
    }

    public void removeEdge(Edge edge){
        if (edge==null) throw new IllegalArgumentException("Edge is null");
        if (graph.containsKey(edge.getStart())){
            graph.get(edge.getStart()).remove(edge);
        }
    }

    public void removeVertex(){
        throw new UnsupportedOperationException();
    }

}
