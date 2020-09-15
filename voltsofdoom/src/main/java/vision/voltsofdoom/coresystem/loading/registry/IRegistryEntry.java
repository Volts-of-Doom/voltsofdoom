package vision.voltsofdoom.coresystem.loading.registry;

import vision.voltsofdoom.coresystem.universal.main.VoltsOfDoomCoreSystem;
import vision.voltsofdoom.coresystem.universal.resource.ResourceLocation;

public interface IRegistryEntry<T> {

	/**
	 * @return The {@link ResourceLocation} identifier of this
	 *         {@link IRegistryEntry}.
	 */
	public ResourceLocation getIdentifier();

	/**
	 * Sets the identifier of this {@link IRegistryEntry}. If called more than once,
	 * should throw an {@link Exception}.<br>
	 * <br>
	 * Should only be called once during initialisation. Modders should
	 * <b><i>never</i></b> need to call this, as this will be handled during
	 * registration by the {@link VoltsOfDoomCoreSystem}. The exception to this is
	 * if you are writing your own registry system <i>completely disregarding the
	 * built in system</i>.
	 */
	public void setIdentifier(ResourceLocation identifier);
}
