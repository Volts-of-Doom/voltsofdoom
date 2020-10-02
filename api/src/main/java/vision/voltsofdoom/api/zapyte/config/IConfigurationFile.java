package vision.voltsofdoom.api.zapyte.config;

import java.util.Map;

public interface IConfigurationFile {

  /**
   * A {@link #BLANK} {@link IConfigurationFile} :)
   */
  IConfigurationFile BLANK = new IConfigurationFile() {

    private Map<String, String> arguments;

    @Override
    public void putArgument(String key, String value) {
      arguments.put(key, value);
    }

    @Override
    public Map<String, String> getArguments() {
      return arguments;
    }
  };

  void putArgument(String key, String value);

  Map<String, String> getArguments();

  String toString();

}
