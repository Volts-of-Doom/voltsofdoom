package vision.voltsofdoom.voltsofdoom.universal.registry;

import vision.voltsofdoom.voltsofdoom.play.adventure.Adventure;
import vision.voltsofdoom.voltsofdoom.play.entity.Entity;
import vision.voltsofdoom.voltsofdoom.play.tile.Tile;
import vision.voltsofdoom.voltsofdoom.universal.main.VoltsOfDoom;
import vision.voltsofdoom.zapbyte.event.RegistryEvent;
import vision.voltsofdoom.zapbyte.event.Stowaway;
import vision.voltsofdoom.zapbyte.registry.TypeRegistry;
import vision.voltsofdoom.zapbyte.resource.ResourceLocation;

public class TypeRegistries {

  public static TypeRegistry<Tile> TILES;
  public static TypeRegistry<Entity> ENTITIES;
  public static TypeRegistry<Adventure> ADVENTURES;

  @Stowaway
  private static void createAndSubmitTypeRegistries(
      RegistryEvent.CreateAndSubmitRegistriesEvent event) {
    VoltsOfDoom.easyDebug("Creating and submitting default TypeRegistries");

    TILES = new TypeRegistry<Tile>(new ResourceLocation(VoltsOfDoom.getId(), "tiles"),
        RegistryTypes.TILES);
    ENTITIES = new TypeRegistry<Entity>(new ResourceLocation(VoltsOfDoom.getId(), "entities"),
        RegistryTypes.ENTITIES);
    ADVENTURES = new TypeRegistry<Adventure>(
        new ResourceLocation(VoltsOfDoom.getId(), "adventures"), RegistryTypes.ADVENTURES);

    event.submit(TILES);
    event.submit(ENTITIES);
    event.submit(ADVENTURES);
  }
}
