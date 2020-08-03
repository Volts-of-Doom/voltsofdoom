package vision.voltsofdoom.coresystem.loading.registry;

import vision.voltsofdoom.coresystem.universal.log.VODLog4J;
import vision.voltsofdoom.coresystem.universal.resource.ResourceLocation;

public abstract class RegistryEntry<T extends IRegistryEntry<T>> implements IRegistryEntry<T> {

	public ResourceLocation identifier;

	@Override
	public ResourceLocation getIdentifier() {

		if (identifier == null) {
			VODLog4J.LOGGER.warn(
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
