package vision.voltsofdoom.coresystem.universal.main;

import java.io.IOException;

import vision.voltsofdoom.coresystem.loading.LoadingManager;
import vision.voltsofdoom.coresystem.loading.registry.Registry;

/**
 * Is the first object to be created when the game starts. Manages loading via
 * the {@link LoadingManager#load()} method, and provides the game's central
 * "catch" block.
 * 
 * @author GenElectrovise
 *
 */
public class GameController {
	private static Registry registry;

	public void initialiseAll() throws IOException {
		try {

			LoadingManager.load();

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
