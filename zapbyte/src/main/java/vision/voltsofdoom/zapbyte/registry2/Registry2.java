package vision.voltsofdoom.zapbyte.registry2;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import vision.voltsofdoom.zapbyte.resource.ResourceLocation;

public class Registry2 implements IRegistry2 {

  private Map<Class<IRegistryEntry2<?>>, Map<ResourceLocation, Supplier<? extends IRegistryEntry2<?>>>> map;

  public Registry2() {
    map = new HashMap<>();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <E extends IRegistryEntry2<E>> IRegistryMessenger2<E> register(ResourceLocation location, Supplier<E> supplier, Class<E> type) {
    RegistryMessenger2<E> messenger = new RegistryMessenger2<E>(location, type, this);
    Map<ResourceLocation, Supplier<? extends IRegistryEntry2<?>>> registry = map.get(type);

    if (registry == null) {
      registry = new HashMap<>();
      map.put((Class<IRegistryEntry2<?>>) type, registry);
    }

    registry.put(location, supplier);

    return messenger;
  }

  @SuppressWarnings("unchecked")
  public <T extends IRegistryEntry2<T>> Map<ResourceLocation, T> getRegistry(Class<T> type) {
    return (Map<ResourceLocation, T>) map.get(type);
  }

  @SuppressWarnings("unchecked")
  public <E extends IRegistryEntry2<E>> Map<ResourceLocation, E> getEntry(ResourceLocation location, Class<E> type) {
    return (Map<ResourceLocation, E>) map.get(type).get(location);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <E extends IRegistryEntry2<E>> Supplier<E> getSupplier(ResourceLocation identifier, Class<E> type) {
    return (Supplier<E>) map.get(type).get(identifier);
  }

}
