package vision.voltsofdoom.voltsofdoom.universal.registry;

import com.google.common.collect.ImmutableList;
import vision.voltsofdoom.voltsofdoom.play.adventure.Adventure;
import vision.voltsofdoom.voltsofdoom.play.entity.Entity;
import vision.voltsofdoom.voltsofdoom.play.tile.Tile;
import vision.voltsofdoom.voltsofdoom.universal.main.VoltsOfDoomCoreSystem;
import vision.voltsofdoom.zapbyte.event.RegistryEvent;
import vision.voltsofdoom.zapbyte.event.Stowaway;
import vision.voltsofdoom.zapbyte.main.ZapByte;
import vision.voltsofdoom.zapbyte.registry.RegistryType;
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
    TILES = event.createRegistryType(new ResourceLocation(VoltsOfDoomCoreSystem.getId(), "tiles"),
        Tile.class);
    ZapByte.LOGGER.info("Creating VOD RegistryTypes");
    ENTITIES = event.createRegistryType(
        new ResourceLocation(VoltsOfDoomCoreSystem.getId(), "entities"), Entity.class);
    ADVENTURES = event.createRegistryType(
        new ResourceLocation(VoltsOfDoomCoreSystem.getId(), "adventures"), Adventure.class);

    vision.voltsofdoom.zapbyte.registry.RegistryTypes.prioritisedTypes =
        ImmutableList.of(TILES, ENTITIES);
  }
}
