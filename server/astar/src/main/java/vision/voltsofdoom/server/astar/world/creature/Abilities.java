package vision.voltsofdoom.server.astar.world.creature;

import java.util.function.Supplier;
import vision.voltsofdoom.server.astar.world.terrain.ITerrainType;

/**
 * Default {@link IAbility}s.
 * 
 * @author GenElectrovise
 *
 */
public class Abilities {

  public static final Supplier<IAbility> WALKING = () -> new IAbility() {
    @Override
    public String name() {
      return "walking";
    }

    @Override
    public boolean canTraverse(ITerrainType terrain) {
      return true;
    }
  };

  public static final Supplier<IAbility> FLYING = () -> new IAbility() {
    @Override
    public String name() {
      return "flying";
    }

    @Override
    public boolean canTraverse(ITerrainType terrain) {
      return true;
    }
  };

  public static final Supplier<IAbility> SWIMMING = () -> new IAbility() {
    @Override
    public String name() {
      return "swimming";
    }

    @Override
    public boolean canTraverse(ITerrainType terrain) {
      return true;
    }
  };
}
