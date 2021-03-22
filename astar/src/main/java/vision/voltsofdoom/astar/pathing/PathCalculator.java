package vision.voltsofdoom.astar.pathing;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;
import vision.voltsofdoom.astar.maths.FloatArrayVector;
import vision.voltsofdoom.astar.world.IPathingWorld;
import vision.voltsofdoom.astar.world.blocks.INode;
import vision.voltsofdoom.astar.world.dimensions.IDimension;

/**
 * An implementation of {@link IPathCalculator}. Finds a path through the given world, from the
 * worlds start node, to its end node.
 * 
 * @author GenElectrovise
 *
 * @param <T>
 */
public class PathCalculator<T extends IDimension> implements IPathCalculator<T> {

  @Override
  public Set<FloatArrayVector> findPath(IPathingWorld<T> world) {
    HashSet<FloatArrayVector> finalPathVectors = new HashSet<FloatArrayVector>();

    Set<INode> open = new HashSet<INode>();
    Set<INode> closed = new HashSet<INode>();
    open.add(world.getStartNode());

    List<INode> currents;
    boolean foundEndNode = false;

    // While we have not found the end node.
    while (!foundEndNode) {

      // Get the nodes with the lowest F-Cost
      currents = getLowestFCost(makeSortedSet(open));

      // For each node with an equally low F-Cost
      for (INode cNode : currents) {
        closeNode(open, closed, cNode);

        // If the node is the end node, we have found a path.
        if (cNode.equals(world.getEndNode())) {
          foundEndNode = true;
          break;
        }

        // For each neighbouring node
        for (INode nNode : world.getSurroundingNodes(cNode.getCoordinates())) {

          // If the client cannot traverse the node, skip this node
          if (!world.getClient().canTraverse(nNode.getTerrainType())) {
            continue;
          }

          float nNodeNewFCost = calculateFCost(world, cNode, nNode);

          // If a path through the current node (to the neighbour) is shorter than the previously calculated
          // path to the neighbour, update the neighbours values.
          // If the neighbour is closed, skip it.
          if (nNode.get_f_cost() > nNodeNewFCost || !open.contains(nNode)) {
            convertNeighbour(open, cNode, nNode, nNodeNewFCost);
          }
        }
      }
    }

    return finalPathVectors;
  }

  /**
   * Used when a node has already been checked once (and closed), but a more favourable path is found.
   * Reopens the node and changes its F-Cost and parent.
   * 
   * @param open
   * @param cNode
   * @param nNode
   * @param nNodeNewFCost
   */
  public void convertNeighbour(Set<INode> open, INode cNode, INode nNode, float nNodeNewFCost) {
    nNode.set_f_cost(nNodeNewFCost);
    nNode.setParent(cNode);
    if (!open.contains(nNode)) {
      open.add(nNode);
    }
  }

  /**
   * Updates the given open and closed lists in order to remove the given {@link INode}.
   * 
   * @param open
   * @param closed
   * @param node
   */
  public void closeNode(Set<INode> open, Set<INode> closed, INode node) {
    open.remove(node);
    closed.add(node);
  }

  /**
   * @param sortedNodes
   * @return The {@link INode}s with the lowest F-Cost from the input {@link SortedSet}.
   */
  public List<INode> getLowestFCost(SortedSet<INode> sortedNodes) {
    float lowestFCost = sortedNodes.first().get_f_cost();

    List<INode> lowestFCostNodes = sortedNodes.stream() //
        .filter((node) -> node.get_f_cost() == lowestFCost) //
        .collect(Collectors.toList()); //
    return lowestFCostNodes;
  }

  /**
   * Turns a {@link Set} into a {@link SortedSet}.
   * 
   * @param nodes
   */
  public SortedSet<INode> makeSortedSet(Set<INode> nodes) {
    SortedSet<INode> sortedNodes = Collections.emptySortedSet();
    sortedNodes.addAll(nodes);
    return sortedNodes;
  }

  @Override
  public float calculateFCost(IPathingWorld<T> world, INode currentNode, INode neighbourNode) {
    return calculateGCost(world, currentNode, neighbourNode) + calculateHCost(world, neighbourNode);
  }

  @Override
  public float calculateGCost(IPathingWorld<T> world, INode currentNode, INode neighbourNode) {
    return currentNode.get_g_cost() + neighbourNode.getBaseDifficulty();
  }

  @Override
  public float calculateHCost(IPathingWorld<T> world, INode node) {

    FloatArrayVector endNodeVec = world.getEndNode().getCoordinates();
    FloatArrayVector nowNodeVec = node.getCoordinates();

    endNodeVec.subtract(nowNodeVec);

    double sum = 0;
    for (float f : endNodeVec.getValues()) {
      sum += f;
    }

    return (float) Math.sqrt(sum);
  }

}
