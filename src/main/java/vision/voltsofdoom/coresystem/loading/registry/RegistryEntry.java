package vision.voltsofdoom.coresystem.loading.registry;

import vision.voltsofdoom.coresystem.universal.log.VoltLog;
import vision.voltsofdoom.coresystem.universal.resource.ResourceLocation;

/**
 * An abstract {@link RegistryEntry}. Contains concrete implementations of the
 * methods in {@link IRegistryEntry}. Unless you think that my methods are
 * deeply flawed in some way, best practise is to extend this class if you want
 * to create a new registerable type.
 * 
 * @author GenElectrovise
 *
 * @param <T>
 */
public abstract class RegistryEntry<T extends IRegistryEntry<T>> implements IRegistryEntry<T> {
	private static final VoltLog LOGGER = new VoltLog(RegistryEntry.class);

	public ResourceLocation identifier;

	@Override
	public ResourceLocation getIdentifier() {

		if (identifier == null) {
			LOGGER.warn(
					"Registry entry returning a null identifier! Going on with it, but this may cause issues later!");
		}

		return identifier;
	}

	@Override
	public void setIdentifier(ResourceLocation identifier) {
		if (getIdentifier() != null) {
			throw new IllegalStateException("Cannot assign new identifier ('" + identifier
					+ "') to a RegistryEntry which already has an identifier ('" + getIdentifier() + "')!");
		}

		this.identifier = identifier;
	}

}
