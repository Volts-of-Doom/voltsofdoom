package vision.voltsofdoom.zapbyte;

import vision.voltsofdoom.zapbyte.bandwagon.BandWagon;
import vision.voltsofdoom.zapbyte.bandwagon.event.LoadingDoneEvent;
import vision.voltsofdoom.zapbyte.bandwagon.event.LoadingEvent;
import vision.voltsofdoom.zapbyte.bandwagon.event.RegistryStatusEvent;
import vision.voltsofdoom.zapbyte.mod.Mods;
import vision.voltsofdoom.zapbyte.reflectory.Reflectories;
import vision.voltsofdoom.zapbyte.registry.IRegistryStatus;
import vision.voltsofdoom.zapbyte.window.ILoadingWindowStatus;
import vision.voltsofdoom.zapbyte.window.LoadingWindow;

/**
 * Holds pre-made {@link ZapBit}s.
 * 
 * @author GenElectrovise
 *
 */
public class DefaultZapBits {

  public static final ZapBit CREATE_LOADING_WINDOW_10 = new ZapBit("create_loading_window_10", 10, () -> {
    // 1) Create loading window in new thread.
    LoadingWindow.loadingWindow.run();
    LoadingWindow.loadingWindow.setStatus(ILoadingWindowStatus.OPENING_WINDOW);
  });

  public static final ZapBit CREATE_REFLECTORIES_20 = new ZapBit("create_reflectories_20", 20, () -> {

    // Add vision.voltsofdoom to reflected paths
    Reflectories.addAdditionalPackage("vision.voltsofdoom");

    // 2) Create reflectories
    // a. find jars
    // b. create Reflectory for each
    LoadingWindow.loadingWindow.setStatus(ILoadingWindowStatus.GENERATING_REFLECTORIES);
    Reflectories.generateReflectories();
  });

  public static final ZapBit SCAN_FOR_MODS_30 = new ZapBit("scan_for_mods_30", 30, () -> {
    // 3) Scan for @Mods
    LoadingWindow.loadingWindow.setStatus(ILoadingWindowStatus.LOCATING_MODS);
    Mods.generate(Reflectories.values());
  });

  public static final ZapBit CREATE_BANDWAGON_40 = new ZapBit("create_bandwagon_40", 40, () -> {
    // 4.1) Scan for BandWagon subscribers (@Stowaway)
    LoadingWindow.loadingWindow.setStatus(ILoadingWindowStatus.LOCATING_BAND_WAGON_SUBSCRIBERS);
    BandWagon.collectSubscribers(Reflectories.values());

    // 4.2) Create BandWagon
    // a. subscribe all valid @Stowaway methods
    // b. scan @Stowaway types for valid methods
    // c. subscribe found valid methods
    LoadingWindow.loadingWindow.setStatus(ILoadingWindowStatus.CREATING_BAND_WAGON);
    BandWagon.playEvent(new LoadingEvent.TestEvent());
    LoadingWindow.loadingWindow.setDetailedStatus(LoadingEvent.TestEvent.DETAILED_STATUS);
    BandWagon.playEvent(new LoadingEvent.BandWagonCreation());
    LoadingWindow.loadingWindow.setDetailedStatus(LoadingEvent.BandWagonCreation.DETAILED_STATUS);
  });

  public static final ZapBit MAKE_MOD_INSTANCES_50 = new ZapBit("make_mod_instances_50", 50, () -> {
    // 3) Scan for @Mods
    LoadingWindow.loadingWindow.setStatus(ILoadingWindowStatus.LOCATING_MODS);
    Mods.instances(Reflectories.values());
  });

  public static final ZapBit FREEZE_REGISTRY_65 = new ZapBit("freeze_registry_65", 65, () -> {
    BandWagon.playEvent(new RegistryStatusEvent(ZapByte.getInstance(), IRegistryStatus.IMMUTABLE));
  });

  public static final ZapBit CLOSE_LOADING_WINDOW_70 = new ZapBit("close_loading_window", 70, () -> {
    // 6) Finally terminate the loading window
    LoadingWindow.loadingWindow.setStatus(ILoadingWindowStatus.DONE);
    BandWagon.playEvent(new LoadingDoneEvent());
    LoadingWindow.loadingWindow.setEnabled(false);
    LoadingWindow.loadingWindow.disableAndDispose();
  });

}
