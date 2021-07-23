package vision.voltsofdoom.server.astar.world.blocks;

import vision.voltsofdoom.server.astar.maths.FloatArrayVector;
import vision.voltsofdoom.server.astar.pathing.IPathCalculator;
import vision.voltsofdoom.server.astar.world.IPathingWorld;
import vision.voltsofdoom.server.astar.world.terrain.ITerrainType;

/**
 * Something that the {@link IPathCalculator} should be aware of in the world. The
 * {@link IPathingWorld} is made out of {@link INode}s.
 * 
 * @author GenElectrovise
 * @see Node
 */
public interface INode {

  /**
   * @return The coordinates of this {@link INode}.
   */
  FloatArrayVector getCoordinates();

  /**
   * @return The base difficulty for this node.
   */
  float getBaseDifficulty();

  /**
   * @return The {@link ITerrainType} for this node.
   */
  ITerrainType getTerrainType();

  /**
   * The sum of the {@link #get_g_cost()} and {@link #get_h_cost()}, representing the goodness of this
   * {@link INode}.
   */
  float get_f_cost();

  void set_f_cost(float cost);

  /**
   * The difficulty of the path from the start node.
   */
  float get_g_cost();

  void set_g_cost(float cost);

  /**
   * The distance remaining to the end node.
   */
  float get_h_cost();

  void set_h_cost(float cost);

  /**
   * @param comparison
   * @return Whether this {@link INode} is as good as (typically meaning "has the same f_cost as") the
   *         comparison {@link INode}.
   */
  boolean isAsGoodAs(INode comparison);

  /**
   * Sets this {@link INode}'s parent to the given {@link INode}. Used for tracking the path back when
   * done.
   * 
   * @param parent
   */
  void setParent(INode parent);

  /**
   * @return The parent {@link INode} of this {@link INode}. Used for tracking the path back when
   *         done.
   */
  INode getParent();

}
