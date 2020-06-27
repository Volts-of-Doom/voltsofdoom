package vision.voltsofdoom.coresystem.loading.registry;

import java.util.LinkedHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import vision.voltsofdoom.coresystem.loading.registry.type.IRegistry;
import vision.voltsofdoom.coresystem.loading.registry.type.IRegistryEntry;
import vision.voltsofdoom.coresystem.loading.registry.type.RegistryType;
import vision.voltsofdoom.coresystem.loading.resource.ResourceLocation;

public class Registry {

	// Management

	public static final LinkedHashMap<ResourceLocation, IRegistry<? extends IRegistryEntry<?>>> registry = new LinkedHashMap<ResourceLocation, IRegistry<? extends IRegistryEntry<?>>>();

	// Creation

	private static boolean created = false;

	public static Registry createInThreadedFashion() {

		if (Registry.isCreated()) {
			throw new IllegalStateException("Cannot create Registry twice!");
		}

		try {

			Future<Registry> future = Executors.newSingleThreadExecutor().submit(() -> {
				return RegistryLoader.create();
			});

			created = true;

			return future.get();

		} catch (Exception e) {
			e.printStackTrace();

			throw new IllegalStateException(
					"Error loading registry! An exception was caught during registry loading in the Registry class, which will lead to the failiure of execution. Program should now exit with failure.");
		}
	}

	public static boolean isCreated() {
		return created;
	}

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
