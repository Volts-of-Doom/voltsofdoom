package vision.voltsofdoom.server.astar;

import java.util.Objects;
import java.util.Set;
import vision.voltsofdoom.server.astar.maths.FloatArrayVector;
import vision.voltsofdoom.server.astar.pathing.IPathCalculator;
import vision.voltsofdoom.server.astar.world.IPathingWorld;
import vision.voltsofdoom.server.astar.world.dimensions.IDimension;

/**
 * The top level / entry point of this A* implementation.
 * 
 * @author GenElectrovise
 *
 */
public class AStar {

  public static void main(String[] args) {
    AStar.findPath(null, null);
  }

  /**
   * 
   * @param pather
   * @param world
   * @return A path from start to end, using the given {@link IPathCalculator}, within the given
   *         {@link IPathingWorld}.
   */
  public static <T extends IDimension> Set<FloatArrayVector> findPath(IPathCalculator<T> pather, IPathingWorld<T> world) {
    Objects.requireNonNull(pather, "The pather for finding an A* path with cannot be null.");
    Objects.requireNonNull(world, "The world for finding an A* path through cannot be null.");
    
    return pather.findPath(world);
  }

}
