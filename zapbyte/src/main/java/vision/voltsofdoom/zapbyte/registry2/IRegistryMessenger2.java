package vision.voltsofdoom.zapbyte.registry2;

import java.util.function.Supplier;
import vision.voltsofdoom.zapbyte.resource.ResourceLocation;

public interface IRegistryMessenger2<T extends IRegistryEntry2<T>> extends Supplier<T> {

  Class<T> getType();

  ResourceLocation getIdentifier();
}
