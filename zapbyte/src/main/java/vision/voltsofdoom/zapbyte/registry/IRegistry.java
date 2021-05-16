package vision.voltsofdoom.zapbyte.registry;

import java.util.function.Supplier;
import vision.voltsofdoom.zapbyte.resource.IID;

public interface IRegistry {

  <E extends IRegistryEntry<E>> IRegistryMessenger<E> register(IID location, Supplier<E> supplier, Class<E> type);

  <E extends IRegistryEntry<E>> Supplier<E> getSupplier(IID identifier, Class<E> type);
  
  void setStatus(IRegistryStatus status);
  
  IRegistryStatus getStatus();
  
  void dump();
}
