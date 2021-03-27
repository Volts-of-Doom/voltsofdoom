package vision.voltsofdoom.voltsofdoom.play.data;

import java.util.Collection;

/**
 * Manages {@link IProperty}s, providing a way to get them, for example.
 * 
 * @author GenElectrovise
 *
 * @param <T>
 */
public interface IPropertyHandler<T> {
  
  /**
   * @return A {@link Collection} of all of the {@link IProperty}s currently held.
   */
  public Collection<IProperty<T>> list();

  /**
   * @param name Name of the {@link IProperty} to get
   * @return The {@link IProperty} denoted by the name.
   */
  public IProperty<T> get(String name);

  /**
   * @param property The {@link IProperty} to add.
   */
  public void add(IProperty<T> property);
}
