# Graphy

## What is Graphy?

Graphy is a tool to draw graphs by yourself. It should help you get a feeling for abstract graphs. 
With graphy you can create your own graph and execute various algorithms on your own graph. You will get a visual 
feedback what happened. 

## How to use the tool
If you launch the app you will start in the welcoming-Window. Here you can choose to either draw a graph directly
or launch it via a csv file. 

If you chose to launch it a new window will appear. Drag and drop your csv file in the white area.
Graphy checks your file and if it has the correct format you can launch it via the launch button.

When you successfully launched a file or chose to draw the graph directly you will get access to the main window where
the graphs are created and displayed. There you got the following two modes: 

### Modes

1. Build-mode (enabled when the checkbox for select-mode is unchecked)

In the build mode you can create a vertex by clicking on the white area. 
Optionally you can give it a name (for this just add a string in the vertex
name field before creating it). If no name is set, a default name 
(incrementing integer) is used. 

To create an edge between two vertices select both of them in the correct order
First start vertex, second end vertex. To give the edge a weight type an integer
before creating the edge. If no weight has been set the default weight 
(euclidean distance) between the two vertices is taken. 

2. Select-mode (enabled when the checkbox for select-mode is checked)
In the select mode you can select as many vertices and edges as you want. With the selection you can
remove the selected vertices and edges.

The select mode is also used to execute the algorithms.

- Dijkstra: Select 2 vertices in the correct order(start, end) and then select Dijkstra in the 
choose-algorithm-menu. The shortest path from the start to the end is calculated and all the traversed edges are marked 
green and the traversed vertices orange. 
The path cost for this traversal is minimal and is displayed at the bottom of the app.

- Dijkstra-via: Select 3 vertices in the correct order (first, via, end) and then select Dijkstra-via in the
chose-algorithm-menu. This will execute a dijkstra from the start vertex to 
the via vertex and then from the via vertex to the end vertex. Once again the traversed edges are marked green, 
the traversed vertices orange and the via vertex is displayed grey. The path cost is again minimal and displayed at the 
bottom of the page.

- BFS-Algorithm: Select 1 vertex and then select BFS from the menu. The shortest path (minimal number of 
vertices) is displayed. Attention! This algorithm ignores edge-weights. The path costs is the number of vertices
traversed. Not the addition of all the weights of the edges traversed. Finally, the used edges are displayed green.

- MST (minimal spanning tree algorithm): Select 1 vertex and then select MST from the menu. A minimal spanning tree
will be calculated and displayed. 

### Saving a graph

To save a created graph file simply click on the save button below. An output directory will be 
created where the graph-csv file will be stored. If you want to name the file, type in the wished
filename in the text field next to the save button (optionally with or without .csv extension). 
If no name is provided the default name is taken (graph.csv) .


## Classdiagramm

Here is a link to the class diagramm




   

