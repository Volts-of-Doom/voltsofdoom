package vision.voltsofdoom.zapbyte.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import vision.voltsofdoom.api.zapyte.config.IConfigurationOptionProvider;

public class StreamedConfigurationFile implements IConfigurationOptionProvider {

  private InputStream stream;
  private Gson gson;

  public StreamedConfigurationFile(File file) throws FileNotFoundException {
    this(new FileInputStream(file));
  }

  public StreamedConfigurationFile(InputStream stream) {
    this.stream = stream;
  }

  public void setGson(Gson gson) {
    this.gson = gson;
  }

  @Override
  public JsonObject getOptions() {
    // If Gson is null, use new Gson, else use given gson instance.
    // Gson.fromJson(stream, JsonObject)
    return (gson == null ? new Gson() : gson).fromJson(new InputStreamReader(stream), JsonObject.class);
  }

  @Override
  public InputStream getInputStream() {
    return stream;
  }
}
