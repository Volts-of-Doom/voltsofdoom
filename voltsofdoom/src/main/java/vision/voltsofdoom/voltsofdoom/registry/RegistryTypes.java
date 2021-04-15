package vision.voltsofdoom.voltsofdoom.registry;

import com.google.common.collect.ImmutableList;
import vision.voltsofdoom.voltsofdoom.adventure.Adventure;
import vision.voltsofdoom.voltsofdoom.entity.Entity;
import vision.voltsofdoom.voltsofdoom.main.VoltsOfDoom;
import vision.voltsofdoom.voltsofdoom.tile.Tile;
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
    TILES = event.createRegistryType(new ResourceLocation(VoltsOfDoom.getId(), "tiles"),
        Tile.class);
    ZapByte.LOGGER.info("Creating VOD RegistryTypes");
    ENTITIES = event.createRegistryType(
        new ResourceLocation(VoltsOfDoom.getId(), "entities"), Entity.class);
    ADVENTURES = event.createRegistryType(
        new ResourceLocation(VoltsOfDoom.getId(), "adventures"), Adventure.class);

    vision.voltsofdoom.zapbyte.registry.RegistryTypes.prioritisedTypes =
        ImmutableList.of(TILES, ENTITIES);
  }
}
