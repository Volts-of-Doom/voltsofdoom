package vision.voltsofdoom.coresystem.play.adventure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import vision.voltsofdoom.coresystem.play.tile.Tile;
import vision.voltsofdoom.coresystem.universal.registry.RegistryTypes;
import vision.voltsofdoom.zapbyte.loading.registry.IFinalisedRegistry;
import vision.voltsofdoom.zapbyte.loading.registry.Registry;
import vision.voltsofdoom.zapbyte.log.Loggers;
import vision.voltsofdoom.zapbyte.resource.ResourceLocation;

/**
 * The map of tiles, as read directly from a .zip file. To obtain {@link Tile} objects, use the
 * nested class {@link TrueMap}.
 * 
 * @author GenElectrovise
 *
 */
public class TileMap {
  private List<KeyNode> key;
  private List<List<String>> map;

  public List<List<String>> getMap() {
    return map;
  }

  public List<KeyNode> getKey() {
    return key;
  }

  public static TileMap fromJson(JsonObject json) {
    TileMap tileMap = new Gson().fromJson(json, TileMap.class);
    Objects.requireNonNull(tileMap.key, () -> "TileMap#fromJson found key to be null.");
    Objects.requireNonNull(tileMap.map, () -> "TileMap#fromJson found map to be null.");

    tileMap.key.forEach((key) -> {
      Objects.requireNonNull(key, () -> "TileMap#fromJson found a KeyNode to be null.");
    });

    tileMap.map.forEach((map) -> {
      Objects.requireNonNull(map,
          () -> "TileMap#fromJson found an EntityKeyPlacementNode to be null.");
    });

    return tileMap;
  }

  public TrueMap generateTwoDimensionalListOfTileObjects() {
    return new TrueMap(map, key);
  }

  /**
   * The true map of tiles.
   * 
   * @author GenElectrovise
   *
   */
  public static class TrueMap {
    private List<List<Tile>> tiles = new ArrayList<List<Tile>>();

    public TrueMap(List<List<String>> map, List<KeyNode> key) {

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
}
