package genelectrovise.voltsofdoom_coresystem.main;

import genelectrovise.voltsofdoom_coresystem.adventure.AdventureLoader;
import genelectrovise.voltsofdoom_coresystem.loader.JarFinder;
import genelectrovise.voltsofdoom_coresystem.loader.JarScanner;
import genelectrovise.voltsofdoom_coresystem.loader.LoaderMissionControl;
import genelectrovise.voltsofdoom_coresystem.loader.LoaderReference;
import genelectrovise.voltsofdoom_coresystem.registry.RegistryLoaderMissionControl;

public class SystemControl {
	SystemControl SYSTEM_CONTROL = this;

	// PreInit
//	private WindowManager windowmanager = new WindowManager();

	// Stage 1 (Load jars)
	private LoaderMissionControl loaderMissionControl;
	private JarFinder jarFinder;
	private JarScanner jarScanner;
	private LoaderReference lref;

	// Stage 2(Load registries)
	private RegistryLoaderMissionControl registryMissionControl = new RegistryLoaderMissionControl();
	private AdventureLoader adventureloader = new AdventureLoader();

	public SystemControl() {
		jarFinder = new JarFinder();
		jarScanner = new JarScanner();
		loaderMissionControl = new LoaderMissionControl(jarFinder, jarScanner);

		preInit();
		initStage1();
		initStage2();
	}
	
	public void preInit() {
	//	windowmanager.getGamewindow().title("Loading");
	}

	/**
	 * Initialises the game. Calls init() methods on IMissionControl classes in
	 * order to load the game.
	 */
	public void initStage1() {
		loaderMissionControl.init();
		lref = loaderMissionControl.getLref();

		registryMissionControl.init(lref);
	}

	public void initStage2() {
		adventureloader.init();
	}

	// GETTERS ==================

	public SystemControl getSYSTEM_CONTROL() {
		return SYSTEM_CONTROL;
	}
	
	public AdventureLoader getAdventureloader() {
		return adventureloader;
	}
	
	public RegistryLoaderMissionControl getRegistryMissionControl() {
		return registryMissionControl;
	}

	public LoaderMissionControl getLoaderMissionControl() {
		return loaderMissionControl;
	}

	public JarFinder getJarFinder() {
		return jarFinder;
	}

	public JarScanner getJarScanner() {
		return jarScanner;
	}

	public RegistryLoaderMissionControl getRegistryLoader() {
		return registryMissionControl;
	}

	public LoaderReference getLref() {
		return lref;
	}

}
