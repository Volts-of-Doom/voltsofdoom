package vision.voltsofdoom.astar.world.terrain;

import vision.voltsofdoom.astar.world.blocks.INode;
import vision.voltsofdoom.astar.world.creature.IAbility;
import vision.voltsofdoom.astar.world.creature.IClient;

/**
 * The terrain (and the difficulties which that brings) of a given {@link INode}. Used to determine
 * how easy / possible it is for an {@link IClient} to traverse an {@link INode}.
 * 
 * @author GenElectrovise
 *
 */
public interface ITerrainType {

  /**
   * @param ability
   * @return The difficulty score for traversing this terrain with the given ability.
   */
  float getDifficultyForAbility(IAbility ability);
}
