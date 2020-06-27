package vision.voltsofdoom.coresystem.loading.registry.type;

import java.util.LinkedHashMap;
import java.util.Objects;

import vision.voltsofdoom.coresystem.play.entity.Entity;
import vision.voltsofdoom.coresystem.play.tile.Tile;
import vision.voltsofdoom.coresystem.universal.main.VoltsOfDoomCoreSystem;
import vision.voltsofdoom.coresystem.loading.resource.ResourceLocation;

/**
 * Holds the default {@link RegistryType}s for the
 * {@link VoltsOfDoomCoreSystem}, plus provides the
 * {@link RegistryTypes#create(ResourceLocation, Class)} method, by which
 * modders can create their own {@link RegistryType}.
 * 
 * @author GenElectrovise
 *
 */
public class RegistryTypes {
	private static LinkedHashMap<ResourceLocation, RegistryType> types = new LinkedHashMap<ResourceLocation, RegistryType>();

	public static final RegistryType TILES = create(new ResourceLocation("coresystem", "tiles"), Tile.class);
	public static final RegistryType ENTITIES = create(new ResourceLocation("coresystem", "tiles"), Entity.class);

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
	public static RegistryType create(ResourceLocation identifier, Class<? extends IRegistryEntry<?>> clazzType) {

		Objects.requireNonNull(identifier, () -> "Cannot create a RegistryType with a null identifier.");
		Objects.requireNonNull(clazzType,
				() -> "Cannot create a RegistryType to register objects of type null. Class type cannot be null.");

		RegistryType registryType = new RegistryType(identifier, clazzType);
		types.put(identifier, registryType);
		return registryType;
	}

	/**
	 * @param identifier
	 * @return A {@link RegistryType} by its identifier, if it exists.
	 */
	public static RegistryType get(ResourceLocation identifier) {
		Objects.requireNonNull(identifier, () -> "Cannot get a RegistryType from a null identifier.");
		return types.get(identifier);
	}

	/**
	 * 
	 * @param type
	 * @return The {@link ResourceLocation} of the given {@link RegistryType}, if it
	 *         exists.
	 */
	public static ResourceLocation getInverted(RegistryType type) {

		Objects.requireNonNull(type, () -> "Cannot get an identifier from a null RegistryType.");

		for (ResourceLocation id : types.keySet()) {
			if (types.get(id).equals(type)) {
				return id;
			}
		}

		throw new NullPointerException("The specified RegistryType does not exist.");
	}

}
