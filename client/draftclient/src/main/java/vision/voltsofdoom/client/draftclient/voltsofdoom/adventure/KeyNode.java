package vision.voltsofdoom.client.draftclient.voltsofdoom.adventure;

import vision.voltsofdoom.zapbyte.resource.ID;

public class KeyNode {
  private String key;
  private ID identifier;
  private DataTagMap data;
  private Sheet sheet;

  public String getKey() {
    return key;
  }

  public ID getIdentifier() {
    return identifier == null ? sheet.getIdentifier() : identifier;
  }

  public DataTagMap getData() {
    return data;
  }

  public Sheet getSheet() {
    return sheet;
  }
}
