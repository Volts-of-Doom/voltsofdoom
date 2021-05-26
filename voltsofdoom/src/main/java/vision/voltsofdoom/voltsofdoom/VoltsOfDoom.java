package vision.voltsofdoom.voltsofdoom;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import vision.voltsofdoom.silverspark.Silverspark;
import vision.voltsofdoom.silverspark.core.Game;
import vision.voltsofdoom.silverspark.guice.GuiceModule;
import vision.voltsofdoom.voltsofdoom.resource.RegisterableResourceLoader;
import vision.voltsofdoom.voltsofdoom.resource.TextureResourceLoader;
import vision.voltsofdoom.zapbyte.DefaultZapBits;
import vision.voltsofdoom.zapbyte.ZapBit;
import vision.voltsofdoom.zapbyte.ZapByte;
import vision.voltsofdoom.zapbyte.registry.IRegistryEntry;
import vision.voltsofdoom.zapbyte.resource.IID;

/**
 * The main class for Volts of Doom's Core System. The game starts running here. In the case that
 * you need it, the game should be run from {@link #main(String[])}. The arguments can be passed
 * either from the runner (e.g. command line arguments), or from the voltsofdoom/config.json file,
 * which can be easily read using {@link #getVMArgs()}.
 * 
 * @author GenElectrovise
 *
 */
public class VoltsOfDoom extends ZapByte<VoltsOfDoom> {

  private static final Logger LOGGER = LoggerFactory.getLogger(VoltsOfDoom.class);

  private static final String ID = "voltsofdoom";

  @Nullable
  private Map<IID, RegisterableResourceLoader> resourceLoaders = new HashMap<IID, RegisterableResourceLoader>();;

  @Nullable
  private Game game;

  @Inject
  private Silverspark silverspark;

  public VoltsOfDoom() {
    super(ID);
  }

  /**
   * Begins the program.
   * 
   * @param args
   */
  public static void main(String[] args) {
    mainStepIn();
  }

  public static void mainStepIn() {
    Injector injector = Guice.createInjector(new GuiceModule());
    instance = injector.getInstance(VoltsOfDoom.class);
    instance.run();
  }

  @Override
  public void run() {
    LOGGER.debug("Running Volts of Doom Core System");
    super.run();
  }

  @Override
  public void collectZapbits() {
    LOGGER.debug("Collecting ZapBits for Volts of Doom Core System");

    addZapBit(new ZapBit(0, () -> LOGGER.info("Starting Volts of Doom!")));
    addZapBit(DefaultZapBits.CREATE_LOADING_WINDOW_10);
    addZapBit(VODZapBits.ADD_VOLTS_OF_DOOM_TO_ADDITIONAL_REFLECTORY_CLASSES_19);
    addZapBit(DefaultZapBits.CREATE_REFLECTORIES_20);
    addZapBit(DefaultZapBits.SCAN_FOR_MODS_30);
    addZapBit(DefaultZapBits.CREATE_BANDWAGON_40);
    addZapBit(DefaultZapBits.MAKE_MOD_INSTANCES_50);
    addZapBit(DefaultZapBits.REGISTRY_DUMP_55);

    addZapBit(VODZapBits.GENERATE_ADVENTURES_62);
    addZapBit(VODZapBits.INITIALISE_RESOURCE_LOADERS_75);

    addZapBit(DefaultZapBits.FREEZE_REGISTRY_90);
    addZapBit(DefaultZapBits.CLOSE_LOADING_WINDOW_100);
  }

  @Override
  public void continueExecution() {
    LOGGER.info("Volts of Doom continuing execution...");

    // Create texture manager
    // TextureResourceLoader manager = new TextureResourceLoader(Reference.getTexturesDir());
    // VoltsOfDoom.getInstance().setTextureManager(manager);
    // manager.load(true);

    /*
     * resourceLoaders = new HashMap<IID, RegisterableResourceLoader>(); initialiseResourceLoaders();
     */

    // Create game
    // Silverspark spark = new Silverspark();
    // TODO - in Silverspark, SS initialisation is now by Guice. Either VOD needs to use Guice, or else
    // SS needs to be set up by hand.
    // start up renderer - game loop excluded for now

    Silverspark spark = VoltsOfDoom.getInstance().getSilverspark();
    spark.start();
    // TODO Game loop has been excluded for now - just working on renderer
    // Game game = new VariableTimestepGame("Volts of Doom");
    // VoltsOfDoomCoreSystem.getInstance().setGame(game);
    // game.start();
  }

  /*
   * private void initialiseResourceLoaders() { Map<IID, Supplier<? extends IRegistryEntry<?>>>
   * registry = getRegistry().getMapOfType(RegisterableResourceLoader.class);
   * 
   * registry.forEach((iid, sup) -> { LOGGER.debug("Loading using RegisterableResourceLoader: " +
   * iid.stringify()); RegisterableResourceLoader loader = (RegisterableResourceLoader) sup.get();
   * loader.setIdentifier(iid); resourceLoaders.put(iid, loader); loader.load(true); }); }
   */

  public static VoltsOfDoom getInstance() {
    return instance != null ? (VoltsOfDoom) instance : new VoltsOfDoom();
  }

  public static String getId() {
    return ID;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  @Override
  public Logger getApplicationLogger() {
    return LoggerFactory.getLogger(VoltsOfDoom.class);
  }

  public void setSilverspark(Silverspark silverspark) {
    this.silverspark = silverspark;
  }

  public Silverspark getSilverspark() {
    return silverspark;
  }

  /**
   * The registry contains passive (supplier) versions. This map is a collection of the active
   * instances.
   */
  public Map<IID, RegisterableResourceLoader> getResourceLoaders() {
    return resourceLoaders;
  }
}
