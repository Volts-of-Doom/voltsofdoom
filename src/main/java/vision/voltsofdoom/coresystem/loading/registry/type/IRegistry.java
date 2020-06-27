package vision.voltsofdoom.coresystem.loading.registry.type;

import java.util.function.Supplier;

import vision.voltsofdoom.coresystem.loading.resource.ResourceLocation;

public interface IRegistry<T extends IRegistryEntry<T>> {

	/**
	 * @return The {@link ResourceLocation} identifier of this {@link IRegistry}.
	 */
	public ResourceLocation getRegistryIdentifier();

	/**
	 * Registers the {@link Supplier} to this {@link IRegistry}, binding it to the
	 * given {@link ResourceLocation}
	 * 
	 * @return
	 */
	public RegistryObjectRetriever<T> register(ResourceLocation identifier, Supplier<T> item);

	/**
	 * @return The {@link RegistryType} of this {@link IRegistry}
	 */
	public RegistryType getType();

	/**
	 * @return the supplier of the {@link IRegistryEntry} identified by the given
	 *         {@link ResourceLocation}.
	 */
	public Supplier<T> retrieveSupplier(ResourceLocation identifier);
}
