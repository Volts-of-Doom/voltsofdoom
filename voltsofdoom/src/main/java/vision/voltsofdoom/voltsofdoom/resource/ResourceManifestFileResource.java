package vision.voltsofdoom.voltsofdoom.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Codematic representation of the manifest.json file in a texture pack.
 * 
 * @author GenElectrovise
 *
 */
public class ResourceManifestFileResource implements IResource {

  private String texturePackName;
  private String pathToZip;
  /**
   * Internal name -> texture
   */
  private Map<String, String> mappings;

  public ResourceManifestFileResource(String texturePackName, String pathToZip, Map<String, String> mappings) {
    this.texturePackName = texturePackName;
    this.pathToZip = pathToZip;
    this.mappings = mappings;
  }

  @Override
  public InputStream getInputStream() {
    String contents = new Gson().toJson(mappings, Map.class);
    return new ByteArrayInputStream(contents.getBytes());
  }

  public String getTexturePackName() {
    return texturePackName;
  }

  public void setTexturePackName(String texturePackName) {
    this.texturePackName = texturePackName;
  }

  public String getPathToZip() {
    return pathToZip;
  }

  public void setPathToZip(String pathToZip) {
    this.pathToZip = pathToZip;
  }

  public Map<String, String> getMappings() {
    return mappings;
  }

  public void setMappings(Map<String, String> mappings) {
    this.mappings = mappings;
  }

  public static class Serializer implements JsonSerializer<ResourceManifestFileResource>, JsonDeserializer<ResourceManifestFileResource> {

    @Override
    public JsonElement serialize(ResourceManifestFileResource manifest, Type manifestTypeToken, JsonSerializationContext context) {
      JsonObject object = new JsonObject();

      object.addProperty("texturePackName", manifest.texturePackName);
      object.add("mappings", context.serialize(manifest.mappings));

      return object;
    }

    @Override
    public ResourceManifestFileResource deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

      if (!(element instanceof JsonObject)) {
        throw new JsonParseException("TexturePackManifests (from manifest.json in a texture pack) must be JsonObjects.");
      }
      JsonObject object = (JsonObject) element;

      String dTexturePackName = context.deserialize(object.get("texturePackName"), String.class);
      Map<String, String> dMappings = context.deserialize(object.get("mappings"), Map.class);

      ResourceManifestFileResource manifest = new ResourceManifestFileResource(dTexturePackName, "unknown_texture_pack_name", dMappings);

      return manifest;
    }

  }

}
