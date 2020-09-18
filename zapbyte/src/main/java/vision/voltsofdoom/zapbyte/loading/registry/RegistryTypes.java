package vision.voltsofdoom.zapbyte.loading.registry;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;

import com.google.common.collect.ImmutableList;

import vision.voltsofdoom.api.zapyte.misc.IResourceLocation;
import vision.voltsofdoom.zapbyte.misc.ResourceLocation;

/**
 * Manages {@link RegistryType}s for the application.
 * 
 * @author GenElectrovise
 *
 */
public class RegistryTypes {
	private static volatile LinkedHashMap<ResourceLocation, RegistryType<?>> types = new LinkedHashMap<ResourceLocation, RegistryType<?>>();

	public static volatile ImmutableList<RegistryType<? extends IRegistryEntry<?>>> prioritisedTypes;

	public static Iterator<RegistryType<?>> getIterator() {
		return types.values().iterator();
	}

	/**
	 * Creates and registers a new {@link RegistryType}, which is necessary for
	 * adding objects to the game which do not exist in the
	 * {@link VoltsOfDoomCoreSystem}.
	 * 
	 * @param identifier The {@link ResourceLocation} identifier for the created
	 *                   {@link RegistryType}.
	 * @param clazzType  The {@link Class} of object which will be registered by
	 *                   {@link IRegistry}s of the created {@link RegistryType}.
	 * @return The completed {@link RegistryType}
	 */
	public static synchronized <T extends IRegistryEntry<T>> RegistryType<T> create(ResourceLocation identifier, Class<T> clazzType) {

		Objects.requireNonNull(identifier, () -> "Cannot create a RegistryType with a null identifier.");
		Objects.requireNonNull(clazzType, () -> "Cannot create a RegistryType to register objects of type null. Class type cannot be null.");

		RegistryType<T> registryType = new RegistryType<T>(identifier, clazzType);
		types.put(identifier, registryType);
		return registryType;
	}

	/**
	 * @param identifier
	 * @return A {@link RegistryType} by its identifier, if it exists.
	 */
	public static RegistryType<?> get(ResourceLocation identifier) {
		Objects.requireNonNull(identifier, () -> "Cannot get a RegistryType from a null identifier.");

		return types.get(identifier);
	}

	/**
	 * 
	 * @param type
	 * @return The {@link ResourceLocation} of the given {@link RegistryType}, if it
	 *         exists.
	 */
	public static IResourceLocation getInverted(RegistryType<?> type) {

		Objects.requireNonNull(type, () -> "Cannot get an identifier from a null RegistryType.");

		for (IResourceLocation id : types.keySet()) {
			if (types.get(id).equals(type)) {
				return id;
			}
		}

		throw new NullPointerException("The specified RegistryType does not exist.");
	}

}
