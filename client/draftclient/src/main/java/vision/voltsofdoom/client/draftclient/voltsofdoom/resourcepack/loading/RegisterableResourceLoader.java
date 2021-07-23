package vision.voltsofdoom.client.draftclient.voltsofdoom.resourcepack.loading;

import vision.voltsofdoom.zapbyte.registry.RegistryEntry;

/**
 * A class which loads a specific type of resource, for example textures.
 * 
 * @author GenElectrovise
 *
 */
public abstract class RegisterableResourceLoader extends RegistryEntry<RegisterableResourceLoader> {

  protected String rootDirectory;
  protected boolean loaded;

  public RegisterableResourceLoader(String rootDirectory, boolean loaded) {
    this.setRootDirectory(rootDirectory);
    this.setLoaded(loaded);
  }

  public abstract void load(boolean forceReload);

  public boolean isLoaded() {
    return loaded;
  }

  public void setLoaded(boolean loaded) {
    this.loaded = loaded;
  }

  public String getRootDirectory() {
    return rootDirectory;
  }

  public void setRootDirectory(String rootDirectory) {
    this.rootDirectory = rootDirectory;
  }
  
}
