package genelectrovise.voltsofdoom_coresystem.universal.main;

import java.io.IOException;

import genelectrovise.voltsofdoom_coresystem.loading.LoaderMissionControl;
import genelectrovise.voltsofdoom_coresystem.loading.LoaderReference;
import genelectrovise.voltsofdoom_coresystem.loading.registry.RegistryLoaderMissionControl;
import genelectrovise.voltsofdoom_coresystem.loading.resource.search.JarFinder;
import genelectrovise.voltsofdoom_coresystem.loading.resource.search.JarScanner;
import genelectrovise.voltsofdoom_coresystem.play.adventure.AdventureLoader;
import genelectrovise.voltsofdoom_coresystem.universal.opengl.RenderEngine;
import genelectrovise.voltsofdoom_coresystem.universal.opengl.WindowHolder;
import genelectrovise.voltsofdoom_coresystem.universal.opengl.render.TestLevelRenderer;

public class SystemControl {
	SystemControl System_Control = this;

	// PreInit
	private WindowHolder windowholder = new WindowHolder(this);

	// Stage 1 (Load jars)
	private LoaderMissionControl loaderMissionControl;
	private JarFinder jarFinder;
	private JarScanner jarScanner;
	private LoaderReference lref;

	// Stage 2(Load registries)
	private RegistryLoaderMissionControl registryMissionControl = new RegistryLoaderMissionControl();
	private AdventureLoader adventureloader = new AdventureLoader();

	public boolean loadingComplete = false;

	public SystemControl() throws IOException {
		
		jarFinder = new JarFinder();
		jarScanner = new JarScanner();
		loaderMissionControl = new LoaderMissionControl(jarFinder, jarScanner);

		preInit();
		initStage1();
		initStage2();
		postInit();
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
	
	/**
	 * Final stage of initialisation.
	 * @throws IOException 
	 */
	public void postInit() throws IOException {
		loadingComplete = true;
	}

	// GETTERS ==================

	public SystemControl getSystem_Control() {
		return System_Control;
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

	public WindowHolder getWindowHolder() {
		return windowholder;
	}

}
