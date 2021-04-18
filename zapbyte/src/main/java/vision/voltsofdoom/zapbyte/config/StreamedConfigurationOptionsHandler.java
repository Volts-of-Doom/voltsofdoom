package vision.voltsofdoom.zapbyte.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import vision.voltsofdoom.api.zapyte.config.IConfigurationOptionsHandler;

public class StreamedConfigurationOptionsHandler implements IConfigurationOptionsHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(StreamedConfigurationOptionsHandler.class);

  private JsonObject master;
  private Gson gson;

  public StreamedConfigurationOptionsHandler(Gson gson) {
    this.master = new JsonObject();
    this.gson = gson;
  }

  public StreamedConfigurationOptionsHandler(Gson gson, InputStream presetJsonSource) {
    this.master = gson.fromJson(new InputStreamReader(presetJsonSource), JsonObject.class);
    this.gson = gson;
  }

  @Override
  public JsonElement getOption(String key) {
    return master.get(key);
  }

  @Override
  public void add(String key, InputStream source) {
    add(key, gson.fromJson(new InputStreamReader(source), JsonElement.class));
  }

  @Override
  public void add(String key, JsonElement source) {
    master.add(key, source);
  }

  @Override
  public JsonElement remove(String key) {
    return master.remove(key);
  }

  @Override
  public JsonObject getOptions() {
    return master;
  }

  @Override
  public void flatten() {
    JsonObject flattened = flatten0(master);
    LOGGER.debug("Flattened configs to: " + flattened.toString());

    JsonObject cleaned = cleanUp(flattened);
    LOGGER.debug("Cleaned configs to: " + cleaned.toString());

    System.exit(0);

    master = cleaned;
  }

  private JsonObject flatten0(JsonObject toFlatten) {
    JsonObject flattened = new JsonObject();
    flatten1("", toFlatten, flattened);
    return flattened;
  }

  private void flatten1(String prefix, JsonObject toFlatten, JsonObject toMutate) {
    for (Entry<String, JsonElement> entry : toFlatten.entrySet()) {
      String keyWithPrefix = prefix + entry.getKey();

      if (entry.getValue() instanceof JsonObject) {
        flatten1(keyWithPrefix + ".", (JsonObject) entry.getValue(), toMutate);
      } else {
        toMutate.add(keyWithPrefix, entry.getValue());
      }
    }
  }

  private JsonObject cleanUp(JsonObject flattened) {
    
    JsonObject newObject = new JsonObject();
    
    flattened.entrySet().forEach((entry) -> {
      if (entry.getKey().startsWith(ConfigurationFileSerializer.OTHER_FILES_DEFAULT_KEY)) {
        String newKey = entry.getKey().replaceFirst(ConfigurationFileSerializer.OTHER_FILES_DEFAULT_KEY + ".", "");
        newObject.add(newKey, entry.getValue());
      }
    });
    
    return newObject;
  }

}
