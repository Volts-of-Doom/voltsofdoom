package vision.voltsofdoom.coresystem.universal.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  
  public static VoltsOfDoomCoreSystem instance;

  public VoltsOfDoomCoreSystem() {
    super(ID);
  }

  public static final GameController GAME_CONTROLLER = new GameController();

  public static final String ID = "voltsofdoom";

  public static volatile boolean launched = false;

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

  @Override
  public void run() {
    getApplicationLogger().debug("Running Volts of Doom Core System");
    super.run();
  }

  @Override
  public void collectZapbits() {
    getApplicationLogger().debug("Collecting ZapBits for Volts of Doom Core System");
    
    addZapBit(
        new ZapBit(0, () -> VoltsOfDoomCoreSystem.instance.getApplicationLogger().info("Starting Volts of Doom!")));
    addZapBit(DefaultZapBits.CREATE_LOADING_WINDOW_10);
    addZapBit(DefaultZapBits.CREATE_REFLECTORIES_20);
    addZapBit(DefaultZapBits.SCAN_FOR_MODS_30);
    addZapBit(DefaultZapBits.CREATE_BANDWAGON_40);

    addZapBit(DefaultZapBits.CREATE_REGISTRY_CREATE_REGISTRY_TYPES_50);
    addZapBit(DefaultZapBits.CREATE_REGISTRY_CREATE_AND_SUBMIT_TYPE_REGISTRIES_54);
    addZapBit(DefaultZapBits.CREATE_REGISTRY_POPULATE_TYPE_REGISTRIES_58);
    addZapBit(VODZapBits.CREATE_REGISTRY_GENERATE_ADVENTURES_62);
    addZapBit(DefaultZapBits.CREATE_REGISTRY_POLL_REGISTRY_TYPES_68);

    addZapBit(DefaultZapBits.CLOSE_LOADING_WINDOW_70);
  }

  @Override
  public void continueExecution() {
    
    VoltsOfDoomCoreSystem.instance.getApplicationLogger().debug("Volts of Doom Core System continuing execution");

    /*
     * //Test level creation Registry.getTyped(RegistryTypes.ADVENTURES).getEntries().forEach((k, a)
     * -> ((Adventure) a.get()) .getLevels().forEach((l) ->
     * l.getRawTileMap().generateTwoDimensionalListOfTileObjects()));
     */
  }

  @Override
  public Logger getApplicationLogger() {
    return LoggerFactory.getLogger(VoltsOfDoomCoreSystem.class);
  }
}
