package vision.voltsofdoom.coresystem.universal.main;

import java.io.IOException;

import vision.voltsofdoom.coresystem.loading.LoadingManager;
import vision.voltsofdoom.coresystem.loading.registry.Registry;

public class GameController {
	private static SystemControl systemControl;
	private static Registry registry;

	public void initialiseAll() throws IOException {
		try {
			
			LoadingManager.load();

			setRegistry(Registry.createInThreadedFashion());

			// Begin the rest of the game
			GameController.getSystemControl().begin();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Get and set

	public static SystemControl getSystemControl() {
		return systemControl;
	}

	public static void setSystemControl(SystemControl systemControl) {
		GameController.systemControl = systemControl;
	}

	public static Registry getRegistry() {
		return registry;
	}

	public static void setRegistry(Registry registry) {
		GameController.registry = registry;
	}
}
