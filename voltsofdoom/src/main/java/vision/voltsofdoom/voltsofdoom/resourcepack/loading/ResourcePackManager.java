package vision.voltsofdoom.voltsofdoom.resourcepack.loading;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vision.voltsofdoom.voltsofdoom.VoltsOfDoom;
import vision.voltsofdoom.voltsofdoom.resourcepack.structure.IResourcePack;
import vision.voltsofdoom.zapbyte.resource.IID;

/**
 * A singleton class, the instance of which is stored in {@link VoltsOfDoom}. The class contains a
 * map of the active instance of each registered {@link RegisterableResourceLoader}, and is the
 * central repository of {@link IResourcePack}s.
 * 
 * @author GenElectrovise
 *
 */
public class ResourcePackManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResourcePackManager.class);

  private boolean loaded;
  private Map<IID, RegisterableResourceLoader> loaders = new HashMap<IID, RegisterableResourceLoader>();
  private Map<IID, IResourcePack> packs = new HashMap<IID, IResourcePack>();

  public ResourcePackManager() {
  }

  public void reload(boolean forceReload) {

    // Messages for the developer
    if ((!forceReload) && loaded) {
      LOGGER.warn("An attempt to call the ResourceManager#reload(..) was made when the target ResourceManager is already built. The attempt was denied because the forceReload flag was not set to true.");
      return;
    }
    if (forceReload && loaded) {
      LOGGER.warn("ResourceManager#reload(..) has been called with the forceReload flag when the target ResourceManager is already built. A ResourceManager rebuild will start.");
    }
    if (!loaded) {
      LOGGER.warn("Building the target ResourceManager for the first time.");
    }
    LOGGER.warn("Building ResourceManager with flags: forceReload=" + forceReload);

    packs = fetchActiveResourcePacks();
    loaders = reloadResourceLoaders();
    
    
    
    /*
     * LOGGER.debug("Loading using RegisterableResourceLoader: " + iid.stringify());
     * RegisterableResourceLoader loader = (RegisterableResourceLoader) sup.get();
     * loader.setIdentifier(iid); VoltsOfDoom.getInstance().getResourceLoaders().put(iid, loader);
     * loader.load(true);
     */
  }

  /**
   * Uses all registered {@link ResourcePackFinder}s to find active resource packs.
   */
  private Map<IID, IResourcePack> fetchActiveResourcePacks() {
    LOGGER.error("Non Impl #54");
    return new HashMap<IID, IResourcePack>();
  }

  /**
   * Creates new instances of each loader, then loads them.
   */
  private Map<IID, RegisterableResourceLoader> reloadResourceLoaders() {
    LOGGER.error("Non Impl #62");
    return new HashMap<IID, RegisterableResourceLoader>();
  }

  // Get

  public boolean isLoaded() {
    return loaded;
  }

  public Map<IID, RegisterableResourceLoader> getLoaders() {
    return loaders;
  }

  public Map<IID, IResourcePack> getPacks() {
    return packs;
  }
}
