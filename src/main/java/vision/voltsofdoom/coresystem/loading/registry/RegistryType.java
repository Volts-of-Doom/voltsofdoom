package vision.voltsofdoom.coresystem.loading.registry;

import vision.voltsofdoom.coresystem.universal.resource.ResourceLocation;

/**
 * Denotes a type of registry for identifying {@link TypeRegistry}s.
 * 
 * @author GenElectrovise
 *
 */
public class RegistryType<T extends IRegistryEntry<T>> {

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

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof RegistryType)) {
			return false;
		}
		
		if(!((RegistryType<?>) obj).clazzType.equals(getClazzType())) {
			return false;
		}

		if (!identifier.equals(((RegistryType<T>) obj).getIdentifier())) {
			return false;
		}

		if (!clazzType.equals(((RegistryType<T>) obj).getClazzType())) {
			return false;
		}

		return true;
	}
	
	
}
