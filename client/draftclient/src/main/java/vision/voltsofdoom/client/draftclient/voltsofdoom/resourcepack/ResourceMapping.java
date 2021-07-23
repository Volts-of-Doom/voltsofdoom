package vision.voltsofdoom.client.draftclient.voltsofdoom.resourcepack;

import vision.voltsofdoom.zapbyte.resource.ID;

public class ResourceMapping {

  private String resourcePath;
  private ID id;

  public ResourceMapping(String resourcePath, String packName, String internalGameMapping) {
    this(resourcePath, new ID(packName, internalGameMapping));
  }

  public ResourceMapping(String resourcePath, ID id) {
    this.resourcePath = resourcePath;
    this.id = id;
  }

  public String getResourcePath() {
    return resourcePath;
  }

  public ID getId() {
    return id;
  }

  @Override
  public String toString() {
    return "ResourceMapping[" + "resourcePath=" + resourcePath + " id=" + id.stringify() + "]";
  }
}
