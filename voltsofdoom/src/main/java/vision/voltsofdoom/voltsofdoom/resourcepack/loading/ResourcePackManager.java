package vision.voltsofdoom.voltsofdoom.resourcepack.loading;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Lists;
import vision.voltsofdoom.voltsofdoom.VoltsOfDoom;
import vision.voltsofdoom.voltsofdoom.resourcepack.structure.IResourcePack;

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
  private List<RegisterableResourceLoader> loaders = Lists.newArrayList();
  private List<IResourcePack> packs = Lists.newArrayList();

  public ResourcePackManager() {}

  public void reload(boolean forceReload) {

    // Messages for the developer
    if ((!forceReload) && loaded) {
      LOGGER.warn("An attempt to call the ResourcePackManager#reload(..) was made when the target ResourcePackManager is already built. The attempt was denied because the forceReload flag was not set to true.");
      return;
    }
    if (forceReload && loaded) {
      LOGGER.warn("ResourcePackManager#reload(..) has been called with the forceReload flag when the target ResourcePackManager is already built. A ResourcePackManager rebuild will start.");
    }
    LOGGER.warn("Building ResourcePackManager with flags: forceReload=" + forceReload + " alreadyLoaded=" + loaded);

    packs = fetchActiveResourcePacks();
    loaders = reloadResourceLoaders();
    loaded = true;


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
  private List<IResourcePack> fetchActiveResourcePacks() {

    List<IResourcePack> packs = Lists.newArrayList();

    VoltsOfDoom.getInstance().getRegistry().getMapOfType(ResourcePackFinder.class).forEach((iid, sup) -> {

      ResourcePackFinder finder = (ResourcePackFinder) sup.get();
      finder.find().forEach((rp) -> {
        packs.add(rp);
      });
    });

    LOGGER.debug("Found active resource packs " + packs);

    return packs;
  }

  /**
   * Creates new instances of each loader, then loads them.
   */
  private List<RegisterableResourceLoader> reloadResourceLoaders() {

    List<RegisterableResourceLoader> loaders = Lists.newArrayList();

    // Create new instances of each loader type
    VoltsOfDoom.getInstance().getRegistry().getMapOfType(RegisterableResourceLoader.class).forEach((iid, sup) -> {
      loaders.add((RegisterableResourceLoader) sup.get());
    });

    // Load all resource loaders
    loaders.forEach((rrl) -> {
      LOGGER.debug("Reloading " + rrl.getClass().getSimpleName());
      rrl.load(true);
    });

    LOGGER.debug("Reloaded resource loaders");

    return loaders;
  }

  // Get

  public boolean isLoaded() {
    return loaded;
  }

  public List<RegisterableResourceLoader> getLoaders() {
    return loaders;
  }

  public List<IResourcePack> getPacks() {
    return packs;
  }
}
