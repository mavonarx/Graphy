package ch.zhaw.graphy;

import ch.zhaw.graphy.Graph.Vertex;

import java.util.ArrayList;
import java.util.List;

public class MainWindowModel {

    List<VertexListener> vertexListeners = new ArrayList<>();
    List<Vertex> displayVertex = new ArrayList<>();
    List<Vertex> selectedVertex = new ArrayList<>();

    public void addDisplayVertex(Vertex vertex){
        displayVertex.add(vertex);
        notifyOnAddVertex(vertex);
    }

    public void addSelectedVertex(Vertex vertex){
        selectedVertex.add(vertex);
    }
    public void addDisplayVertex(List<Vertex> vertex){
        displayVertex.addAll(vertex);
        //notifyOnAddVertex();
    }
    public void addSelectedVertex(List<Vertex> vertex){
        selectedVertex.addAll(vertex);
    }
    public void clearDisplayVertex(){
        displayVertex.clear();
    }
    public void clearSelectedVertex(){
        selectedVertex.clear();
    }

    public void registerVertexListener(VertexListener listener){
        vertexListeners.add(listener);
    }

    private void notifyOnAddVertex(Vertex newVertex){
        for (VertexListener listener : vertexListeners){
            listener.onAddVertex(newVertex);
        }
    }

    interface VertexListener{
        void onAddVertex(Vertex newVertex);
        void onSelectVertex(Vertex selectedVertex);
    }
}
