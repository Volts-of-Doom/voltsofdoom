package vision.voltsofdoom.zapbyte.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import vision.voltsofdoom.api.zapyte.config.IConfigurationOptionsHandler;

public class StreamedConfigurationOptionsHandler implements IConfigurationOptionsHandler {

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
  public void standardize() {
    
  }

}
