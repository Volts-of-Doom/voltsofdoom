package vision.voltsofdoom.zapbyte.main;

import vision.voltsofdoom.zapbyte.event.BandWagon;
import vision.voltsofdoom.zapbyte.event.LoadingEvent;
import vision.voltsofdoom.zapbyte.event.RegistryEvent;
import vision.voltsofdoom.zapbyte.mod.Mods;
import vision.voltsofdoom.zapbyte.reflectory.Reflectories;
import vision.voltsofdoom.zapbyte.window.ILoadingWindowStatus;
import vision.voltsofdoom.zapbyte.window.LoadingWindow;

/**
 * Holds pre-made {@link ZapBit}s.
 * 
 * @author GenElectrovise
 *
 */
public class DefaultZapBits {

  // STAGE 1

  public static final ZapBit CREATE_LOADING_WINDOW_10 = new ZapBit("create_loading_window_10", 10, () -> {
    // 1) Create loading window in new thread.
    LoadingWindow.loadingWindow.run();
    LoadingWindow.loadingWindow.setStatus(ILoadingWindowStatus.OPENING_WINDOW);
  });

  // STAGE 2

  public static final ZapBit CREATE_REFLECTORIES_20 = new ZapBit("create_reflectories_20", 20, () -> {

    // Add vision.voltsofdoom to reflected paths
    Reflectories.addAdditionalPackage("vision.voltsofdoom");

    // 2) Create reflectories
    // a. find jars
    // b. create Reflectory for each
    LoadingWindow.loadingWindow.setStatus(ILoadingWindowStatus.GENERATING_REFLECTORIES);
    Reflectories.generateReflectories();
  });

  // STAGE 3

  public static final ZapBit SCAN_FOR_MODS_30 = new ZapBit("scan_for_mods_30", 30, () -> {
    // 3) Scan for @Mods
    LoadingWindow.loadingWindow.setStatus(ILoadingWindowStatus.LOCATING_MODS);
    Mods.generate(Reflectories.values());
  });

  // STAGE 4

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

  // STAGE 5

  public static final ZapBit CREATE_REGISTRY_CREATE_REGISTRY_TYPES_50 = new ZapBit("create_registry_create_registry_types_50", 50, () -> {

    // 5) Begin Registry creation by firing registry events
    BandWagon.playEvent(new LoadingWindow.UpdateStatusEvent(ILoadingWindowStatus.CREATING_REGISTRY));
    LoadingWindow.loadingWindow.setStatus(ILoadingWindowStatus.CREATING_REGISTRY);

    // CreateRegistryTypesEvent
    LoadingWindow.loadingWindow.setDetailedStatus(RegistryEvent.CreateRegistryTypesEvent.DETAILED_STATUS);
    BandWagon.playEvent(new RegistryEvent.CreateRegistryTypesEvent());
  });

  public static final ZapBit CREATE_REGISTRY_CREATE_AND_SUBMIT_TYPE_REGISTRIES_54 = new ZapBit("create_registry_create_and_submit_type_registries_54", 54, () -> {
    // CreateAndSubmitRegistriesEvent
    LoadingWindow.loadingWindow.setDetailedStatus(RegistryEvent.CreateAndSubmitRegistriesEvent.DETAILED_STATUS);
    BandWagon.playEvent(new RegistryEvent.CreateAndSubmitRegistriesEvent());
  });

  public static final ZapBit CREATE_REGISTRY_POPULATE_TYPE_REGISTRIES_58 = new ZapBit("create_registry_populate_type_registries_58", 58, () -> {
    // PopulateTypeRegistriesEvent
    LoadingWindow.loadingWindow.setDetailedStatus(RegistryEvent.PopulateTypeRegistriesEvent.DETAILED_STATUS);
    BandWagon.playEvent(new RegistryEvent.PopulateTypeRegistriesEvent());
  });

  public static final ZapBit CREATE_REGISTRY_POLL_REGISTRY_TYPES_68 = new ZapBit("create_registry_poll_registry_types_68", 68, () -> {
    // PollRegistryTypeEventsEvent
    LoadingWindow.loadingWindow.setDetailedStatus(RegistryEvent.PollRegistryTypeEventsEvent.DETAILED_STATUS);
    BandWagon.playEvent(new RegistryEvent.PollRegistryTypeEventsEvent());
  });

  // STAGE 6

  public static final ZapBit CLOSE_LOADING_WINDOW_70 = new ZapBit("clost_loading_window", 70, () -> {
    // 6) Finally terminate the loading window
    LoadingWindow.loadingWindow.setStatus(ILoadingWindowStatus.DONE);
    BandWagon.playEvent(new RegistryEvent.LoadingDoneEvent());
    LoadingWindow.loadingWindow.setEnabled(false);
    LoadingWindow.loadingWindow.disableAndDispose();
  });

}
