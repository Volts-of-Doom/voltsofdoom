package genelectrovise.voltsofdoom_coresystem.main;

import java.io.IOException;

import genelectrovise.voltsofdoom_coresystem.adventure.AdventureLoader;
import genelectrovise.voltsofdoom_coresystem.loader.JarFinder;
import genelectrovise.voltsofdoom_coresystem.loader.JarScanner;
import genelectrovise.voltsofdoom_coresystem.loader.LoaderMissionControl;
import genelectrovise.voltsofdoom_coresystem.loader.LoaderReference;
import genelectrovise.voltsofdoom_coresystem.opengl.RenderEngine;
import genelectrovise.voltsofdoom_coresystem.opengl.WindowHolder;
import genelectrovise.voltsofdoom_coresystem.opengl.render.LoadingScreenRenderer;
import genelectrovise.voltsofdoom_coresystem.registry.RegistryLoaderMissionControl;

public class SystemControl {
	SystemControl SYSTEM_CONTROL = this;

	// PreInit
	private WindowHolder windowholder = new WindowHolder();

	// Stage 1 (Load jars)
	private LoaderMissionControl loaderMissionControl;
	private JarFinder jarFinder;
	private JarScanner jarScanner;
	private LoaderReference lref;

	// Stage 2(Load registries)
	private RegistryLoaderMissionControl registryMissionControl = new RegistryLoaderMissionControl();
	private AdventureLoader adventureloader = new AdventureLoader();

	public SystemControl() throws IOException {
		jarFinder = new JarFinder();
		jarScanner = new JarScanner();
		loaderMissionControl = new LoaderMissionControl(jarFinder, jarScanner);

		preInit();
		initStage1();
		initStage2();
	}

	/**
	 * Prepares the game window, among other things
	 * @throws IOException 
	 */
	public void preInit() throws IOException {
		windowholder.start();
	}

	/**
	 * Initialises the game. Calls init() methods on MissionControl classes in order
	 * to load the game.
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
