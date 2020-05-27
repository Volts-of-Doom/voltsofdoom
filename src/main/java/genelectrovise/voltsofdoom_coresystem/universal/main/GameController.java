package genelectrovise.voltsofdoom_coresystem.universal.main;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import genelectrovise.voltsofdoom_coresystem.loading.registry.Registry;
import genelectrovise.voltsofdoom_coresystem.loading.window.LoadingWindow;

public class GameController {
	private static SystemControl systemControl;
	private static Registry registry;

	public void initialiseAll() throws IOException {
		try {
			// New instance of SystemControl
			systemControl = new SystemControl();

			// Run the launcher. Create the Registry when prompted by user.
			runLauncher();

			// Begin the rest of the game
			// systemControl.begin();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void runLauncher() throws InterruptedException, ExecutionException {
		new LoadingWindow().run();
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
