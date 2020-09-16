package vision.voltsofdoom.coresystem.loading.registry;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;

import com.google.common.collect.ImmutableList;

import vision.voltsofdoom.coresystem.play.adventure.Adventure;
import vision.voltsofdoom.coresystem.play.entity.Entity;
import vision.voltsofdoom.coresystem.play.tile.Tile;
import vision.voltsofdoom.coresystem.universal.band_wagon.Stowaway;
import vision.voltsofdoom.coresystem.universal.event.RegistryEvent;
import vision.voltsofdoom.coresystem.universal.main.VoltsOfDoomCoreSystem;
import vision.voltsofdoom.zapbyte.misc.ResourceLocation;

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
	private static volatile LinkedHashMap<ResourceLocation, RegistryType<?>> types = new LinkedHashMap<ResourceLocation, RegistryType<?>>();

	public static RegistryType<Tile> TILES;
	public static RegistryType<Entity> ENTITIES;
	public static RegistryType<Adventure> ADVENTURES;

	public static volatile ImmutableList<RegistryType<? extends IRegistryEntry<?>>> prioritisedTypes;

	@Stowaway
	private static void generateTypes(RegistryEvent.CreateRegistryTypesEvent event) {
		TILES = event.createRegistryType(new ResourceLocation(VoltsOfDoomCoreSystem.ID, "tiles"), Tile.class);
		ENTITIES = event.createRegistryType(new ResourceLocation(VoltsOfDoomCoreSystem.ID, "entities"), Entity.class);
		ADVENTURES = event.createRegistryType(new ResourceLocation(VoltsOfDoomCoreSystem.ID, "adventures"), Adventure.class);

		prioritisedTypes = ImmutableList.of(TILES, ENTITIES);
	}

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
	public static synchronized <T extends IRegistryEntry<T>> RegistryType<T> create(ResourceLocation identifier,
			Class<T> clazzType) {

		Objects.requireNonNull(identifier, () -> "Cannot create a RegistryType with a null identifier.");
		Objects.requireNonNull(clazzType,
				() -> "Cannot create a RegistryType to register objects of type null. Class type cannot be null.");

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
	public static ResourceLocation getInverted(RegistryType<?> type) {

		Objects.requireNonNull(type, () -> "Cannot get an identifier from a null RegistryType.");

		for (ResourceLocation id : types.keySet()) {
			if (types.get(id).equals(type)) {
				return id;
			}
		}

		throw new NullPointerException("The specified RegistryType does not exist.");
	}

}
