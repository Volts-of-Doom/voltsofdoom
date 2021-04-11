package vision.voltsofdoom.voltsofdoom.play.adventure;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import vision.voltsofdoom.voltsofdoom.play.tile.Tile;
import vision.voltsofdoom.voltsofdoom.universal.main.VoltsOfDoom;
import vision.voltsofdoom.voltsofdoom.universal.registry.RegistryTypes;
import vision.voltsofdoom.zapbyte.registry.IFinalisedRegistry;
import vision.voltsofdoom.zapbyte.registry.Registry;
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

    VoltsOfDoom
        .easyInfo("Finished loading " + this.getClass().getSimpleName() + " for latest Level");
  }

  public List<List<Tile>> getTiles() {
    return tiles;
  }
}
