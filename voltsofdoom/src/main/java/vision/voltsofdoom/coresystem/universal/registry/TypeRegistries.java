package vision.voltsofdoom.coresystem.universal.registry;

import vision.voltsofdoom.coresystem.play.adventure.Adventure;
import vision.voltsofdoom.coresystem.play.entity.Entity;
import vision.voltsofdoom.coresystem.play.tile.Tile;
import vision.voltsofdoom.coresystem.universal.main.VoltsOfDoomCoreSystem;
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
    VoltsOfDoomCoreSystem.getInstance().getApplicationLogger().debug("Creating and submitting default TypeRegistries");

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
