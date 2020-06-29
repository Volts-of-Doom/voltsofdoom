package vision.voltsofdoom.coresystem.loading.registry;

import vision.voltsofdoom.coresystem.loading.resource.ResourceLocation;

/**
 * Denotes a type of registry for identifying {@link TypeRegistry}s.
 * 
 * @author GenElectrovise
 *
 */
public class RegistryType {

	private final ResourceLocation identifier;
	private final Class<? extends IRegistryEntry<?>> clazzType;

	protected RegistryType(ResourceLocation identifier, Class<? extends IRegistryEntry<?>> clazzType) {
		this.identifier = identifier;
		this.clazzType = clazzType;
	}

	public ResourceLocation getIdentifier() {
		return identifier;
	}

	public Class<? extends IRegistryEntry<?>> getClazzType() {
		return clazzType;
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof RegistryType)) {
			return false;
		}

		if (!identifier.equals(((RegistryType) obj).getIdentifier())) {
			return false;
		}

		if (!clazzType.equals(((RegistryType) obj).getClazzType())) {
			return false;
		}

		return true;
	}
}
