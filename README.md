# Graphy

## What is Graphy?

Graphy is a tool to draw graphs by yourself. It should help you get a feeling for abstract graphs. 
With graphy you can create your own graph and execute various algorithms on your own graph. You will get a visual 
feedback what happened.

### Prototype

This version of graphy is only the first prototype. In which 4 algorithms are available, graphs are drawable
and colorable by the weight of the edges, next to drawing graphs graphy can read CSV files with the correct 
format and print the drawn graphs into such files. 
All in all this tool is designed to be as intuitive to use as we could make it.

#### Future
As mentioned before this is only a prototype, and we built this tool in a way it should be quite easy to update.
- Algorithms have a superclass which can hold methods like the (checkIfConnected())
which could be used by multiple algorithms.
- EdgeGui and VertexGui are their own class which makes them easy to use or alter.
- Edge,Vertex and the GraphHandler are also in a way that it should be easy to change them.


## How to use the tool
If you launch the app you will start in the welcoming-Window. Here you can choose to either draw a graph directly
or launch it via a csv file. We provide two predefined example files which you can find in the /Examples folder.

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


## Our work structure

- From the beginning we all assigned us in the groups different tasks we would work on. 
A task should be a single logical issue/function or a well documented collective (e.g. Algorithms).
For each problem we add an issue, a single task can solve/work on multiple issues though.
- For our branching model: We create branches for any task that is more than a simple small addition.
These branches are well documented, commented and maintained.
- Directed commits to the main are ok if the commit is small and doesn't require an individual branch. 
- The most important rule is that the main branch is always functional. 
- In GitHub we made 3 issue templates: documentation, feature and bug. We use these along the different labels
to clarify our task-documentation.
- When a pull request is created a different team member has to approve it, 
- only then may the owner merge it into the main branch.

### Teamwork

- We meet regularly every 2-4 day depending on how the days fit.
- We keep in touch, discuss and solve problems, which we will encounter inevitably, together
and work towards a satisfying end product.
- Everyone creates branches and issues for their tasks, 
if more than one are working on the same issue they use the same GitHub issue.




## Classdiagramm

Here is a link to the class diagram [Class-diagram](Class-Diagramm.png)
