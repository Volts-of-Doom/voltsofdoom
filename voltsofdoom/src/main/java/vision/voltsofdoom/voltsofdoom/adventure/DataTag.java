package vision.voltsofdoom.voltsofdoom.adventure;

import java.lang.reflect.Type;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import vision.voltsofdoom.voltsofdoom.VoltsOfDoom;

public class DataTag {
  public static final DataTagDeserializer DESERIALIZER = new DataTagDeserializer();
  private String key;
  private String value;

  public DataTag(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  public static class DataTagDeserializer implements JsonDeserializer<DataTag> {

    @Override
    public DataTag deserialize(JsonElement element, Type type, JsonDeserializationContext context)
        throws JsonParseException {

      if (!type.equals(DataTag.class)) {
        VoltsOfDoom.getInstance().getApplicationLogger()
            .error("Illegal type for DataTagMapDeserializer: " + type.getTypeName());
        return null;
      }

      JsonObject object = element.getAsJsonObject();
      return new DataTag(object.get("key").getAsString(), object.get("value").getAsString());
    }

  }
}
