package vision.voltsofdoom.zapbyte.registry2;

import java.util.function.Supplier;
import vision.voltsofdoom.zapbyte.resource.IResourceLocation;

public interface IRegistry2 {

  <E extends IRegistryEntry2<E>> IRegistryMessenger2<E> register(IResourceLocation location, Supplier<E> supplier, Class<E> type);

  <E extends IRegistryEntry2<E>> Supplier<E> getSupplier(IResourceLocation identifier, Class<E> type);
  
  void setStatus(IRegistryStatus status);
  
  IRegistryStatus getStatus();
  
  void dump();
}
