package vision.voltsofdoom.voltsofdoom.play.adventure;

import java.util.List;
import java.util.Objects;
import com.google.gson.JsonObject;
import vision.voltsofdoom.voltsofdoom.universal.resource.json.GsonHandler;

public class EntityMap {
  private List<KeyNode> key;
  private List<EntityKeyPlacementNode> map;

  public List<KeyNode> getKey() {
    return key;
  }

  public List<EntityKeyPlacementNode> getMap() {
    return map;
  }

  public static EntityMap fromJson(JsonObject json) {
    EntityMap entityMap = GsonHandler.GSON.fromJson(json, EntityMap.class);
    Objects.requireNonNull(entityMap.key, () -> "EntityMap#fromJson found key to be null.");
    Objects.requireNonNull(entityMap.map, () -> "EntityMap#fromJson found map to be null.");

    entityMap.key.forEach((key) -> {
      Objects.requireNonNull(key, () -> "EntityMap#fromJson found a KeyNode to be null.");
    });

    entityMap.map.forEach((map) -> {
      Objects.requireNonNull(map,
          () -> "EntityMap#fromJson found an EntityKeyPlacementNode to be null.");
    });

    return entityMap;
  }
}
