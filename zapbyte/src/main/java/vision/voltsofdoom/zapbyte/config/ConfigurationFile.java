package vision.voltsofdoom.zapbyte.config;

import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import vision.voltsofdoom.api.zapyte.config.IConfigurationFile;

public class ConfigurationFile implements IConfigurationFile {
  private Map<String, String> arguments;

  @Override
  public void putArgument(String key, String value) {
    arguments.put(key, value);
  }

  @Override
  public Map<String, String> getArguments() {
    return arguments;
  }

  public static IConfigurationFile fromJson(JsonObject jsonObject) {
    ConfigurationFile configurationFile = new Gson().fromJson(jsonObject, ConfigurationFile.class);
    return configurationFile;
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
