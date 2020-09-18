package vision.voltsofdoom.coresystem.universal.main;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.LogManager;

import vision.voltsofdoom.zapbyte.main.ZapBit;
import vision.voltsofdoom.zapbyte.main.ZapByte;

/**
 * The main class for Volts of Doom's Core System. The game starts running here.
 * In the case that you need it, the game should be run from
 * {@link #main(String[])}. The arguments can be passed either from the runner
 * (e.g. command line arguments), or from the voltsofdoom/config.json file,
 * which can be easily read using {@link #getVMArgs()}.
 * 
 * @author GenElectrovise
 *
 */
public class VoltsOfDoomCoreSystem extends ZapByte {

	public VoltsOfDoomCoreSystem(String applicationNamespace) {
		super(applicationNamespace);
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
		VoltsOfDoomCoreSystem vodcs = new VoltsOfDoomCoreSystem(ID);

		vodcs.run();
	}

	@Override
	public void run() {
		super.run();

		try {
			GAME_CONTROLLER.initialiseAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void collectZapbits() {
		addZapBit(new ZapBit(0, () -> {
			
		}));
	}

	@SuppressWarnings("unused")
	private VoltsOfDoomCoreSystem applyLoggerProperties() {

		String configFileLocation = "config/logger/logging.properties";

		Properties properties = new Properties();
		try {
			InputStream configFile = VoltsOfDoomCoreSystem.class.getClassLoader().getResourceAsStream(configFileLocation);
			properties.load(configFile);
			System.out.println("Internal logger properties file '" + configFileLocation + "' \n >> " + properties);
			LogManager.getLogManager().readConfiguration(configFile);
		} catch (Exception e) {
			System.err.println("Warning! Logging failed to configure!");
			e.printStackTrace();
			throw new IllegalStateException("Logging failed to configure");
		}

		return this;
	}
}
