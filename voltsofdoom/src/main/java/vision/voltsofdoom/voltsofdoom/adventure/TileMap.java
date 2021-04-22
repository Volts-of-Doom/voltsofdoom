package vision.voltsofdoom.voltsofdoom.adventure;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vision.voltsofdoom.voltsofdoom.VoltsOfDoom;
import vision.voltsofdoom.voltsofdoom.tile.Tile;
import vision.voltsofdoom.zapbyte.resource.ResourceLocation;

/**
 * The in-game map of {@link Tile}s.
 * 
 * @author GenElectrovise
 *
 */
public class TileMap {

  public static final Logger LOGGER = LoggerFactory.getLogger(TileMap.class);

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

        KeyNode node = KeyMaps.getNodeByKey(key, id);
        ResourceLocation resourceLocation = node.getIdentifier();

        Supplier<Tile> tileSupp = VoltsOfDoom.getInstance().getRegistry().getSupplier(resourceLocation, Tile.class);

        Tile tile = (Tile) tileSupp.get();

        tilesToAdd.add(tile);
      }

      tiles.add(tilesToAdd);
    }

    LOGGER.info("Finished loading " + this.getClass().getSimpleName() + " for latest Level");
  }

  public List<List<Tile>> getTiles() {
    return tiles;
  }
}
