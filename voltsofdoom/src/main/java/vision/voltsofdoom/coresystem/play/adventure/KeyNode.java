package vision.voltsofdoom.coresystem.play.adventure;

import vision.voltsofdoom.zapbyte.resource.ResourceLocation;

public class KeyNode {
  private String key;
  private ResourceLocation identifier;
  private DataTagMap data;
  private Sheet sheet;

  public String getKey() {
    return key;
  }

  public ResourceLocation getIdentifier() {
    return identifier == null ? sheet.getIdentifier() : identifier;
  }

  public DataTagMap getData() {
    return data;
  }

  public Sheet getSheet() {
    return sheet;
  }
}
