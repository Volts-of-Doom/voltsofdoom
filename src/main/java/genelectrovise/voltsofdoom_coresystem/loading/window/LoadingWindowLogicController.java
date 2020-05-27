package genelectrovise.voltsofdoom_coresystem.loading.window;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import genelectrovise.voltsofdoom_coresystem.loading.registry.Registry;
import genelectrovise.voltsofdoom_coresystem.universal.log.VODLog4J;
import genelectrovise.voltsofdoom_coresystem.universal.main.GameController;

public class LoadingWindowLogicController {

	public static void launch() {
		try {

			ExecutorService executor = Executors
					.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("registry_loader").build());
			
			executor.execute(() -> {
				GameController.getSystemControl().createRegistry();
				VODLog4J.LOGGER.debug("Registry created");
				GameController.setRegistry(Registry.getInstance());
				VODLog4J.LOGGER.debug("Registry extracted");
			});

			VODLog4J.LOGGER.debug("Registry creation initialised. Shutdown of related ExecutorService scheduelled.");
			executor.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
