package vision.voltsofdoom.api.zapyte.config;

import java.io.InputStream;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * NOTE: SHOULD BUILD A MASTER CONFIGURATION FILE OUT OF ALL OF THE INPUT FILES. I.E. MAKE ONE BIG
 * JSON, WHICH CAN THEN BE QUERIED! THIS SEEMS LIKE A FAR MORE LOGICAL APPROACH!
 * 
 * @author GenElectrovise
 *
 */
public interface IConfigurationHandler {

  /**
   * @param key
   * @return The option specified by the given key.
   */
  JsonElement getOption(String key);

  /**
   * {@link #add(JsonElement)}
   */
  void add(InputStream source);

  /**
   * Adds the given {@link JsonElement} as a tracked option.
   */
  void add(JsonElement source);

  /**
   * @param key
   * @return The option removed, specified by the given key.
   */
  JsonElement remove(String key);

  /**
   * @return All tracked options.
   */
  JsonObject getOptions();
}
