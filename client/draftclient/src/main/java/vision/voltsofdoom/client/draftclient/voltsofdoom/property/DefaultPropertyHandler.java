package vision.voltsofdoom.client.draftclient.voltsofdoom.property;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple, default implementation of {@link IPropertyHandler}.
 * 
 * @author GenElectrovise
 *
 * @param <T>
 */
public class DefaultPropertyHandler<T> implements IPropertyHandler<T> {

  private Map<String, IProperty<T>> properties = new HashMap<String, IProperty<T>>();

  @Override
  public IProperty<T> get(String name) {
    return properties.get(name);
  }

  @Override
  public void add(IProperty<T> property) {
    properties.put(property.getName(), property);
  }

  @Override
  public Collection<IProperty<T>> list() {
    return properties.values();
  }

}
