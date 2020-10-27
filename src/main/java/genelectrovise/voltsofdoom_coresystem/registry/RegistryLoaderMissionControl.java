package genelectrovise.voltsofdoom_coresystem.registry;

import java.util.ArrayList;

import genelectrovise.voltsofdoom_coresystem.annotation.Mod;
import genelectrovise.voltsofdoom_coresystem.loader.LoaderReference;
import genelectrovise.voltsofdoom_coresystem.log.VODLog4J;

/**
 * Stage 2 of the game loading process. Loads all registries.
 * 
 * @author adam_
 *
 */
public class RegistryLoaderMissionControl {
	private final Registry registry = Registry.getInstance();
	private ArrayList<ModRegistry> listOfPreLoadingModRegistries;
	private final RegistryQueue registryQueue = new RegistryQueue();
	private LoaderReference lref;

	public RegistryLoaderMissionControl init(LoaderReference lref) {
		this.lref = lref;

		// Populates the ArrayList of modInstances
		lref.setModInstances(generateModInstances(lref));

		// Collects the registry classes from the modInstances
		listOfPreLoadingModRegistries = collectModRegistries(lref);

		// Adds everything in the listOfPreLoadingModRegistries to the GameRegistry
		addModRegistriesToGameRegistry();

		// Time to populate the ModRegisties!
		populateModRegistries();

		return this;
	}

	private void addModRegistriesToGameRegistry() {
		for (ModRegistry mr : listOfPreLoadingModRegistries) {
			Registry.getInstance().getREGISTRY().put(mr.getModid(), mr);
		}
	}

	private void populateModRegistries() {
		RegistryQueue.getInstance().populate();
	}

	/**
	 * Instanciates an ArrayList of instances of mods. When loaded, mods are given
	 * the chance to submit their own GenericRegistries to the RegistryQueue.
	 * 
	 * @param lref The LoaderMissionControl instance of LoaderReference.
	 * @return An ArrayList of instances of mods.
	 */
	public ArrayList<Object> generateModInstances(LoaderReference lref) {
		VODLog4J.LOGGER
				.status("Generating mod instances! If y'all have any GenericRegistries to submit, this is the time!");
		try {
			ArrayList<Object> instances = new ArrayList<Object>();

			for (Class<?> clazz : lref.getMods()) {
				instances.add(clazz.newInstance());
			}

			return instances;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Collects modids and creates ModRegistres for those modids.
	 * 
	 * @param lref The LoaderMissionControl instance of LoaderReference.
	 * @return An ArrayList of ModRegistries.
	 */
	public ArrayList<ModRegistry> collectModRegistries(LoaderReference lref) {

		ArrayList<ModRegistry> mr = new ArrayList<ModRegistry>();

		for (String modid : getAllModids(lref)) {
			mr.add(new ModRegistry(modid));
		}

		return mr;
	}

	/**
	 * Gets all modids from the list of mods' classes in the LoaderReference class.
	 * 
	 * @param lref The LoaderMissionControl instance of LoaderReference.
	 * @return An ArrayList of Strings for all of the modids.
	 */
	private ArrayList<String> getAllModids(LoaderReference lref) {
		ArrayList<String> modids = new ArrayList<String>();
		for (Class<?> clazz : lref.getMods()) {
			modids.add(clazz.getAnnotation(Mod.class).modid());
		}

		return modids;
	}

	// GETTERS AND SETTERS

	public Registry getGameRegistry() {
		return registry;
	}

	public ArrayList<ModRegistry> getListOfPreLoadingModRegistries() {
		return listOfPreLoadingModRegistries;
	}

	public RegistryQueue getRegistryQueue() {
		return registryQueue;
	}

	public LoaderReference getLref() {
		return lref;
	}
}