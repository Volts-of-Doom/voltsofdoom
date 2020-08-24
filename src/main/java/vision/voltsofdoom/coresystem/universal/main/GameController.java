package vision.voltsofdoom.coresystem.universal.main;

import vision.voltsofdoom.silverspark.core.Game;
import vision.voltsofdoom.silverspark.core.VariableTimestepGame;
import vision.voltsofdoom.coresystem.loading.LoadingManager;
import vision.voltsofdoom.coresystem.loading.registry.Registry;

import java.io.IOException;

public class GameController {
	private static Registry registry;

	public void initialiseAll() throws IOException {
		try {

			LoadingManager.load();

			/*
			 * setRegistry(Registry.createInThreadedFashion());
			 * 
			 * GameController.getSystemControl().begin();
			 */

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
