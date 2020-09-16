package vision.voltsofdoom.coresystem.universal.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.LogManager;

import com.google.gson.JsonArray;

import vision.voltsofdoom.coresystem.universal.resource.VODJsonReader;
import vision.voltsofdoom.coresystem.universal.util.Reference;
import vision.voltsofdoom.coresystem.universal.util.StringUtils;
import vision.voltsofdoom.zapbyte.log.Loggers;
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

	public static final String ID = "coresystem";

	public static volatile boolean launched = false;

	public String[] args = null;

	private static String[] defaultArgs = {};

	/**
	 * Begins the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		VoltsOfDoomCoreSystem vodcs = new VoltsOfDoomCoreSystem("voltsofdoom");

		vodcs.setArgs(args);

		vodcs.run();
	}

	@Override
	public void collectZapbits() {

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

	/**
	 * Sets this object's arguments to the arguments given to the VM at launch so
	 * they may be queried later.
	 * 
	 * @param args The {@link String}[] of arguments in.
	 * @return This {@link VoltsOfDoomCoreSystem} object.
	 */
	private VoltsOfDoomCoreSystem setArgs(String[] args) {
		this.args = args;
		return this;
	}

	/**
	 * @return The current program arguments.
	 */
	public String[] getArgs() {
		return args;
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

	/**
	 * Reads the VM configuration in voltsofdoom/config.json
	 * 
	 * @return A {@link String}[] of the configuration options.
	 */
	public static String[] getVMArgs() {

		File configFile = new File(Reference.CONFIG + "vmconfig.json");

		if (!configFile.exists()) {
			Loggers.ZAPBYTE.info("Configuration file does not exist at: " + configFile);
			try {
				Loggers.ZAPBYTE.info("Trying to write a new configuration file...");
				BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
				writer.write("{\"vmargs\":[\"argument\"]}");
				writer.close();
				Loggers.ZAPBYTE.info("Written!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Loggers.ZAPBYTE.info("Reading configuration file: " + configFile);

		VODJsonReader reader = new VODJsonReader(configFile);
		JsonArray array = reader.fromKey("vmargs").getAsJsonArray();
		String[] args = new String[array.size()];

		for (int i = 0; i < array.size(); i++) {
			args[i] = array.get(i).getAsString();
		}

		Loggers.ZAPBYTE.info("Read configuration: " + StringUtils.arrayToString(args));

		return args;
	}
}
