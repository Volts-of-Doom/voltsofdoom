package vision.voltsofdoom.voltsofdoom.universal.registry;

import vision.voltsofdoom.voltsofdoom.play.adventure.Adventure;
import vision.voltsofdoom.voltsofdoom.play.entity.Entity;
import vision.voltsofdoom.voltsofdoom.play.tile.Tile;
import vision.voltsofdoom.voltsofdoom.universal.main.VoltsOfDoomCoreSystem;
import vision.voltsofdoom.zapbyte.event.RegistryEvent;
import vision.voltsofdoom.zapbyte.event.Stowaway;
import vision.voltsofdoom.zapbyte.loading.registry.TypeRegistry;
import vision.voltsofdoom.zapbyte.resource.ResourceLocation;

public class TypeRegistries {

  public static TypeRegistry<Tile> TILES;
  public static TypeRegistry<Entity> ENTITIES;
  public static TypeRegistry<Adventure> ADVENTURES;

  @Stowaway
  private static void createAndSubmitTypeRegistries(
      RegistryEvent.CreateAndSubmitRegistriesEvent event) {
    VoltsOfDoomCoreSystem.easyDebug("Creating and submitting default TypeRegistries");

    TILES = new TypeRegistry<Tile>(new ResourceLocation(VoltsOfDoomCoreSystem.getId(), "tiles"),
        RegistryTypes.TILES);
    ENTITIES = new TypeRegistry<Entity>(new ResourceLocation(VoltsOfDoomCoreSystem.getId(), "entities"),
        RegistryTypes.ENTITIES);
    ADVENTURES = new TypeRegistry<Adventure>(
        new ResourceLocation(VoltsOfDoomCoreSystem.getId(), "adventures"), RegistryTypes.ADVENTURES);

    event.submit(TILES);
    event.submit(ENTITIES);
    event.submit(ADVENTURES);
  }
}
