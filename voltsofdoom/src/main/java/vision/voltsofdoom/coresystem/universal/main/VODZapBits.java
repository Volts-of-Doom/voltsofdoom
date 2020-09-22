package vision.voltsofdoom.coresystem.universal.main;

import vision.voltsofdoom.coresystem.play.adventure.GenerateAdventuresEvent;
import vision.voltsofdoom.gamebase.core.Game;
import vision.voltsofdoom.gamebase.core.VariableTimestepGame;
import vision.voltsofdoom.zapbyte.bandwagon.BandWagon;
import vision.voltsofdoom.zapbyte.loading.window.LoadingWindow;
import vision.voltsofdoom.zapbyte.main.ZapBit;

public class VODZapBits {
	public static final ZapBit CREATE_GAME_70 = new ZapBit(70, () -> {
		Game game = new VariableTimestepGame("../silverspark/classes");
		game.start();
	});

	public static final ZapBit CREATE_REGISTRY_GENERATE_ADVENTURES_62 = new ZapBit(62, () -> {
		// GenerateAdventuresEvent
		LoadingWindow.loadingWindow.setDetailedStatus(GenerateAdventuresEvent.DETAILED_STATUS);
		BandWagon.playEvent(new GenerateAdventuresEvent());
	});
}
