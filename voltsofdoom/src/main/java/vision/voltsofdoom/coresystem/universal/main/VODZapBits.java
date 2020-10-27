package vision.voltsofdoom.coresystem.universal.main;

import vision.voltsofdoom.coresystem.play.adventure.GenerateAdventuresEvent;
import vision.voltsofdoom.silverspark.core.Game;
import vision.voltsofdoom.silverspark.core.VariableTimestepGame;
import vision.voltsofdoom.zapbyte.event.BandWagon;
import vision.voltsofdoom.zapbyte.loading.window.LoadingWindow;
import vision.voltsofdoom.zapbyte.main.ZapBit;

public class VODZapBits {
  public static final ZapBit CREATE_GAME_70 = new ZapBit(70, () -> {
    Game game = new VariableTimestepGame();
    game.start();
  });

  public static final ZapBit CREATE_REGISTRY_GENERATE_ADVENTURES_62 = new ZapBit(62, () -> {
    // GenerateAdventuresEvent
    LoadingWindow.loadingWindow.setDetailedStatus(GenerateAdventuresEvent.DETAILED_STATUS);
    BandWagon.playEvent(new GenerateAdventuresEvent());
  });
}
