package vision.voltsofdoom.voltsofdoom.universal.resource.image;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class TexturePackManifest  {

  public static JsonObject serialise() {
    JsonObject object = new JsonObject();

    return object;
  }

  public static TexturePackManifest deserialise(JsonObject json) {
    JsonArray mappings = json.get("mappings").getAsJsonArray();
    
    return null;
  }

}
