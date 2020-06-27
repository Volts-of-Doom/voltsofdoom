package vision.voltsofdoom.coresystem.loading.registry;

import vision.voltsofdoom.coresystem.loading.JarLoadingManager;
import vision.voltsofdoom.coresystem.loading.resource.search.JarFinder;
import vision.voltsofdoom.coresystem.loading.resource.search.JarScanner;
import vision.voltsofdoom.coresystem.play.adventure.AdventureLoader;

public class RegistryLoader {

	public static void main(String[] args) {
		create();
	}

	public static Registry create() {

		if (Registry.isCreated()) {
			throw new IllegalStateException("Cannot create Registry twice!");
		}

		JarLoadingManager jar = new JarLoadingManager(new JarFinder(), new JarScanner()).init();
		AdventureLoader adventure = new AdventureLoader().init();
		RegistryLoaderMissionControl registry = new RegistryLoaderMissionControl().init();
		Registry gameReg = Registry.getInstance();
		System.out.println(gameReg);
		return gameReg;
	}
}
