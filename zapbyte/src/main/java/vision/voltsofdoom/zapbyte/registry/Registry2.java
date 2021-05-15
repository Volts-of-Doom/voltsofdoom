package vision.voltsofdoom.zapbyte.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vision.voltsofdoom.zapbyte.bandwagon.Stowaway;
import vision.voltsofdoom.zapbyte.bandwagon.event.RegistryStatusEvent;
import vision.voltsofdoom.zapbyte.resource.IID;
import vision.voltsofdoom.zapbyte.resource.ID;

public class Registry2 implements IRegistry2 {

  private static final Logger LOGGER = LoggerFactory.getLogger(Registry2.class);

  private IRegistryStatus status;
  private Map<Class<IRegistryEntry2<?>>, Map<IID, Supplier<? extends IRegistryEntry2<?>>>> map;

  public Registry2() {
    this.status = IRegistryStatus.MUTABLE;
    this.map = new HashMap<>();
  }

  @Stowaway
  private static void onRegistryStatusEvent(RegistryStatusEvent event) {

    IRegistry2 registry = event.getZapbyte().getRegistry();

    IRegistryStatus oldStatus = registry.getStatus();

    registry.setStatus(event.getStatus());

    LOGGER.warn("Registry state has been updated for main ZapByte registry: " + oldStatus.getName() + " >> " + event.getStatus().getName());
    registry.dump();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <E extends IRegistryEntry2<E>> IRegistryMessenger2<E> register(IID location, Supplier<E> supplier, Class<E> type) {

    if (!status.isMutable()) {
      throw new IllegalStateException("The current registry status " + status.getName() + " is not mutable.");
    }

    RegistryMessenger2<E> messenger = new RegistryMessenger2<E>(location, type, this);
    Map<IID, Supplier<? extends IRegistryEntry2<?>>> registry = map.get(type);

    if (registry == null) {
      registry = new HashMap<>();
      map.put((Class<IRegistryEntry2<?>>) type, registry);
    }

    registry.put(location, supplier);

    return messenger;
  }

  @SuppressWarnings("unchecked")
  public <T extends IRegistryEntry2<T>> Map<IID, T> getRegistry(Class<T> type) {
    return (Map<IID, T>) map.get(type);
  }

  @SuppressWarnings("unchecked")
  public <E extends IRegistryEntry2<E>> Map<IID, E> getEntry(ID location, Class<E> type) {
    return (Map<IID, E>) map.get(type).get(location);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <E extends IRegistryEntry2<E>> Supplier<E> getSupplier(IID identifier, Class<E> type) {
    return (Supplier<E>) map.get(type).get(identifier);
  }

  @Override
  public void setStatus(IRegistryStatus status) {
    this.status = status;
  }

  @Override
  public IRegistryStatus getStatus() {
    return status;
  }

  @Override
  public void dump() {
    LOGGER.debug("Registry dump!");

    map.forEach((clazz, mappings) -> {
      LOGGER.debug(clazz.getName());
      
      LOGGER.debug(mappings.toString());
    });
  }

}
