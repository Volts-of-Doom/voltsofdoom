package vision.voltsofdoom.voltsofdoom.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonObjectResource implements IResource {

  private JsonObject object;

  public JsonObjectResource(JsonObject json) {
    this.object = json;
  }

  public JsonObjectResource(String json) {
    this.object = new Gson().fromJson(json, JsonObject.class);
  }

  @Override
  public InputStream getInputStream() {
    return new ByteArrayInputStream(object.toString().getBytes());
  }

}
