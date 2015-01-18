package cz.cvut.atg.zui.astar.munchmar;

import cz.cvut.atg.zui.astar.AbstractOpenList;
import cz.cvut.atg.zui.astar.PlannerInterface;
import cz.cvut.atg.zui.astar.RoadGraph;
import cz.cvut.atg.zui.astar.Utils;
import eu.superhub.wp5.planner.planningstructure.GraphEdge;
import eu.superhub.wp5.planner.planningstructure.GraphNode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author munchmar
 */
public class Planner implements PlannerInterface<Object> {

    private OpenList openList = new OpenList();
    private ArrayList<SearchTreeNode> closedList = new ArrayList<>();

    @Override
    public List<GraphEdge> plan(RoadGraph graph, GraphNode origin, GraphNode destination) {

        // Temporary variables
        SearchTreeNode currentNode, tmp1, tmp2 = new SearchTreeNode(null, 0, 0, null);
        GraphNode tmp;
        double temporaryCost, heuristics;

        ArrayList<GraphEdge> result = new ArrayList<>(); // here will be result path

        // preparing for A-star
        if (destination.equals(origin)) {
            return null;
        } else {
            openList.add(new SearchTreeNode(origin, 0, Utils.distanceInKM(origin, destination) / 120, null));
        }

        // A-star loop
        while (!openList.empty()) {

            // Get BEST node to expand
            currentNode = (SearchTreeNode) openList.get();

            // We reached the destination
            // go back through nodes and read path
            if (destination.equals(currentNode.getGraphNode())) {
                tmp1 = currentNode;
                tmp2 = (SearchTreeNode) currentNode.getPrecedor();
                while (!tmp2.getGraphNode().equals(origin)) {
                    result.add(0, graph.getEdge(tmp2.getGraphNode().getId(), tmp1.getGraphNode().getId()));
                    tmp1 = tmp2;
                    tmp2 = (SearchTreeNode) tmp2.getPrecedor();
                }
                result.add(0, graph.getEdge(tmp2.getGraphNode().getId(), tmp1.getGraphNode().getId()));

                return result;
            }


            closedList.add(currentNode);

            long idOfCurrent = currentNode.getGraphNode().getId();

            //If there is something to expand
            if (graph.getNodeOutcomingEdges(idOfCurrent) != null) {
                // Go through all nodes that are after currentNode
                for (GraphEdge edge : graph.getNodeOutcomingEdges(idOfCurrent)) {

                    //Get node on the end of edge
                    tmp = graph.getNodeByNodeId(edge.getToNodeId());
                    //save nodeID into tmp2 -> for searching in our openList
                    tmp2.setNodeID(tmp.getId());
                    //compute new heuristics and cost
                    temporaryCost = currentNode.getCost() + (edge.getLengthInMetres() / 1000) / edge.getAllowedMaxSpeedInKmph();
                    heuristics = Utils.distanceInKM(tmp, destination) / 120;

                    //If the node wasnt expanded yes
                    if (!closedList.contains(tmp2)) {
                        // if node is not in closed list -> add
                        // otherwise, check, if we found better way and update
                        if (!openList.contains(tmp2)) {
                            openList.add(new SearchTreeNode(tmp,
                                    temporaryCost, heuristics, currentNode));
                        } else {

                            tmp1 = openList.getNode(tmp2);
                            if (tmp1.getCost() > temporaryCost) {
                                tmp1.update(temporaryCost, currentNode);
                            }

                            openList.insert(tmp1);
                        }
                    }
                }
            }

        }

        return null;
    }

    @Override
    public AbstractOpenList<Object> getOpenList() {
        return openList;
    }
}