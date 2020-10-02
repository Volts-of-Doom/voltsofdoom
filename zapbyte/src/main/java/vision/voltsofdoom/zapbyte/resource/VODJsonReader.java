package vision.voltsofdoom.zapbyte.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * A handy class for reading the contents of .json files. Should use convenience methods in Gson (
 * eg getAsString and getAsInteger ) to get the contents of obj ( obtained by getObj ) as your
 * wanted type. For example: <br>
 * <br>
 * <code>double myDouble = obj.getAsDouble()</code> <br>
 * <br>
 * You could then append <code>.toString</code> to the end if you so wished.
 * 
 * @author GenElectrovise
 *
 */
public class VODJsonReader {
  private Gson gson;
  private File file;
  private JsonObject obj;

  /**
   * @param file .json file to read from
   * @param reciever Class type to parse to.
   */
  public VODJsonReader(File file) {
    try {
      this.gson = new Gson();
      this.file = file;
      this.obj = fileToJsonObj();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Takes the file and parses it into a JsonObject.
   * 
   * @throws FileNotFoundException
   * @throws JsonIOException
   * @throws JsonSyntaxException
   */
  private JsonObject fileToJsonObj()
      throws JsonSyntaxException, JsonIOException, FileNotFoundException {
    return gson.fromJson(new BufferedReader(new FileReader(file)), JsonObject.class);
  }

  /**
   * @param key A String value to get the JsonElement of.
   * @return A JsonElement associated with the name of the key.
   */
  public JsonElement fromKey(String key) {
    return obj.get(key);
  }

  public JsonObject getObj() {
    return obj;
  }
}
