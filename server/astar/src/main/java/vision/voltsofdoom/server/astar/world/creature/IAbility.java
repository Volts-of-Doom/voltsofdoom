package vision.voltsofdoom.server.astar.world.creature;

import vision.voltsofdoom.server.astar.world.terrain.ITerrainType;

/**
 * Gives an {@link IClient} the ability to move in another kind of space, or provides a similar
 * buff.
 * 
 * @author GenElectrovise
 *
 */
public interface IAbility {

  /**
   * @return The name of this {@link IAbility}
   * 
   */
  String name();

  /**
   * Which {@link ITerrainType}s can this {@link IAbility} traverse? For example: does the terrain
   * require flight?
   * 
   * @param terrain
   */
  boolean canTraverse(ITerrainType terrain);
}
