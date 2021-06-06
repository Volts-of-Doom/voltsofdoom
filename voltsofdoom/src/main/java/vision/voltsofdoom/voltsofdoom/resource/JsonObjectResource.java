package vision.voltsofdoom.voltsofdoom.resource;

import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
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
  public ByteBuffer getBytes() {
    byte[] bytes = object.toString().getBytes();
    return BufferUtils.createByteBuffer(bytes.length).put(bytes);
  }

}
