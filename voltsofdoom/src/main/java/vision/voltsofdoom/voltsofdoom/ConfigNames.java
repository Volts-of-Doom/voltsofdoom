package vision.voltsofdoom.voltsofdoom;

public enum ConfigNames {
  RESOURCE_PACK_PRIORITY("resources.resource_pack_priority");

  private String nameInConfigFile;

  ConfigNames(String nameInConfigFile) {
    this.nameInConfigFile = nameInConfigFile;
  }

  public String getNameInConfigFile() {
    return nameInConfigFile;
  }
}
