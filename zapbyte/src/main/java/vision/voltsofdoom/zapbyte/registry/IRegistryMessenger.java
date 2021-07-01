package vision.voltsofdoom.zapbyte.registry;

import java.util.function.Supplier;
import vision.voltsofdoom.zapbyte.resource.IID;

public interface IRegistryMessenger<T extends IRegistryEntry<T>> extends Supplier<T> {

  Class<T> getType();

  IID getIdentifier();
}
