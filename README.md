# Graphy

### How to create a correct csv graph file
The csv file has to be structured to be 
correctly interpreted: Here we provide a step by step
manual to create one. 

1. create an empty file with a .csv extension
2. in the very first line there has to be an indicator
if the graph is directed. 
   - If the graph is directed put a 1
   - If the graph is undirected put a 0
3. Set the number of vertices as an int
4. In the following lines there are the #nr vertices 
5. Put a vertex as follows: vertexName, x-coordinate as int, y-coordinate as int (attention! only use each coordinate tuple once!)
6. Repeat this as many times as #nr vertices put in the second line. 
7. After that the edges are put in the csv file: an edge is represented by 2 vertices and a weight seperated by commas
Because a vertex is only equal to another when the coordinates are equal
    1. First put the values of the start edge name, x-coordinate, y-coordinate (as ints again)
    2. Second put the value of the end edge name, x-coordinate, y-coorinate (as ints again)
    3. Put the weight (as an integer)
8. Alternatively you can also create a file with
excel. If you save it as a csv it will add the commas automatically
   

