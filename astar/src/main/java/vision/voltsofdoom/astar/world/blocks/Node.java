package vision.voltsofdoom.astar.world.blocks;

import vision.voltsofdoom.astar.maths.Difficulties;
import vision.voltsofdoom.astar.maths.FloatArrayVector;
import vision.voltsofdoom.astar.world.terrain.ITerrainType;
import vision.voltsofdoom.astar.world.terrain.TerrainTypes;

/**
 * An implementation of {@link INode}.
 * 
 * @author GenElectrovise
 *
 */
public class Node implements INode {

  private float f_cost, g_cost, h_cost;

  /**
   * Construct a new {@link Node}. Normally an end user should use
   * {@link #Node(FloatArrayVector, ITerrainType, float)} because they won't know the parent or costs.
   * 
   * @param f_cost The starting F-Cost of the {@link Node}.
   * @param g_cost The starting G-Cost of the {@link Node}.
   * @param h_cost The starting H-Cost of the {@link Node}.
   * @param coordinates The {@link FloatArrayVector} coordinates of this {@link Node}.
   * @param terrainType The {@link ITerrainType} of this {@link Node}.
   * @param baseDifficulty The base difficulty of this {@link Node}. See {@link TerrainTypes} for
   *        examples.
   * @param parent The parent {@link Node} of this {@link Node}.
   */
  public Node(float f_cost, float g_cost, float h_cost, FloatArrayVector coordinates, ITerrainType terrainType, float baseDifficulty, INode parent) {
    this.f_cost = f_cost;
    this.g_cost = g_cost;
    this.h_cost = h_cost;
    this.coordinates = coordinates;
    this.terrainType = terrainType;
    this.baseDifficulty = baseDifficulty;
    this.parent = parent;
  }

  /**
   * See {@link Node#Node(float, float, float, FloatArrayVector, ITerrainType, float, INode)}
   */
  public Node(FloatArrayVector coordinates, ITerrainType terrainType, float baseDifficulty) {
    this(Difficulties.UNCOMPUTED, Difficulties.UNCOMPUTED, Difficulties.UNCOMPUTED, coordinates, terrainType, baseDifficulty, null);
  }

  private FloatArrayVector coordinates;
  private ITerrainType terrainType;
  private float baseDifficulty;
  private INode parent;

  @Override
  public float getBaseDifficulty() {
    return baseDifficulty;
  }

  @Override
  public ITerrainType getTerrainType() {
    return terrainType;
  }

  @Override
  public FloatArrayVector getCoordinates() {
    return coordinates;
  }

  @Override
  public float get_f_cost() {
    return f_cost;
  }

  @Override
  public void set_f_cost(float cost) {
    this.f_cost = cost;
  }

  @Override
  public float get_g_cost() {
    return g_cost;
  }

  @Override
  public void set_g_cost(float cost) {
    this.g_cost = cost;
  }

  @Override
  public float get_h_cost() {
    return h_cost;
  }

  @Override
  public void set_h_cost(float cost) {
    this.h_cost = cost;
  }

  @Override
  public boolean isAsGoodAs(INode comparison) {
    return this.f_cost == comparison.get_f_cost();
  }

  @Override
  public void setParent(INode parent) {
    this.parent = parent;
  }

  @Override
  public INode getParent() {
    return parent;
  }
}
