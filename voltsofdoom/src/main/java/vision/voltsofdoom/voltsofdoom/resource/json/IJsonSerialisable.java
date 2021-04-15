package vision.voltsofdoom.voltsofdoom.resource.json;

import com.google.gson.JsonObject;

/**
 * An object which can be read to and from a JSON file.
 * 
 * @author GenElectrovise
 *
 * @param <T>
 */
public interface IJsonSerialisable {

  /**
   * Convert to JSON
   */
  public JsonObject serialise();

  /**
   * Read from JSON
   */
  public void deserialise(JsonObject json);
}
