package vision.voltsofdoom.zapbyte.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vision.voltsofdoom.api.guice.Guicer;
import vision.voltsofdoom.api.guice.Guicer.GuiceTest;
import vision.voltsofdoom.api.zapyte.config.IConfigHandler;
import vision.voltsofdoom.zapbyte.config.ConfigHandler;
import vision.voltsofdoom.zapbyte.reflectory.Reflectory;
import vision.voltsofdoom.zapbyte.resource.ISystemResourceHandler;
import vision.voltsofdoom.zapbyte.resource.ZBSystemResourceHandler;

/**
 * The main class of the {@link ZapByte} module. Any application wishing to use {@link ZapByte}
 * should extends this class. Its <code>main</code> method should create a new instance of itself
 * and call {@link #run()} to start the application.
 */
public abstract class ZapByte {

  private boolean launched = false;

  private Set<ZapBit> zapBits;
  private IConfigHandler configHandler;
  private Guicer guicer;
  private static final String ZAPBYTE = "zapbyte";
  private ISystemResourceHandler systemResourceHandler;
  private Reflectory reflectory;

  public static Logger LOGGER;

  /**
   * Constructs a new root class for a {@link ZapByte} driven application loading cycle. <br>
   * <br>
   * {@link ZapByte} isn't a game engine... It's a loader for <i>something...</i> Anything really.
   * {@link ZapByte}'s loading cycle is driven by {@link ZapBit}s, which are essentially souped-up
   * {@link Runnable}s. They are executed in order of priority, in order to load the application!
   * 
   * @param applicationNamespace The system-compliant name for your application. This will be used to
   *        store configuration files in the {@link ZapByteReference#getApplicationRoaming()} folder,
   *        located on Windows in <code>"{user.home}/App
   *                             Data/Roaming"</code>, which is normally hidden. The name you put in
   *        here <i><b>MUST</b></i> comply with your OS' path system. For example, on windows, you
   *        must not use the "/" or "\\" character in your name. {@link ZapByte} cannot enforce this
   *        for all possible OSs, so it is partially up to the user to ensure compatibility with
   *        expected systems.
   */
  @SuppressWarnings("unused")
  public ZapByte(String applicationNamespace) {
    ZapByteReference.APPLICATION_NAMESPACE = applicationNamespace;

    // You can't use logging calls until after this
    configureLogger();
    LOGGER.info("Configured logging");

    // Configure the uncaught exception handler
    Thread.setDefaultUncaughtExceptionHandler(new ZapByteUncaughtExceptionHandler());
    LOGGER.info("Configured ZapByteUncaughtExceptionHandler");

    // Set up dependency injection
    setGuicer(new Guicer(new ZapByteGuiceBindingModule()));
    // Test the Guicer
    GuiceTest guiceTest = guicer.getInjector().getInstance(GuiceTest.class);
    LOGGER.info("Configured Guicer");

    // Make an empty set for zap bits
    setZapBits(new HashSet<ZapBit>());
    LOGGER.info("Configured ZapBit HashSet");

    // Set up configuration handler
    setConfigHandler(new ConfigHandler());
    LOGGER.info("Configured ConfigHandler");

    // Set the system resource handler
    setSystemResourceHandler(guicer.getInjector().getInstance(ZBSystemResourceHandler.class));
    LOGGER.info("Configured ISystemResourceHandler");
  }

  /**
   * Logging calls WILL NOT work before this method is called, hence it is called before anything
   * else.
   */
  private void configureLogger() {

    // Set location of config file
    System.setProperty("logback.configurationFile", ZapByteReference.getConfig() + "logback.xml");

    // Set location of output file
    System.setProperty("vision.voltsofdoom.zapbyte.log.outputFile", ZapByteReference.getLogs() + Calendar.getInstance().getTime().toString().replace(" ", "_").replace(":", "-") + ".log");

    LOGGER = LoggerFactory.getLogger(ZapByte.class);
  }

  /**
   * Call {@link #addZapBit(ZapBit)} to add {@link ZapBit}s to the list of {@link #zapBits} used in
   * loading.
   */
  public abstract void collectZapbits();

  public abstract void continueExecution();

  public void run() {
    LOGGER.info("Running ZapByte loading cycle!");

    collectZapbits();
    LOGGER.debug("Collected " + zapBits.size() + " ZapBits");

    if (launched) {
      throw new IllegalStateException("This instance of <? extends ZapByte> has already been launched!");
    }

    launched = true;
    LOGGER.debug("Launching... (launched=true)");

    configHandler.loadIfConfigurationFileBlank();
    LOGGER.debug("Loaded ZapByte IConfigHandler configHandler");

    // Get all into map
    Map<Integer, ZapBit> bits = new HashMap<Integer, ZapBit>();
    zapBits.forEach((bit) -> bits.put(bit.getPriority(), bit));

    // Sort
    List<Integer> ints = new ArrayList<Integer>(bits.keySet());
    Collections.sort(ints);

    // Run
    for (Integer integer : ints) {
      ZapBit bit = bits.get(integer);
      LOGGER.debug("Running ZapBit: name=" + bit.getName() + " priority=" + integer);
      bit.run();
    }

    LOGGER.warn("ZapBit execution complete. Continuing external (none-ZapBit) execution.");
    continueExecution();

    LOGGER.error("ZapByte cycle complete. Exiting.");
    System.exit(0);
  }

  public void addZapBit(ZapBit bit) {
    zapBits.add(bit);
  }

  // Get

  public static String getZapbyte() {
    return ZAPBYTE;
  }

  public Guicer getGuicer() {
    return guicer;
  }

  public IConfigHandler getConfigHandler() {
    return configHandler;
  }

  public Set<ZapBit> getZapBits() {
    return zapBits;
  }

  public ISystemResourceHandler getSystemResourceHandler() {
    return systemResourceHandler;
  }

  /**
   * Override to return the desired instance of {@link Logger} for your application.
   */
  public Logger getApplicationLogger() {
    return LOGGER;
  }

  // Set

  protected void setGuicer(Guicer guicer) {
    this.guicer = guicer;
    LOGGER.info("Guicer has been reset!");
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

  public void setSystemResourceHandler(ISystemResourceHandler systemResourceHandler) {
    this.systemResourceHandler = systemResourceHandler;
  }

  public Reflectory getReflectory() {
    return reflectory;
  }

  public void setReflectory(Reflectory reflectory) {
    this.reflectory = reflectory;
  }
}
