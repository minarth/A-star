package cz.cvut.atg.zui.astar.munchmar;

import eu.superhub.wp5.planner.planningstructure.GraphNode;

/**
 * This class is basic node of our search tree
 *
 * @author munchmar
 */
public class SearchTreeNode {

    private GraphNode graphNode;
    private double heuristics;          
    private double cost;             // What is the cost to get to this node   
    private SearchTreeNode precedor; // Precedor in a map
    private SearchTreeNode previous; // Previous node in linked list
    private SearchTreeNode next;     // Next node in linked list
    private long nodeID;

    public SearchTreeNode(GraphNode graphNode, double pathLength, double heuristics, SearchTreeNode precedor) {
        this.graphNode = graphNode;
        this.heuristics = heuristics;
        this.cost = pathLength;
        this.precedor = precedor;

        this.next = null;
        this.previous = null;
        if (graphNode != null) {
            this.nodeID = graphNode.getId();
        } else {
            this.nodeID = 0;
        }
    }

    public Object getPrecedor() {
        return precedor;
    }

    public void setNodeID(long id) {
        this.nodeID = id;
    }

    public long getNodeID() {
        return this.nodeID;
    }

    // Update values for better
    public void update(double pathLength, SearchTreeNode precedor) {
        this.precedor = precedor;
        this.cost = pathLength;
    }

    public void setPrecedor(SearchTreeNode precedor) {
        this.precedor = precedor;
    }

    public void setPrevious(SearchTreeNode prev) {
        this.previous = prev;
    }

    public void setNext(SearchTreeNode next) {
        this.next = next;
    }

    public SearchTreeNode getPrevious() {
        return previous;
    }

    public SearchTreeNode getNext() {
        return next;
    }

    public GraphNode getGraphNode() {
        return graphNode;
    }

    public double getHeuristics() {
        return heuristics;
    }

    public void setHeuristics(double heuristics) {
        this.heuristics = heuristics;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double pathLength) {
        this.cost = pathLength;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (int) (this.nodeID ^ (this.nodeID >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SearchTreeNode other = (SearchTreeNode) obj;
        if (this.nodeID != other.nodeID) {
            return false;
        }
        return true;
    }
}
