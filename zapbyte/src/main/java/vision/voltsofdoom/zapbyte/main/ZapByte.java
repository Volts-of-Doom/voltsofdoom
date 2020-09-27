package vision.voltsofdoom.zapbyte.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import vision.voltsofdoom.api.guice.Guicer;
import vision.voltsofdoom.api.guice.ZapByteGuiceBindingModule;
import vision.voltsofdoom.api.guice.Guicer.GuiceTest;
import vision.voltsofdoom.api.zapyte.config.IConfigHandler;
import vision.voltsofdoom.zapbyte.config.ConfigHandler;
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
	private IConfigHandler configHandler;
	private Guicer guicer;
	public static final String ZAPBYTE = "zapbyte";

	/**
	 * Constructs a new root class for a {@link ZapByte} driven application loading
	 * cycle. <br>
	 * <br>
	 * {@link ZapByte} isn't a game engine... It's a loader for <i>something...</i>
	 * Anything really. {@link ZapByte}'s loading cycle is driven by
	 * {@link ZapBit}s, which are essentially souped-up {@link Runnable}s. They are
	 * executed in order of priority, in order to load the application!
	 * 
	 * @param applicationNamespace The system-compliant name for your application.
	 *                             This will be used to store configuration files in
	 *                             the
	 *                             {@link ZapByteReference#getApplicationRoaming()}
	 *                             folder, located on Windows in <code>"{user.home}/App
	 *                             Data/Roaming"</code>, which is normally hidden. The name
	 *                             you put in here <i><b>MUST</b></i> comply with
	 *                             your OS' path system. For example, on windows,
	 *                             you must not use the "/" or "\\" character in
	 *                             your name. {@link ZapByte} cannot enforce this
	 *                             for all possible OSs, so it is partially up to
	 *                             the user to ensure compatibility with expected
	 *                             systems.
	 */
	public ZapByte(String applicationNamespace) {
		ZapByteReference.APPLICATION_NAMESPACE = applicationNamespace;

		setGuicer(new Guicer(new ZapByteGuiceBindingModule()));

		this.zapBits = new HashSet<ZapBit>();
		this.configHandler = new ConfigHandler();

		@SuppressWarnings("unused")
		GuiceTest guiceTest = guicer.getInjector().getInstance(GuiceTest.class);
	}

	/**
	 * Call {@link #addZapBit(ZapBit)} to add {@link ZapBit}s to the list of
	 * {@link #zapBits} used in loading.
	 */
	public abstract void collectZapbits();

	@Override
	public void run() {

		collectZapbits();

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
			bits.get(integer).run();
		}
	}

	public void addZapBit(ZapBit bit) {
		zapBits.add(bit);
	}

	// Get

	public Guicer getGuicer() {
		return guicer;
	}

	public IConfigHandler getConfigHandler() {
		return configHandler;
	}

	public Set<ZapBit> getZapBits() {
		return zapBits;
	}

	// Set

	protected void setGuicer(Guicer guicer) {
		this.guicer = guicer;
	}

	protected void setConfigHandler(IConfigHandler configHandler) {
		this.configHandler = configHandler;
	}

	protected void setLaunched(boolean launched) {
		this.launched = launched;
	}

	protected void setZapBits(Set<ZapBit> zapBits) {
		this.zapBits = zapBits;
	}
}
