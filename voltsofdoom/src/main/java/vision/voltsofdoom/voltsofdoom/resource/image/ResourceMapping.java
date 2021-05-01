package vision.voltsofdoom.voltsofdoom.resource.image;

public class ResourceMapping {

  private String resourcePath;
  private String packName;
  private String internalGameMapping;

  public ResourceMapping(String resourcePath, String packName, String internalGameMapping) {
    this.resourcePath = resourcePath;
    this.packName = packName;
    this.internalGameMapping = internalGameMapping;
  }

  public String getResourcePath() {
    return resourcePath;
  }

  public String getPackName() {
    return packName;
  }

  public String getInternalGameMapping() {
    return internalGameMapping;
  }
  
  @Override
  public String toString() {
    return "ResourceMapping[" + "resourcePath=" + resourcePath + " packName=" + packName + " internalGameMapping=" + internalGameMapping + "]";
  }
}
