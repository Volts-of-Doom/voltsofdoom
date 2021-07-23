package vision.voltsofdoom.server.astar.world.dimensions;

/**
 * An implementation of {@link IDimension} representing a flat (2-dimensional) space.
 * 
 * @author GenElectrovise
 *
 */
public class TwoDimensional implements IDimension {

  @Override
  public int getAspects() {
    return 2;
  }

}
