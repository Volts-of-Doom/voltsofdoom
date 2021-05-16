package vision.voltsofdoom.voltsofdoom.property;

import java.util.function.Consumer;
import vision.voltsofdoom.voltsofdoom.tile.Tile;

/**
 * Defines a property of something. This could be the hardness of a {@link Tile}, or something else
 * entirely.
 * 
 * @author GenElectrovise
 *
 * @param <T>
 */
public interface IProperty<T> {

  /**
   * @return The name of this {@link IProperty}.
   */
  public String getName();

  /**
   * @return The current value of this {@link IProperty}.
   */
  public T getValue();

  /**
   * Apply this {@link IProperty} to the propertyApplyee
   * 
   * @param propertyApplyee The object to apply this {@link IProperty} to.
   */
  public void apply(Consumer<T> propertyApplyee);
}
