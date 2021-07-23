package vision.voltsofdoom.server.astar.world;

import java.util.Set;
import vision.voltsofdoom.server.astar.maths.FloatArrayVector;
import vision.voltsofdoom.server.astar.world.blocks.INode;
import vision.voltsofdoom.server.astar.world.creature.IClient;
import vision.voltsofdoom.server.astar.world.dimensions.IDimension;

/**
 * A world which the algorithm can pathfind in.
 * 
 * @author GenElectrovise
 *
 * @param <T> The dimension type of the world, for example, 2D, 3D or even 4D.
 */
public interface IPathingWorld<T extends IDimension> {

  /**
   * @return The current {@link IClient} for which a path is being found.
   */
  IClient getClient();

  /**
   * @param coordinates
   * @return The node at the
   */
  INode getNodeAt(int[] coordinates);

  /**
   * 
   * @param coordinates
   * @return A {@link Set} of the {@link INode}s surrounding the {@link INode} at the given
   *         coordinates.
   */
  Set<INode> getSurroundingNodes(FloatArrayVector coordinates);

  /**
   * 
   * @return The {@link INode} to path from.
   */
  INode getStartNode();

  /**
   * 
   * @return The {@link INode} to path to.
   */
  INode getEndNode();
}
