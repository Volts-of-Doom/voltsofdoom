package vision.voltsofdoom.zapbyte.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import vision.voltsofdoom.zapbyte.config.ConfigHandler;
import vision.voltsofdoom.zapbyte.guice.Guicer;
import vision.voltsofdoom.zapbyte.log.Loggers;

/**
 * The main class of the {@link ZapByte} module. Any application wishing to use
 * {@link ZapByte} should extends this class. Its <code>main</code> method
 * should create a new instance of itself, run the {@link #collectZapbits()}
 * method, adding each found {@link ZapBit} to the {@link #zapBits} {@link Set},
 * then should call {@link #run()} to start the application.
 */
public abstract class ZapByte implements Runnable {

	private boolean launched = false;

	private Set<ZapBit> zapBits;
	private ConfigHandler configHandler;
	private Guicer guicer;

	public ZapByte(String applicationNamespace) {
		this.zapBits = new HashSet<ZapBit>();
		this.configHandler = new ConfigHandler();
		
	}

	public Set<ZapBit> getZapBits() {
		return zapBits;
	}

	public void addZapBit(ZapBit bit) {
		zapBits.add(bit);
	}

	/**
	 * Call {@link #addZapBit(ZapBit)} to add {@link ZapBit}s to the list of
	 * {@link #zapBits} used in loading.
	 */
	public abstract void collectZapbits();

	@Override
	public void run() {

		if (launched) {
			throw new IllegalStateException("This instance of <? extends ZapByte> has already been launched!");
		}

		launched = true;
		configHandler.loadIfConfigurationFileBlank();

		Loggers.ZAPBYTE_LOADING.info("Running Java Vitual Machine (JVM) with arguments: " + configHandler.getConfigurationFile().toString());

		// Get all into map
		Map<Integer, ZapBit> bits = new HashMap<Integer, ZapBit>();
		zapBits.forEach((bit) -> bits.put(bit.getPriority(), bit));

		// Sort
		List<Integer> ints = new ArrayList<Integer>(bits.keySet());
		Collections.sort(ints);

		// Run
		for (Integer integer : ints) {
			bits.get(integer).getTask().run();
		}
	}

	public ConfigHandler getConfigHandler() {
		return configHandler;
	}
}
