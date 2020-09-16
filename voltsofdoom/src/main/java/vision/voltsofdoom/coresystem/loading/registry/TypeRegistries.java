package vision.voltsofdoom.coresystem.loading.registry;

import vision.voltsofdoom.coresystem.play.adventure.Adventure;
import vision.voltsofdoom.coresystem.play.entity.Entity;
import vision.voltsofdoom.coresystem.play.tile.Tile;
import vision.voltsofdoom.coresystem.universal.band_wagon.Stowaway;
import vision.voltsofdoom.coresystem.universal.event.RegistryEvent;
import vision.voltsofdoom.coresystem.universal.main.VoltsOfDoomCoreSystem;
import vision.voltsofdoom.zapbyte.log.Loggers;
import vision.voltsofdoom.zapbyte.misc.ResourceLocation;

public class TypeRegistries {

	public static TypeRegistry<Tile> TILES;
	public static TypeRegistry<Entity> ENTITIES;
	public static TypeRegistry<Adventure> ADVENTURES;

	@Stowaway
	private static void createAndSubmitTypeRegistries(RegistryEvent.CreateAndSubmitRegistriesEvent event) {
		Loggers.ZAPBYTE_LOADING_REGISTRY.fine("Creating and submitting default TypeRegistries");
		
		TILES = new TypeRegistry<Tile>(new ResourceLocation(VoltsOfDoomCoreSystem.ID, "tiles"), RegistryTypes.TILES);
		ENTITIES = new TypeRegistry<Entity>(new ResourceLocation(VoltsOfDoomCoreSystem.ID, "entities"),
				RegistryTypes.ENTITIES);
		ADVENTURES = new TypeRegistry<Adventure>(new ResourceLocation(VoltsOfDoomCoreSystem.ID, "adventures"), RegistryTypes.ADVENTURES);
		
		event.submit(TILES);
		event.submit(ENTITIES);
		event.submit(ADVENTURES);
	}
}
