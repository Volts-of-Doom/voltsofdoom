package vision.voltsofdoom.zapbyte.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import vision.voltsofdoom.zapbyte.main.ZapByteReference;

public class ConfigurationFileSerializer {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationFileSerializer.class);

  private List<File> files;
  private Gson gson;

  public ConfigurationFileSerializer(Gson gson, File rootDirectory) {

    this.gson = gson;

    if (!rootDirectory.isDirectory()) {
      LOGGER.error("The file " + rootDirectory.toString() + " is not a directory. Defaulting to zapbyte/app/config");
      rootDirectory = new File(ZapByteReference.getConfig());
    }

    File[] proposedFiles = rootDirectory.listFiles((parent, filename) -> filename.endsWith(".json"));
    files = Lists.newArrayList(proposedFiles);
  }

  public JsonObject objectifyFiles() {

    JsonObject object = new JsonObject();

    for (File file : files) {

      if (file.isDirectory())
        continue;
      FileReader reader = null;

      try {
        reader = new FileReader(file);
      } catch (JsonSyntaxException js) {
        js.printStackTrace();
      } catch (JsonIOException io) {
        io.printStackTrace();
      } catch (FileNotFoundException fi) {
        fi.printStackTrace();
      }

      JsonElement element = gson.fromJson(reader, JsonElement.class);
      String name = file.getName().split(".")[0];
      object.add(name, element);
    }


    return object;
  }
}
