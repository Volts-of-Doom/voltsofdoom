package vision.voltsofdoom.coresystem.universal.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Loggers {

	// Logger tiers
	// Main Systems
	public static final Logger CORESYSTEM = Logger.getLogger("coresystem");
	public static final Logger MOD = Logger.getLogger("mod");

	// Sub Systems
	public static final Logger CORESYSTEM_LOADING = Logger.getLogger("coresystem.loading");
	public static final Logger CORESYSTEM_MISCELLANEOUS = Logger.getLogger("coresystem.miscellaneous");

	public static final Logger MOD_DETAIL = Logger.getLogger("mod.detail");

	// Sub System Parts
	public static final Logger CORESYSTEM_LOADING_REGISTRY = Logger.getLogger("coresystem.loading.registry");
	public static final Logger CORESYSTEM_LOADING_BANDWAGON = Logger.getLogger("coresystem.loading.bandwagon");
	public static final Logger CORESYSTEM_LOADING_RESOURCE = Logger.getLogger("coresystem.loading.resource");

	public static final Logger CORESYSTEM_MISCELLANEOUS_MATH = Logger.getLogger("coresystem.miscellaneous.math");

	// Really fine details
	public static final Logger CORESYSTEM_MISCELLANEOUS_MATH_PICKY = Logger
			.getLogger("coresystem.miscellaneous.math.picky");

	// Configuration
	static {
		try {
			// 1
			CORESYSTEM.setLevel(Level.INFO);
			MOD.setLevel(Level.INFO);
			
			CORESYSTEM.addHandler(Handlers.FILE_HANDLER);
			CORESYSTEM.addHandler(Handlers.CONSOLE_HANDLER);

			MOD.addHandler(Handlers.FILE_HANDLER);
			MOD.addHandler(Handlers.CONSOLE_HANDLER);

			// 2
			CORESYSTEM_LOADING.setLevel(Level.FINE);
			CORESYSTEM_MISCELLANEOUS.setLevel(Level.FINE);

			MOD_DETAIL.setLevel(Level.FINEST);

			// 3
			CORESYSTEM_LOADING_REGISTRY.setLevel(Level.FINEST);
			CORESYSTEM_LOADING_BANDWAGON.setLevel(Level.FINEST);
			CORESYSTEM_LOADING_RESOURCE.setLevel(Level.FINEST);

			CORESYSTEM_MISCELLANEOUS_MATH.setLevel(Level.FINER);

			// 4
			CORESYSTEM_MISCELLANEOUS_MATH_PICKY.setLevel(Level.FINEST);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
