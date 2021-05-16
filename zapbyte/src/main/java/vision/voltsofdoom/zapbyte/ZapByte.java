package vision.voltsofdoom.zapbyte;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import com.google.gson.GsonBuilder;
import vision.voltsofdoom.api.guice.Guicer;
import vision.voltsofdoom.api.guice.Guicer.GuiceTest;
import vision.voltsofdoom.api.zapyte.config.IConfigurationOptionsHandler;
import vision.voltsofdoom.zapbyte.config.ConfigurationFileSerializer;
import vision.voltsofdoom.zapbyte.config.StreamedConfigurationOptionsHandler;
import vision.voltsofdoom.zapbyte.reflectory.Reflectory;
import vision.voltsofdoom.zapbyte.registry.IRegistry;
import vision.voltsofdoom.zapbyte.registry.Registry;
import vision.voltsofdoom.zapbyte.resource.ISystemResourceHandler;
import vision.voltsofdoom.zapbyte.resource.ZBSystemResourceHandler;

/**
 * The main class of the {@link ZapByte} module. Any application wishing to use {@link ZapByte}
 * should extends this class. Its <code>main</code> method should create a new instance of itself
 * and call {@link #run()} to start the application.
 */
public abstract class ZapByte<Z extends ZapByte<Z>> {

  protected static ZapByte<? extends ZapByte<?>> instance;

  private boolean launched = false;
  private Set<ZapBit> zapBits;
  private IConfigurationOptionsHandler configHandler;
  private Guicer guicer;
  private static final String ZAPBYTE = "zapbyte";
  private ISystemResourceHandler systemResourceHandler;
  private Reflectory reflectory;
  private IRegistry registry;

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
    
    instance = this;

    System.out.println(" >>> WELCOME TO THE ZAPBYTE LOADER. ZAPBYTE IS NOW CONSTRUCTING. LOGGING WILL BE CONFIGURED FIRST. <<< ");

    ZapByteReference.APPLICATION_NAMESPACE = applicationNamespace;

    // You can't use logging calls until after this
    configureLogger();
    LOGGER.info("Configured logging");

    // Configure the uncaught exception handler
    LOGGER.info("Configuring ZapByteUncaughtExceptionHandler");
    Thread.setDefaultUncaughtExceptionHandler(new ZapByteUncaughtExceptionHandler());
    LOGGER.debug("Done");

    // Set up dependency injection
    LOGGER.info("Configuring Guicer");
    setGuicer(new Guicer(new ZapByteGuiceBindingModule()));
    LOGGER.info("Testing Guicer");
    GuiceTest guiceTest = guicer.getInjector().getInstance(GuiceTest.class);
    LOGGER.debug("Done");

    // Make an empty set for zap bits
    LOGGER.info("Configuring ZapBit HashSet");
    setZapBits(new HashSet<ZapBit>());
    LOGGER.debug("Done");

    // Set up configuration handler
    LOGGER.info("Configuring ConfigHandler");
    setConfigHandler(new StreamedConfigurationOptionsHandler(new GsonBuilder().setPrettyPrinting().create()));
    LOGGER.debug("Done");

    // Set the system resource handler
    LOGGER.info("Configuring ISystemResourceHandler");
    setSystemResourceHandler(guicer.getInjector().getInstance(ZBSystemResourceHandler.class));
    LOGGER.debug("Done");

    // Make a registry (which is currently blank)
    LOGGER.info("Constructing blank registry");
    registry = new Registry();
    LOGGER.debug("Done");

    LOGGER.info("ZapByte is now constructed.");
  }

  /**
   * Logging calls WILL NOT work before this method is called, hence it is called before anything
   * else.
   */
  private void configureLogger() {

    String logbackXMLPath = ZapByteReference.getConfig() + "logback.xml";

    // Set location of config file
    System.setProperty("logback.configurationFile", logbackXMLPath);
    File logbackXML = new File(logbackXMLPath);

    if (!logbackXML.exists()) {
      System.err.println("LOGBACK CONFIGURATION DOES NOT EXIST! WILL WRITE A NEW CONFIGURATION FILE!");
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(logbackXML))) {

        // Default logging configuration
        writer.write(
            "<configuration scan=\"true\" debug=\"true\"><appender name=\"Console-STDOUT\" class=\"ch.qos.logback.core.ConsoleAppender\"><encoder><pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern></encoder></appender><appender name=\"File\" class=\"ch.qos.logback.core.FileAppender\"><file>${vision.voltsofdoom.zapbyte.log.outputFile}</file><encoder><pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern></encoder></appender><root level=\"trace\"><appender-ref ref=\"Console-STDOUT\" /><appender-ref ref=\"File\" /></root><contextListener class=\"ch.qos.logback.classic.jul.LevelChangePropagator\"><resetJUL>true</resetJUL></contextListener></configuration>");

      } catch (IOException e) {
        System.err.println("An IOException has occured whilst configuring logging.");
        e.printStackTrace();
        System.exit(-1);
      }
    }

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
    LOGGER.info("Running the ZapByte loading cycle!");

    LOGGER.debug("Collecting ZapBits");
    collectZapbits();
    LOGGER.debug("Collected " + zapBits.size() + " ZapBits");

    if (launched) {
      throw new IllegalStateException("This instance of <? extends ZapByte> has already been launched!");
    }

    launched = true;
    LOGGER.debug("Launching... (launched=true)");

    LOGGER.debug("Loading configuration options");
    configHandler.add(ConfigurationFileSerializer.OTHER_FILES_DEFAULT_KEY, new ConfigurationFileSerializer(new GsonBuilder().setPrettyPrinting().create(), new File(ZapByteReference.getConfig())).objectifyFiles());
    configHandler.flatten();
    LOGGER.debug(configHandler.getOption("zapbyte.custom_configuration_test_success_message").getAsString());
    LOGGER.debug("Loaded ZapByte IConfigurationOptionsHandler configHandler");

    // Get all into map
    LOGGER.debug("Mapping ZapBits");
    Map<Integer, ZapBit> bits = new HashMap<Integer, ZapBit>();
    zapBits.forEach((bit) -> bits.put(bit.getPriority(), bit));
    LOGGER.debug("Done");

    // Sort
    LOGGER.debug("Sorting ZapBits");
    List<Integer> ints = new ArrayList<Integer>(bits.keySet());
    Collections.sort(ints);
    LOGGER.debug("Done");

    // Run
    LOGGER.warn("Running ZapBits");
    for (Integer integer : ints) {
      ZapBit bit = bits.get(integer);
      LOGGER.debug("Running ZapBit: name=" + bit.getName() + " priority=" + integer);
      bit.run();
    }
    LOGGER.warn("All ZapBits have been run");

    LOGGER.warn("ZapBit execution complete. Continuing external (non-ZapBit) execution.");
    continueExecution();

    LOGGER.error("ZapByte cycle complete. Exiting.");
    System.exit(0);
  }

  public void addZapBit(ZapBit bit) {
    zapBits.add(bit);
  }

  // Get
  
  public static ZapByte<? extends ZapByte<?>> getInstance() {
    return instance;
  }

  public static String getZapbyte() {
    return ZAPBYTE;
  }

  public Guicer getGuicer() {
    return guicer;
  }

  public IConfigurationOptionsHandler getConfigurationHandler() {
    return configHandler;
  }

  public Set<ZapBit> getZapBits() {
    return zapBits;
  }

  public ISystemResourceHandler getSystemResourceHandler() {
    return systemResourceHandler;
  }

  public IRegistry getRegistry() {
    return registry;
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

  protected void setConfigHandler(IConfigurationOptionsHandler configHandler) {
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
