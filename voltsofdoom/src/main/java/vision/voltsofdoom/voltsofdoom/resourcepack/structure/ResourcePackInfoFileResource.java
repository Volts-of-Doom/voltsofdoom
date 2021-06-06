package vision.voltsofdoom.voltsofdoom.resourcepack.structure;

import javax.annotation.Nullable;
import com.google.gson.JsonObject;
import vision.voltsofdoom.voltsofdoom.resourcepack.JsonObjectResource;

public class ResourcePackInfoFileResource extends JsonObjectResource {

  public static final String PACK_INFO_JSON = "pack.json";

  private String fileName;
  private String modid;
  private String packDisplayName;
  private int packFormat;
  private String packInternalName;

  /**
   * 
   * @param json The source JSON for the file, used for constructing as a {@link JsonObjectResource}.
   * @param fileName The file name of the resource pack, for example "MyPack.zip". If this came from a
   *        mod, use null.
   * @param modid The modid of the mod which this resource pack is located in, for example "mymod". If
   *        this is not from a mod, use null.
   * @param packDisplayName The displayed name of the resource pack, for example "My Resource Pack".
   * @param packFormat A numeric value to tell the game how to load the resource pack in case of
   *        format changes.
   * @param packInternalName The internal name which the game should address your pack by, like a
   *        namespace, for example "my_resource_pack".
   */
  public ResourcePackInfoFileResource(JsonObject json, @Nullable String fileName, @Nullable String modid, String packDisplayName, int packFormat, String packInternalName) {
    super(json);
    this.fileName = fileName;
    this.modid = modid;
    this.packDisplayName = packDisplayName;
    this.packFormat = packFormat;
    this.packInternalName = packInternalName;
  }

  public String getFileName() {
    return fileName;
  }

  public String getModid() {
    return modid;
  }

  public String getPackDisplayName() {
    return packDisplayName;
  }

  public int getPackFormat() {
    return packFormat;
  }

  public String getPackInternalName() {
    return packInternalName;
  }

  @Override
  public String toString() {
    return "ResourcePackInfoFileResource[" + "modid=" + modid + " packInternalName=" + packInternalName + "]";
  }

}
