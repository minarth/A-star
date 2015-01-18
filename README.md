# Implementation of A-star algorithm
This was an assignment for courses of Artificial inteligence. Task was to implement A-star algorithm for searching quickest path between two points in graph. We got graph of Great Britain, which is in file data/ukhigh_filtered.dat. 

## My implementation
My part is done in src/.../munchmar

SearchTreeNode.java is class for node in an OpenList
OpenList.java is class for openList in A-star, it is double linked list of SearchTreeNode
Planner.java is implementation of A-star, call Planner.plan(graph, from, to).