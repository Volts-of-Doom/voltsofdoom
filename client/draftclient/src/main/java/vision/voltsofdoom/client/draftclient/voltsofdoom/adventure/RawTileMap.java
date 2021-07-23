package vision.voltsofdoom.client.draftclient.voltsofdoom.adventure;

import java.util.List;
import java.util.Objects;
import com.google.gson.JsonObject;
import vision.voltsofdoom.client.draftclient.voltsofdoom.tile.Tile;

/**
 * The map of tiles, as read directly from a .zip file. To obtain {@link Tile} objects, use the
 * class {@link TileMap}.
 * 
 * @author GenElectrovise
 *
 */
public class RawTileMap {
  private List<KeyNode> key;
  private List<List<String>> map;

  public List<List<String>> getMap() {
    return map;
  }

  public List<KeyNode> getKey() {
    return key;
  }

  public static RawTileMap fromJson(JsonObject json) {
    RawTileMap tileMap = GsonHandler.GSON.fromJson(json, RawTileMap.class);
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

  public TileMap generateTwoDimensionalListOfTileObjects() {    
    return new TileMap(map, key);
  }
}
