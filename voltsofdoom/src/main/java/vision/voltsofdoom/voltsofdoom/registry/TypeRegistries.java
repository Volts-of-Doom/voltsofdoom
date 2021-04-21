package vision.voltsofdoom.voltsofdoom.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vision.voltsofdoom.voltsofdoom.VoltsOfDoom;
import vision.voltsofdoom.voltsofdoom.adventure.Adventure;
import vision.voltsofdoom.voltsofdoom.entity.Entity;
import vision.voltsofdoom.voltsofdoom.tile.Tile;
import vision.voltsofdoom.zapbyte.event.RegistryEvent;
import vision.voltsofdoom.zapbyte.event.Stowaway;
import vision.voltsofdoom.zapbyte.registry.TypeRegistry;
import vision.voltsofdoom.zapbyte.resource.ResourceLocation;

public class TypeRegistries {
  
  public static final Logger LOGGER = LoggerFactory.getLogger(TypeRegistries.class);

  public static TypeRegistry<Tile> TILES;
  public static TypeRegistry<Entity> ENTITIES;
  public static TypeRegistry<Adventure> ADVENTURES;

  @Stowaway
  private static void createAndSubmitTypeRegistries(RegistryEvent.CreateAndSubmitRegistriesEvent event) {
    LOGGER.debug("Creating and submitting default TypeRegistries");

    TILES = new TypeRegistry<Tile>(new ResourceLocation(VoltsOfDoom.getId(), "tiles"), RegistryTypes.TILES);
    ENTITIES = new TypeRegistry<Entity>(new ResourceLocation(VoltsOfDoom.getId(), "entities"), RegistryTypes.ENTITIES);
    ADVENTURES = new TypeRegistry<Adventure>(new ResourceLocation(VoltsOfDoom.getId(), "adventures"), RegistryTypes.ADVENTURES);

    event.submit(TILES);
    event.submit(ENTITIES);
    event.submit(ADVENTURES);
  }
}
