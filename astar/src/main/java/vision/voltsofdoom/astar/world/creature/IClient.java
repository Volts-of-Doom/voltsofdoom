package vision.voltsofdoom.astar.world.creature;

import java.util.Set;
import vision.voltsofdoom.astar.world.terrain.ITerrainType;

/**
 * A thing for which a path can be found.
 * 
 * @author GenElectrovise
 *
 */
public interface IClient {

  /**
   * @return A {@link Set} of {@link IAbility}s which this {@link IClient} has.
   */
  Set<IAbility> getAbilities();

  /**
   * 
   * @param type
   * @return A modifier to tell how difficult is it for this {@link IClient} to traverse a tile of the
   *         given {@link ITerrainType}.
   */
  float getModifierForTerrain(ITerrainType type);

  /**
   * Can this {@link IClient} traverse a tile of the given terrain? This should primarily be derived
   * from the creature's {@link IAbility}s.
   * 
   * @param terrain
   * @return
   */
  boolean canTraverse(ITerrainType terrain);
}
