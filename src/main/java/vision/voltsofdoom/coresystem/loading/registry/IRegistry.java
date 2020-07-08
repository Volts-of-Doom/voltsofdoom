package vision.voltsofdoom.coresystem.loading.registry;

import java.util.function.Supplier;

import vision.voltsofdoom.coresystem.loading.resource.ResourceLocation;

/**
 * A mutable (non finalised) registry.
 * 
 * @author GenElectrovise
 *
 * @param <T> The type of {@link IRegistryEntry} being registered.
 */
public interface IRegistry<T extends IRegistryEntry<T>> {

	/**
	 * @return The {@link ResourceLocation} identifier of this {@link IRegistry}.
	 */
	public ResourceLocation getRegistryIdentifier();

	/**
	 * @return The {@link RegistryType} of this {@link IRegistry}
	 */
	public RegistryType getType();

	/**
	 * @return the supplier of the {@link IRegistryEntry} identified by the given
	 *         {@link ResourceLocation}.
	 */
	public Supplier<T> retrieveSupplier(ResourceLocation identifier);

	/**
	 * @return The current {@link IRegistryState} of this {@link IRegistry}
	 */
	public IRegistryState getState();

	/**
	 * Sets the {@link IRegistryState} of this {@link IRegistry}
	 * 
	 * @param state
	 */
	public void setState(IRegistryState state);

	/**
	 * Registers the {@link Supplier} to this {@link IRegistry}, binding it to the
	 * given {@link ResourceLocation}
	 * 
	 * @return
	 */
	public RegistryObjectRetriever<T> register(ResourceLocation identifier, Supplier<T> item);
}
