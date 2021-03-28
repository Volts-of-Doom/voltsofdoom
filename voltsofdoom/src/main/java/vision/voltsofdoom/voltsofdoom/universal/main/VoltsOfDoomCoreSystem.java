package vision.voltsofdoom.voltsofdoom.universal.main;

import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vision.voltsofdoom.silverspark.core.Game;
import vision.voltsofdoom.voltsofdoom.universal.resource.image.TextureManager;
import vision.voltsofdoom.zapbyte.main.DefaultZapBits;
import vision.voltsofdoom.zapbyte.main.ZapBit;
import vision.voltsofdoom.zapbyte.main.ZapByte;

/**
 * The main class for Volts of Doom's Core System. The game starts running here. In the case that
 * you need it, the game should be run from {@link #main(String[])}. The arguments can be passed
 * either from the runner (e.g. command line arguments), or from the voltsofdoom/config.json file,
 * which can be easily read using {@link #getVMArgs()}.
 * 
 * @author GenElectrovise
 *
 */
public class VoltsOfDoomCoreSystem extends ZapByte {

  private static VoltsOfDoomCoreSystem instance;

  private static final String ID = "voltsofdoom";

  @Nullable
  private TextureManager textureManager;

  @Nullable
  private Game game;

  public VoltsOfDoomCoreSystem() {
    super(ID);
    instance = this;
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
    instance = new VoltsOfDoomCoreSystem();

    instance.run();
  }

  public static void easyDebug(String message) {
    getInstance().getApplicationLogger().debug(message);
  }

  public static void easyInfo(String message) {
    getInstance().getApplicationLogger().info(message);
  }

  @Override
  public void run() {
    getApplicationLogger().debug("Running Volts of Doom Core System");
    super.run();
  }

  @Override
  public void collectZapbits() {
    getApplicationLogger().debug("Collecting ZapBits for Volts of Doom Core System");

    addZapBit(new ZapBit(0, () -> VoltsOfDoomCoreSystem.instance.getApplicationLogger().info("Starting Volts of Doom!")));
    addZapBit(DefaultZapBits.CREATE_LOADING_WINDOW_10);
    addZapBit(VODZapBits.CREATE_TEXTURE_MANAGER_11);
    addZapBit(VODZapBits.ADD_VOLTS_OF_DOOM_TO_ADDITIONAL_REFLECTORY_CLASSES_19);
    addZapBit(DefaultZapBits.CREATE_REFLECTORIES_20);
    addZapBit(DefaultZapBits.SCAN_FOR_MODS_30);
    addZapBit(DefaultZapBits.CREATE_BANDWAGON_40);

    addZapBit(DefaultZapBits.CREATE_REGISTRY_CREATE_REGISTRY_TYPES_50);
    addZapBit(DefaultZapBits.CREATE_REGISTRY_CREATE_AND_SUBMIT_TYPE_REGISTRIES_54);
    addZapBit(DefaultZapBits.CREATE_REGISTRY_POPULATE_TYPE_REGISTRIES_58);
    addZapBit(VODZapBits.CREATE_REGISTRY_GENERATE_ADVENTURES_62);
    addZapBit(DefaultZapBits.CREATE_REGISTRY_POLL_REGISTRY_TYPES_68);

    addZapBit(DefaultZapBits.CLOSE_LOADING_WINDOW_70);
    addZapBit(VODZapBits.CREATE_GAME_100);
  }

  @Override
  public void continueExecution() {
    VoltsOfDoomCoreSystem.instance.getApplicationLogger().debug("Volts of Doom Core System continuing execution...");
  }

  public static VoltsOfDoomCoreSystem getInstance() {
    return instance != null ? instance : new VoltsOfDoomCoreSystem();
  }

  public TextureManager getTextureManager() {
    return textureManager;
  }

  public void setTextureManager(TextureManager textureManager) {
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
    return LoggerFactory.getLogger(VoltsOfDoomCoreSystem.class);
  }
}
