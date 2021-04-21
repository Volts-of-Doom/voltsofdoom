package vision.voltsofdoom.zapbyte.registry2;

import java.util.function.Supplier;
import vision.voltsofdoom.zapbyte.resource.ResourceLocation;

public interface IRegistry2 {

  <E extends IRegistryEntry2<E>> IRegistryMessenger2<E> register(ResourceLocation location, Supplier<E> supplier, Class<E> type);

  <E extends IRegistryEntry2<E>> Supplier<E> getSupplier(ResourceLocation identifier, Class<E> type);
}
