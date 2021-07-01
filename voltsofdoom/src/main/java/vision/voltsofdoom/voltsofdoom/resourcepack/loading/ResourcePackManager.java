package vision.voltsofdoom.voltsofdoom.resourcepack.loading;

import java.util.List;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import vision.voltsofdoom.voltsofdoom.ConfigNames;
import vision.voltsofdoom.voltsofdoom.VoltsOfDoom;
import vision.voltsofdoom.voltsofdoom.resourcepack.indexing.IIndex;
import vision.voltsofdoom.voltsofdoom.resourcepack.structure.IResourcePack;
import vision.voltsofdoom.zapbyte.registry.IRegistryEntry;
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
  private List<RegisterableResourceLoader> loaders = Lists.newArrayList();
  private List<IResourcePack> packs = Lists.newArrayList();
  private IIndex index = IIndex.BLANK;

  public ResourcePackManager() {}

  public void reload(boolean forceReload) {

    // Messages for the developer
    if ((!forceReload) && isLoaded()) {
      LOGGER.warn("An attempt to call the ResourcePackManager#reload(..) was made when the target ResourcePackManager is already built. The attempt was denied because the forceReload flag was not set to true.");
      return;
    }
    if (forceReload && isLoaded()) {
      LOGGER.warn("ResourcePackManager#reload(..) has been called with the forceReload flag when the target ResourcePackManager is already built. A ResourcePackManager rebuild will start.");
    }
    LOGGER.warn("Building ResourcePackManager with flags: forceReload=" + forceReload + " alreadyLoaded=" + isLoaded());

    packs = fetchActiveResourcePacks();
    loaders = reloadResourceLoaders();
    index = generateIndex();
    setLoaded(true);
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

  /**
   * @return
   */
  private IIndex<String, IID> generateIndex() {

    // Combine and override textures
    // a) Get priorities
    JsonArray texturePackPriorityJsonArray = VoltsOfDoom.getInstance().getConfigurationHandler().getOption(ConfigNames.RESOURCE_PACK_PRIORITY.getNameInConfigFile()).getAsJsonArray();
    List<IResourcePack> packs = VoltsOfDoom.getInstance().getResourcePackManager().getPacks();

    // Reorder packs and place here
    List<IResourcePack> prioritisedPacks = Lists.newArrayList();
    prioritisedPacks = packs.subList(0, packs.size() - 1);

    // Maintain a list of indices
    List<IIndex<String, IID>> indices = Lists.newArrayList();

    // Use each registered indexer.
    for (Supplier<? extends IRegistryEntry<?>> sup : VoltsOfDoom.getInstance().getRegistry().getMapOfType(ResourcePackIndexer.class).values()) {
      indices.add(((ResourcePackIndexer) sup.get()).index(prioritisedPacks));
    }

    // Combine indices
    IIndex<String, IID> index = IIndex.create();
    indices.forEach((in) -> {
      // Put all from "in" into "index"
      LOGGER.error("230621 #122 no impl");
    });

    return index;
  }

  // Get

  protected void setLoaded(boolean loaded) {
    this.loaded = loaded;
  }

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
