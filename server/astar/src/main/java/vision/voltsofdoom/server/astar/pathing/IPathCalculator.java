package vision.voltsofdoom.server.astar.pathing;

import java.util.Set;
import vision.voltsofdoom.server.astar.maths.FloatArrayVector;
import vision.voltsofdoom.server.astar.world.IPathingWorld;
import vision.voltsofdoom.server.astar.world.blocks.INode;
import vision.voltsofdoom.server.astar.world.dimensions.IDimension;

/**
 * Denotes a class which can be used to calculate paths.
 * 
 * @author GenElectrovise
 * @see PathCalculator
 *
 * @param <T>
 */
public interface IPathCalculator<T extends IDimension> {

  /**
   * Finds a path from start to end within the given {@link IPathingWorld}
   * 
   * @param world
   * @return A {@link Set} of {@link FloatArrayVector}s along the path.
   */
  Set<FloatArrayVector> findPath(IPathingWorld<T> world);

  /**
   * Finds {@link INode#get_f_cost()}
   * 
   * @param node
   */
  float calculateFCost(IPathingWorld<T> world, INode currentNode, INode neighbourNode);

  /**
   * Finds {@link INode#get_g_cost()}
   * 
   * @param node
   */
  float calculateGCost(IPathingWorld<T> world, INode currentNode, INode neighbourNode);

  /**
   * Finds {@link INode#get_h_cost()}
   * 
   * @param node
   */
  float calculateHCost(IPathingWorld<T> world, INode node);
}
