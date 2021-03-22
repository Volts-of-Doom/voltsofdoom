package vision.voltsofdoom.astar.world.dimensions;

/**
 * Reflects a new dimension. For example: 2D, 3D or even 4D.
 * 
 * @author GenElectrovise
 *
 */
public interface IDimension {

  /**
   * @return The number of aspects to this dimension. For example: 2 for 2D; 3 for 3D.
   */
  int getAspects();
}
