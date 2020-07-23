package vision.voltsofdoom.coresystem.loading.registry;

import java.util.Map;
import java.util.function.Supplier;

import vision.voltsofdoom.coresystem.loading.resource.ResourceLocation;

/**
 * An {@link IRegistry} which is immutable. This is a required implemented
 * interface for items in the {@link Registry}.
 * 
 * @author GenElectrovise
 *
 * @param <T> The type of {@link IRegistryEntry} being registered.
 */
public interface IFinalisedRegistry<T extends IRegistryEntry<T>> {

	/**
	 * @return The contents of this {@link IFinalisedRegistry}.
	 */
	public Map<ResourceLocation, Supplier<T>> getEntries();

	/**
	 * @return The {@link ResourceLocation} identifier of this {@link IRegistry}.
	 */
	public ResourceLocation getRegistryIdentifier();

	/**
	 * @return The {@link RegistryType} of this {@link IRegistry}
	 */
	public RegistryType<?> getType();

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
	 * Called by the {@link Registry} to inject the contents of another
	 * {@link IFinalisedRegistry} into this one, combining the two.
	 * 
	 * @param finalisedRegistry
	 */
	void inject(IFinalisedRegistry<?> finalisedRegistry);
	
	@Override
	String toString();

	/**
	 * Is called by the {@link Registry} to close off this
	 * {@link IFinalisedRegistry}. This should ensure that no edits can be made to
	 * the contents of this {@link IFinalisedRegistry} after this method has been
	 * called. No need to (in fact, don't) call this method yourself, as this is
	 * dealt with by the game registry.
	 */
	public void lock();
}
