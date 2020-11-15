package vision.voltsofdoom.coresystem.universal.main;

import vision.voltsofdoom.coresystem.play.adventure.GenerateAdventuresEvent;
import vision.voltsofdoom.silverspark.core.Game;
import vision.voltsofdoom.silverspark.core.VariableTimestepGame;
import vision.voltsofdoom.zapbyte.event.BandWagon;
import vision.voltsofdoom.zapbyte.loading.reflectory.Reflectories;
import vision.voltsofdoom.zapbyte.loading.window.LoadingWindow;
import vision.voltsofdoom.zapbyte.main.ZapBit;

public class VODZapBits {
  public static final ZapBit CREATE_GAME_100 = new ZapBit(100, () -> {
    Game game = new VariableTimestepGame();
    game.start();
  });

  public static final ZapBit CREATE_REGISTRY_GENERATE_ADVENTURES_62 = new ZapBit(62, () -> {
    // GenerateAdventuresEvent
    LoadingWindow.loadingWindow.setDetailedStatus(GenerateAdventuresEvent.DETAILED_STATUS);
    BandWagon.playEvent(new GenerateAdventuresEvent());
  });
  public static final ZapBit ADD_VOLTS_OF_DOOM_TO_ADDITIONAL_REFLECTORY_CLASSES_19 =
      new ZapBit(19, () -> {
        Reflectories.addAdditionalClass("voltsofdoom", VoltsOfDoomCoreSystem.class);
      });
}
