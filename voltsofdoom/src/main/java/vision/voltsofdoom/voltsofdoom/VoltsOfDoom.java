package vision.voltsofdoom.voltsofdoom;

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
import vision.voltsofdoom.voltsofdoom.util.Reference;
import vision.voltsofdoom.zapbyte.DefaultZapBits;
import vision.voltsofdoom.zapbyte.ZapBit;
import vision.voltsofdoom.zapbyte.ZapByte;
import vision.voltsofdoom.zapbyte.ZapByteReference;
import vision.voltsofdoom.zapbyte.registry.IRegistryMessenger2;
import vision.voltsofdoom.zapbyte.registry.RegistryMessenger2;
import vision.voltsofdoom.zapbyte.resource.ID;

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
  private TextureResourceLoader textureManager;

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
    addZapBit(DefaultZapBits.FREEZE_REGISTRY_65);

    addZapBit(VODZapBits.GENERATE_ADVENTURES_62);

    addZapBit(DefaultZapBits.CLOSE_LOADING_WINDOW_70);
  }

  @Override
  public void continueExecution() {
    LOGGER.info("Volts of Doom continuing execution...");

    // Create texture manager
    TextureResourceLoader manager = new TextureResourceLoader(Reference.getTexturesDir());
    VoltsOfDoom.getInstance().setTextureManager(manager);
    manager.load(true);

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

  public static VoltsOfDoom getInstance() {
    return instance != null ? (VoltsOfDoom) instance : new VoltsOfDoom();
  }

  public TextureResourceLoader getTextureManager() {
    return textureManager;
  }

  public void setTextureManager(TextureResourceLoader textureManager) {
    this.textureManager = textureManager;
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
   * A mod 'owned' by the Volts of Doom game itself which is used for adding to game registries etc
   * like a normal mod would.
   * 
   * @author GenElectrovise
   *
   */
  @vision.voltsofdoom.zapbyte.mod.Mod(modid = VoltsOfDoom.CoreMod.MODID)
  public static class CoreMod {
    public static final String MODID = "voltsofdoom";
    private static final Logger LOGGER = LoggerFactory.getLogger(VoltsOfDoom.CoreMod.class);

    public CoreMod() {
      LOGGER.info("Constructing " + this.getClass().getTypeName());
    }

    public static final IRegistryMessenger2<RegisterableResourceLoader> TEXTURES =
        VoltsOfDoom.getInstance().getRegistry().register(new ID(MODID, "textures"), () -> new TextureResourceLoader(ZapByteReference.getResources()), RegisterableResourceLoader.class);
  }
}
