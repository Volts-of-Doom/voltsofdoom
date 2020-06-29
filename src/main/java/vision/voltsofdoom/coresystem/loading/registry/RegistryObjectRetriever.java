package vision.voltsofdoom.coresystem.loading.registry;

import java.util.Objects;
import java.util.function.Supplier;

import vision.voltsofdoom.coresystem.loading.resource.ResourceLocation;

/**
 * THIS OBJECT DOES NOT GO INTO THE REGISTRY!! <br>
 * <br>
 * This object acts as a retrieval mechanism for accessing a registered object!
 * 
 * @author GenElectrovise
 *
 * @param <T> The type of the {@link IRegistryEntry} of this object, i.e. the type
 *        of object that the {@link RegistryObjectRetriever#get()} method will
 *        return.
 */
public class RegistryObjectRetriever<T extends IRegistryEntry<T>> implements Supplier<T> {

	private final ResourceLocation identifier;
	private final Supplier<T> instanceSupplier;
	private final IRegistry<T> parentRegistry;

	public RegistryObjectRetriever(ResourceLocation identifier, Supplier<T> instanceSupplier,
			IRegistry<T> parentRegistry) {
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

	private void updateReference() {
		Registry.getTyped(parentRegistry.getType()).retrieveSupplier(identifier);
	}

	@Override
	public T get() {
		updateReference();
		return instanceSupplier.get();
	}

}
