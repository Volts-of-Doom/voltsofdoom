package vision.voltsofdoom.coresystem.universal.main;

import vision.voltsofdoom.silverspark.core.Game;
import vision.voltsofdoom.silverspark.core.VariableTimestepGame;
import vision.voltsofdoom.zapbyte.loading.registry.Registry;

/**
 * Is the first object to be created when the game starts. Manages loading via
 * the {@link LoadingManager#load()} method, and provides the game's central
 * "catch" block.
 * 
 * @author GenElectrovise
 *
 */
import java.io.IOException;

public class GameController {
	private static Registry registry;

	public void initialiseAll() throws IOException {
		try {

			//LoadingManager.load();

			Game game = new VariableTimestepGame();
			game.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Get and set

	public static Registry getRegistry() {
		return registry;
	}

	public static void setRegistry(Registry registry) {
		GameController.registry = registry;
	}
}
