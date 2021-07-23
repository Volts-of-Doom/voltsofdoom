package vision.voltsofdoom.server.astar.maths;

import vision.voltsofdoom.server.astar.world.blocks.Node;

/**
 * Default constants for {@link Node} difficulties and costs. They should all be prime numbers, and
 * abnormal values should be negative.
 * 
 * @author GenElectrovise
 *
 */
public class Difficulties {
  public static final float UNCOMPUTED = -1;
  public static final float ALWAYS_IMPOSSIBLE = -2;
  public static final float NOW_IMPASSABLE = -3;
}
