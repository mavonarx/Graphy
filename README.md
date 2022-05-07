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
3. in the following lines there are either edges or vertices
4. an edge is represented by 2 vertices and a weight seperated by commas
    1. First put the value of the start edge (any string)
    2. Second put the value of the end edge (any string)
    3. Put the weight (as an integer)
5. a vertex is just represented as a single string on the line
6. repeat steps 4,5 as many times as needed
7. If a vertex is already a start or end vertex of an edge 
it will get automatically added to the graph and does not have to be 
provided in the csv file (but still can).
8. Alternatively you can also create a file with
excel. If you save it as a csv it will add the commas automatically
   

