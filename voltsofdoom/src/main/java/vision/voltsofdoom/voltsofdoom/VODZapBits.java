package vision.voltsofdoom.voltsofdoom;

import vision.voltsofdoom.voltsofdoom.adventure.GenerateAdventuresEvent;
import vision.voltsofdoom.zapbyte.ZapBit;
import vision.voltsofdoom.zapbyte.bandwagon.BandWagon;
import vision.voltsofdoom.zapbyte.reflectory.Reflectories;
import vision.voltsofdoom.zapbyte.window.LoadingWindow;

public class VODZapBits {

  public static final ZapBit ADD_VOLTS_OF_DOOM_TO_ADDITIONAL_REFLECTORY_CLASSES_19 = new ZapBit("add_volts_of_doom_to_additional_reflectory_classes_19", 19, () -> {
    Reflectories.addAdditionalClass(VoltsOfDoom.class);
  });

  public static final ZapBit GENERATE_ADVENTURES_62 = new ZapBit("create_registry_generate_adventures_62", 62, () -> {
    LoadingWindow.loadingWindow.setDetailedStatus(GenerateAdventuresEvent.DETAILED_STATUS);
    BandWagon.playEvent(new GenerateAdventuresEvent());
  });

}
