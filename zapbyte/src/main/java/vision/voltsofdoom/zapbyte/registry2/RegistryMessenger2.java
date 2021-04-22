package vision.voltsofdoom.zapbyte.registry2;

import java.util.function.Supplier;
import vision.voltsofdoom.zapbyte.resource.IResourceLocation;

public class RegistryMessenger2<T extends IRegistryEntry2<T>> implements IRegistryMessenger2<T> {

  private Class<T> type;
  private IResourceLocation identifier;
  private IRegistry2 registry;

  public RegistryMessenger2(IResourceLocation identifier, Class<T> type, IRegistry2 registry) {
    this.type = type;
    this.identifier = identifier;
    this.registry = registry;
  }

  @Override
  public T get() {
    return registry.getSupplier(identifier, type).get();
  }

  public Supplier<T> getSupplier() {
    return registry.getSupplier(identifier, type);
  }

  @Override
  public Class<T> getType() {
    return type;
  }

  @Override
  public IResourceLocation getIdentifier() {
    return identifier;
  }

}
