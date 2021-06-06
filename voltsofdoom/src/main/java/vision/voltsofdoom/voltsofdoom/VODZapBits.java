package vision.voltsofdoom.voltsofdoom;

import java.util.Map;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vision.voltsofdoom.voltsofdoom.adventure.GenerateAdventuresEvent;
import vision.voltsofdoom.voltsofdoom.resourcepack.loading.RegisterableResourceLoader;
import vision.voltsofdoom.zapbyte.ZapBit;
import vision.voltsofdoom.zapbyte.bandwagon.BandWagon;
import vision.voltsofdoom.zapbyte.reflectory.Reflectories;
import vision.voltsofdoom.zapbyte.registry.IRegistryEntry;
import vision.voltsofdoom.zapbyte.resource.IID;
import vision.voltsofdoom.zapbyte.window.LoadingWindow;

public class VODZapBits {
  
  // private static final Logger LOGGER = LoggerFactory.getLogger(VODZapBits.class);

  public static final ZapBit ADD_VOLTS_OF_DOOM_TO_ADDITIONAL_REFLECTORY_CLASSES_19 = new ZapBit("add_volts_of_doom_to_additional_reflectory_classes_19", 19, () -> {
    Reflectories.addAdditionalClass(VoltsOfDoom.class);
  });

  public static final ZapBit GENERATE_ADVENTURES_62 = new ZapBit("create_registry_generate_adventures_62", 62, () -> {
    LoadingWindow.loadingWindow.setDetailedStatus(GenerateAdventuresEvent.DETAILED_STATUS);
    BandWagon.playEvent(new GenerateAdventuresEvent());
  }); 
  
  public static final ZapBit INITIALISE_RESOURCE_LOADERS_75 = new ZapBit("initialise_resource_loaders_75", 75, () -> {
    Map<IID, Supplier<? extends IRegistryEntry<?>>> registry = VoltsOfDoom.getInstance().getRegistry().getMapOfType(RegisterableResourceLoader.class);

    registry.forEach((iid, sup) -> {      
      VoltsOfDoom.getInstance().getResourceManager().reload(true);
    });
  });

}
