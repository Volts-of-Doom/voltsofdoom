package vision.voltsofdoom.coresystem.loading.registry;

import java.util.LinkedHashMap;

import vision.voltsofdoom.coresystem.loading.resource.ResourceLocation;

public class Registry {

	// Management

	public static final LinkedHashMap<ResourceLocation, IRegistry<? extends IRegistryEntry<?>>> registry = new LinkedHashMap<ResourceLocation, IRegistry<? extends IRegistryEntry<?>>>();

	@SuppressWarnings("unchecked")
	public static <T extends IRegistryEntry<T>> IRegistry<T> getTyped(RegistryType registryType) {
		IRegistry<T> out = null;

		try {
			for (IRegistry<?> registry : registry.values()) {
				if (registry.getType() == registryType) {

					// Unchecked cast
					out = (IRegistry<T>) registry.getType();
					break;
				}
			}
		} catch (NullPointerException n) {
			n.printStackTrace();
		} catch (ClassCastException c) {
			c.printStackTrace();
		}

		return out;
	}

}
