package vision.voltsofdoom.coresystem.play.adventure;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import vision.voltsofdoom.coresystem.universal.main.VoltsOfDoomCoreSystem;

/**
 * Represents a {@link Map} of {@link DataTag}s.
 * 
 * @author GenElectrovise
 *
 */
public class DataTagMap {
  public static final DataTagMapDeserializer DESERIALIZER = new DataTagMapDeserializer();

  /**
   * Typically a {@link LinkedHashMap}.
   */
  private Map<String, DataTag> tagMap;

  public DataTagMap(List<DataTag> tags) {
    this(tagsToMap(tags));
  }

  public DataTagMap(Map<String, DataTag> tagMap) {
    this.tagMap = tagMap;
  }

  public List<DataTag> getTags() {
    return new ArrayList<DataTag>(tagMap.values());
  }

  /**
   * @param tags
   * @return A {@link LinkedHashMap} of the given {@link DataTag}s.
   */
  public static Map<String, DataTag> tagsToMap(List<DataTag> tags) {

    Map<String, DataTag> map = new LinkedHashMap<String, DataTag>();

    tags.forEach((tag) -> {
      map.put(tag.getKey(), tag);
    });

    return map;
  }

  public static class DataTagMapDeserializer implements JsonDeserializer<DataTagMap> {

    @Override
    public DataTagMap deserialize(JsonElement element, Type type,
        JsonDeserializationContext context) throws JsonParseException {

      if (!type.equals(DataTagMap.class)) {
        VoltsOfDoomCoreSystem.
            easyInfo("Illegal type for DataTagMapDeserializer: " + type.getTypeName());
        return null;
      }

      Map<String, DataTag> map = new HashMap<String, DataTag>();
      JsonObject object = element.getAsJsonObject();
      
      if(!object.has("tags")) {
        VoltsOfDoomCoreSystem.getInstance().getApplicationLogger().warn("DataMapDeserializer found a member named 'tags' from an unknown JSON file to not exist. A basic DataTagMap(HashMap) will be injected in its place.");
        return new DataTagMap(new HashMap<String, DataTag>());
      }
      
      JsonArray array = object.get("tags").getAsJsonArray();

      // Each {tag} object (members of the "tags" array)
      array.forEach((tagElem) -> {
        JsonObject tagObj = tagElem.getAsJsonObject();

        DataTag dataTag = DataTag.DESERIALIZER.deserialize(tagObj, DataTag.class, context);

        map.put(dataTag.getKey(), dataTag);
      });

      return new DataTagMap(map);
    }

  }
}
