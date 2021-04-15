package vision.voltsofdoom.voltsofdoom.resource.json;

import java.util.function.Function;
import com.google.gson.JsonObject;

/**
 * Note: this class may or may not be entirely useless but it was a fun experiment... <br>
 * <br>
 * A utility class for de/serialising Java and JSON objects. <br>
 * <br>
 * <code>JsonSerialisation.deserialise(IJsonSerialisable::deserialise, json)</code><br>
 * <code>JsonSerialisation.serialise(IJsonSerialisable::serialise, instance)</code>
 * 
 * @author GenElectrovise
 *
 */
public class JsonSerialisation {

  /**
   * 
   * @param instructions A lambda expression, typically denoted here with a double-colon, for a method
   *        which takes a {@link JsonObject} (which provides the instructions for deserialising), as
   *        well as the instance to deserialise from.
   * @param json The instance of {@link JsonObject} to deserialise from.
   * @return 
   */
  public static <T> T deserialise(Function<JsonObject, T> instructions, JsonObject json) {
    return instructions.apply(json);
  }

  /**
   * 
   * @param instructions A lambda expression, typically denoted here with a double-colon, for a method
   *        which takes an instance of the class to serialise (which provides the instructions for
   *        serialising), as well as the instance to serialise.
   * @param instance The instance of the class to serialise.
   */
  public static <T> JsonObject serialise(Function<T, JsonObject> instructions, T instance) {
    return instructions.apply(instance);
  }
}
