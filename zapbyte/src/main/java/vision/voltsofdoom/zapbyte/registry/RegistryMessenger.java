package vision.voltsofdoom.zapbyte.registry;

import java.util.function.Supplier;
import vision.voltsofdoom.zapbyte.resource.IID;

public class RegistryMessenger<T extends IRegistryEntry<T>> implements IRegistryMessenger<T> {

  private Class<T> type;
  private IID identifier;
  private IRegistry registry;

  public RegistryMessenger(IID identifier, Class<T> type, IRegistry registry) {
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
