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
public interface IConfigurationOptionsHandler {

  /**
   * Standardises the format of the contents. For example, the following tree: <br>
   * <code>
   * {<br>
   * "config" : {<br>
   * ~   "option_one" : {<br>
   * ~   ~   "option_two" : "result_one"<br>
   * ~   ~   }<br>
   * ~   }<br>
   * }<br>
   * </code> ... will be compressed to: <br>
   * <code>
   * "config.option_one.option_two" : "result_one"</code>
   */
  void standardize();

  /**
   * @param key
   * @return The option specified by the given key.
   */
  JsonElement getOption(String key);

  /**
   * {@link #add(JsonElement)}
   */
  void add(String key, InputStream source);

  /**
   * Adds the given {@link JsonElement} as a tracked option.
   */
  void add(String key, JsonElement source);

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
