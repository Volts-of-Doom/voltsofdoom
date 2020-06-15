package genelectrovise.voltsofdoom_coresystem.universal.main;

import java.io.IOException;

import genelectrovise.voltsofdoom_coresystem.loading.JarLoadingManager;
import genelectrovise.voltsofdoom_coresystem.loading.LoaderReference;
import genelectrovise.voltsofdoom_coresystem.loading.registry.RegistryLoaderMissionControl;
import genelectrovise.voltsofdoom_coresystem.loading.resource.search.JarFinder;
import genelectrovise.voltsofdoom_coresystem.loading.resource.search.JarScanner;
import genelectrovise.voltsofdoom_coresystem.play.adventure.AdventureLoader;
import genelectrovise.voltsofdoom_coresystem.universal.opengl.WindowHolder;

public class SystemControl {
	SystemControl System_Control = this;

	// PreInit
	private WindowHolder windowholder = new WindowHolder(this);

	// Stage 1 (Load jars)
	public JarLoadingManager loaderMissionControl;
	public JarFinder jarFinder;
	private JarScanner jarScanner;
	public LoaderReference lref;

	// Stage 2(Load registries)
	public RegistryLoaderMissionControl registryMissionControl = new RegistryLoaderMissionControl();
	public AdventureLoader adventureloader = new AdventureLoader();

	public boolean loadingComplete = false;

	public SystemControl() throws IOException {
		jarFinder = new JarFinder();
		jarScanner = new JarScanner();
		loaderMissionControl = new JarLoadingManager(jarFinder, jarScanner);
	}

	public void begin() throws IOException {
		postInit();
	}

	public void initAdventures() {
		adventureloader.init();
	}

	/**
	 * Final stage of initialisation. Opens the game window.
	 * 
	 * @throws IOException
	 */
	public void postInit() throws IOException {
		loadingComplete = true;
		windowholder.start();
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

	public JarLoadingManager getLoaderMissionControl() {
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
