package vision.voltsofdoom.coresystem.universal.registry;

import com.google.common.collect.ImmutableList;

import vision.voltsofdoom.coresystem.play.adventure.Adventure;
import vision.voltsofdoom.coresystem.play.entity.Entity;
import vision.voltsofdoom.coresystem.play.tile.Tile;
import vision.voltsofdoom.coresystem.universal.main.VoltsOfDoomCoreSystem;
import vision.voltsofdoom.zapbyte.bandwagon.Stowaway;
import vision.voltsofdoom.zapbyte.event.RegistryEvent;
import vision.voltsofdoom.zapbyte.loading.registry.RegistryType;
import vision.voltsofdoom.zapbyte.resource.ResourceLocation;


/**
 * Holds Volts of Doom's {@link RegistryType}s.
 * 
 * @author GenElectrovise
 *
 */
public class RegistryTypes {

	public static RegistryType<Tile> TILES;
	public static RegistryType<Entity> ENTITIES;
	public static RegistryType<Adventure> ADVENTURES;

	@Stowaway
	private static void generateTypes(RegistryEvent.CreateRegistryTypesEvent event) {
		TILES = event.createRegistryType(new ResourceLocation(VoltsOfDoomCoreSystem.ID, "tiles"), Tile.class);
		ENTITIES = event.createRegistryType(new ResourceLocation(VoltsOfDoomCoreSystem.ID, "entities"), Entity.class);
		ADVENTURES = event.createRegistryType(new ResourceLocation(VoltsOfDoomCoreSystem.ID, "adventures"), Adventure.class);

		vision.voltsofdoom.zapbyte.loading.registry.RegistryTypes.prioritisedTypes = ImmutableList.of(TILES, ENTITIES);
	}
}
