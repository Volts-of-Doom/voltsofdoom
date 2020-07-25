package vision.voltsofdoom.coresystem.loading.registry;

import java.util.Objects;
import java.util.function.Supplier;

import vision.voltsofdoom.coresystem.universal.resource.ResourceLocation;

/**
 * THIS OBJECT DOES NOT GO INTO THE REGISTRY!! <br>
 * <br>
 * This object acts as a retrieval mechanism for accessing a registered object!
 * 
 * @author GenElectrovise
 *
 * @param <T> The type of the {@link IRegistryEntry} of this object, i.e. the
 *        type of object that the {@link RegistryMessenger#get()} method will
 *        return.
 */
public class RegistryMessenger<T extends IRegistryEntry<T>> implements Supplier<T> {

	private final ResourceLocation identifier;
	private Supplier<T> instanceSupplier;
	private final IRegistry<T> parentRegistry;

	public RegistryMessenger(ResourceLocation identifier, Supplier<T> instanceSupplier, IRegistry<T> parentRegistry) {
		Objects.requireNonNull(identifier, () -> "Identifier cannot be null!");
		Objects.requireNonNull(instanceSupplier, () -> "Supplier cannot be null!");
		Objects.requireNonNull(parentRegistry, () -> "Parent IRegistry cannot be null!");

		this.identifier = identifier;
		this.instanceSupplier = instanceSupplier;
		this.parentRegistry = parentRegistry;
	}

	public ResourceLocation getIdentifier() {
		return identifier;
	}

	public Supplier<T> getInstanceSupplier() {
		return instanceSupplier;
	}

	@SuppressWarnings("unchecked")
	private void updateReference() {
		IFinalisedRegistry<?> fReg = Registry.getTyped(parentRegistry.getType());
		instanceSupplier = (Supplier<T>) fReg.retrieveSupplier(identifier);
	}

	@Override
	public T get() {
		updateReference();
		return instanceSupplier.get();
	}

}
