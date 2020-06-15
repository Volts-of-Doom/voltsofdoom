package genelectrovise.voltsofdoom_coresystem.loading.registry;

import java.util.ArrayList;

import genelectrovise.voltsofdoom_coresystem.loading.LoaderReference;
import genelectrovise.voltsofdoom_coresystem.universal.annotation.Mod;
import genelectrovise.voltsofdoom_coresystem.universal.log.VODLog4J;

/**
 * Stage 2 of the game loading process. Loads all registries.
 * 
 * @author GenElectrovise
 *
 */
public class RegistryLoaderMissionControl {
	private ArrayList<ModRegistry> listOfPreLoadingModRegistries;
	private final RegistryQueue registryQueue = new RegistryQueue();

	public RegistryLoaderMissionControl init() {
		// Populates the ArrayList of modInstances
		LoaderReference.Mods.setModInstances(generateModInstances());

		// Collects the registry classes from the modInstances
		listOfPreLoadingModRegistries = collectModRegistries();

		// Adds everything in the listOfPreLoadingModRegistries to the GameRegistry
		addModRegistriesToGameRegistry();

		// Time to populate the ModRegisties!
		populateModRegistries();

		return this;
	}

	private void addModRegistriesToGameRegistry() {
		for (ModRegistry mr : listOfPreLoadingModRegistries) {
			Registry.getInstance().retrieve().put(mr.getModid(), mr);
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
	public ArrayList<Object> generateModInstances() {
		VODLog4J.LOGGER
				.status("Generating mod instances! If y'all have any GenericRegistries to submit, this is the time!");
		try {
			ArrayList<Object> instances = new ArrayList<Object>();

			for (Class<?> clazz : LoaderReference.Mods.getMods()) {
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
	public ArrayList<ModRegistry> collectModRegistries() {

		ArrayList<ModRegistry> mr = new ArrayList<ModRegistry>();

		for (String modid : getAllModids()) {
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
	private ArrayList<String> getAllModids() {
		ArrayList<String> modids = new ArrayList<String>();
		for (Class<?> clazz : LoaderReference.Mods.getMods()) {
			modids.add(clazz.getAnnotation(Mod.class).modid());
		}

		return modids;
	}

	// GETTERS AND SETTERS

	public ArrayList<ModRegistry> getListOfPreLoadingModRegistries() {
		return listOfPreLoadingModRegistries;
	}

	public RegistryQueue getRegistryQueue() {
		return registryQueue;
	}
}
