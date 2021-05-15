package vision.voltsofdoom.zapbyte.registry;

import java.util.function.Supplier;
import vision.voltsofdoom.zapbyte.resource.IID;

public class RegistryMessenger2<T extends IRegistryEntry2<T>> implements IRegistryMessenger2<T> {

  private Class<T> type;
  private IID identifier;
  private IRegistry2 registry;

  public RegistryMessenger2(IID identifier, Class<T> type, IRegistry2 registry) {
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
  public IID getIdentifier() {
    return identifier;
  }

}
