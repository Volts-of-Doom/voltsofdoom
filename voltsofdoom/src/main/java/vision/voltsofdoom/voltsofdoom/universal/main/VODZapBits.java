package vision.voltsofdoom.voltsofdoom.universal.main;

import vision.voltsofdoom.silverspark.core.Game;
import vision.voltsofdoom.silverspark.core.VariableTimestepGame;
import vision.voltsofdoom.voltsofdoom.play.adventure.GenerateAdventuresEvent;
import vision.voltsofdoom.voltsofdoom.universal.resource.image.TextureManager;
import vision.voltsofdoom.voltsofdoom.universal.util.Reference;
import vision.voltsofdoom.zapbyte.event.BandWagon;
import vision.voltsofdoom.zapbyte.loading.reflectory.Reflectories;
import vision.voltsofdoom.zapbyte.loading.window.LoadingWindow;
import vision.voltsofdoom.zapbyte.main.ZapBit;

public class VODZapBits {

  public static final ZapBit CREATE_TEXTURE_MANAGER_11 = new ZapBit("create_texture_manager_11", 11, () -> {
    TextureManager manager = new TextureManager(Reference.getTexturesDir());
    VoltsOfDoomCoreSystem.getInstance().setTextureManager(manager);
    VoltsOfDoomCoreSystem.getInstance().getTextureManager();
  });

  public static final ZapBit ADD_VOLTS_OF_DOOM_TO_ADDITIONAL_REFLECTORY_CLASSES_19 = new ZapBit("add_volts_of_doom_to_additional_reflectory_classes_19", 19, () -> {
    Reflectories.addAdditionalClass("voltsofdoom", VoltsOfDoomCoreSystem.class);
  });

  public static final ZapBit CREATE_REGISTRY_GENERATE_ADVENTURES_62 = new ZapBit(62, () -> {
    LoadingWindow.loadingWindow.setDetailedStatus(GenerateAdventuresEvent.DETAILED_STATUS);
    BandWagon.playEvent(new GenerateAdventuresEvent());
  });

  public static final ZapBit CREATE_GAME_100 = new ZapBit("create_game_100", 100, () -> {
    System.out.println("VODZapBits.enclosing_method() #31");
    Game game = new VariableTimestepGame(null);
    VoltsOfDoomCoreSystem.getInstance().setGame(game);
    game.start();
  });
}
