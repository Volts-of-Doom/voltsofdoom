package vision.voltsofdoom.coresystem.play.adventure;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import vision.voltsofdoom.coresystem.play.tile.Tile;
import vision.voltsofdoom.coresystem.universal.registry.RegistryTypes;
import vision.voltsofdoom.zapbyte.loading.registry.IFinalisedRegistry;
import vision.voltsofdoom.zapbyte.loading.registry.Registry;
import vision.voltsofdoom.zapbyte.log.Loggers;
import vision.voltsofdoom.zapbyte.resource.ResourceLocation;

/**
 * The in-game map of {@link Tile}s.
 * 
 * @author GenElectrovise
 *
 */
public class TileMap {
  private List<List<Tile>> tiles = new ArrayList<List<Tile>>();
  
  public TileMap(RawTileMap raw) {
    this(raw.getMap(), raw.getKey());
  }

  public TileMap(List<List<String>> map, List<KeyNode> key) {

    // Each list of the map.
    for (List<String> subList : map) {

      List<Tile> tilesToAdd = new ArrayList<Tile>();
      // Each ID string in the map.
      for (String id : subList) {

        @SuppressWarnings("unchecked")
        IFinalisedRegistry<Tile> tileFReg =
            (IFinalisedRegistry<Tile>) Registry.getTyped(RegistryTypes.TILES);

        KeyNode node = KeyMaps.getNodeByKey(key, id);
        ResourceLocation resourceLocation = node.getIdentifier();

        Supplier<Tile> tileSupp = tileFReg.retrieveSupplier(resourceLocation);

        Tile tile = (Tile) tileSupp.get();

        tilesToAdd.add(tile);
      }

      tiles.add(tilesToAdd);
    }

    Loggers.ZAPBYTE_LOADING_RESOURCE
        .info("Finished loading " + this.getClass().getSimpleName() + " for latest Level");
  }

  public List<List<Tile>> getTiles() {
    return tiles;
  }
}