package vision.voltsofdoom.coresystem.loading.registry;

import java.util.ArrayList;

import vision.voltsofdoom.coresystem.play.entity.Entity;
import vision.voltsofdoom.coresystem.play.tile.Tile;
import vision.voltsofdoom.coresystem.universal.annotation.Mod;
import vision.voltsofdoom.coresystem.universal.log.VODLog4J;
import vision.voltsofdoom.coresystem.universal.main.ModItemTest;
import vision.voltsofdoom.coresystem.loading.registry.type.FinalisedTypeRegistry;
import vision.voltsofdoom.coresystem.loading.registry.type.ModRegistry;

public class RegistryQueue {
	private static final RegistryQueue instance = new RegistryQueue();
	private ArrayList<FinalisedTypeRegistry<?>> queuedRegistries = new ArrayList<FinalisedTypeRegistry<?>>();
	private ArrayList<FinalisedTypeRegistry<?>> nativeRegistries = new ArrayList<FinalisedTypeRegistry<?>>();
	static ArrayList<Class<?>> types = new ArrayList<Class<?>>();
	private ArrayList<FinalisedTypeRegistry<?>> toAddThenDelete = new ArrayList<FinalisedTypeRegistry<?>>();

	/**
	 * The queue for registering stuff.
	 */
	private ArrayList<FinalisedTypeRegistry<?>> queue = new ArrayList<FinalisedTypeRegistry<?>>();

	public ArrayList<FinalisedTypeRegistry<?>> getQueuedRegistries() {
		return queuedRegistries;
	}

	public static RegistryQueue getInstance() {
		return instance;
	}

	public void add(FinalisedTypeRegistry<?> genericRegistry) {
		queuedRegistries.add(genericRegistry);
	}

	public void populate() {
		VODLog4J.LOGGER.debug("Populating registries!");
		seperateNativeRegistries();
		addRegistryTestingTypes();
		whileListPopulatedIterate(nativeRegistries);
		whileListPopulatedIterate(queuedRegistries);

		addQueueToGameRegistry();
	}

	private void addQueueToGameRegistry() {
		for (FinalisedTypeRegistry<?> reg : queue) {
			ModRegistry mr = Registry.getInstance().retrieve().retrieveSupplier(reg.getModid());

			for (String key : reg.getMap().keySet()) {
				mr.add(key, reg.getMap().retrieveSupplier(key));
				VODLog4J.LOGGER.debug("Adding item with registryName : " + key);
			}
		}
	}

	/**
	 * Seperates the native registries from the non native ones into a
	 * nativeRegistries or leaves them in the queuedRegistries list.
	 */
	private void seperateNativeRegistries() {
		VODLog4J.LOGGER.debug("Seperating native registries!");

		for (FinalisedTypeRegistry<?> reg : queuedRegistries) {
			if (reg.isNative) {
				VODLog4J.LOGGER.debug(reg.toString() + " was native!");
				nativeRegistries.add(reg);
				queuedRegistries.remove(reg);
			} else {
				VODLog4J.LOGGER.debug(reg.toString() + " was not native");
			}
		}
	}

	/**
	 * Iterates through the given list of GenericRegistries, calling
	 * testTypesAndAddIfShould(reg) on each of them
	 * 
	 * @param list A list to iterate through
	 */
	private void whileListPopulatedIterate(ArrayList<FinalisedTypeRegistry<?>> list) {
		VODLog4J.LOGGER.debug("While '" + list.toString() + "' is populated, will register contents in right order!");
		while (list.size() > 0) {
			VODLog4J.LOGGER.debug("List size greater than 0!");

			for (FinalisedTypeRegistry<?> reg : list) {
				VODLog4J.LOGGER.debug("Testing types against type of " + reg.toString());
				testTypesAndAddIfShould(reg);
			}

			cleanToAddAndDelete();
		}
	}

	/**
	 * Goes through each of the <i>types</i> and adds this GenericRegistry to the
	 * queue if it is its turn.
	 * 
	 * @param reg
	 */
	private void testTypesAndAddIfShould(FinalisedTypeRegistry<?> reg) {
		for (Class<?> type : types) {
			if (reg.getType() == type) {
				VODLog4J.LOGGER.debug(reg.toString() + " was a type " + type.getName());
				toAddThenDelete.add(reg);
			} else {
				VODLog4J.LOGGER.debug(reg.toString() + " was not of type " + type.getName());
			}
		}
	}

	private void cleanToAddAndDelete() {
		VODLog4J.LOGGER.debug("Cleaning toAddAndDelete list");

		for (FinalisedTypeRegistry<?> reg : toAddThenDelete) {
			queue.add(reg);
			queuedRegistries.remove(reg);
		}

		toAddThenDelete.clear();
	}

	/**
	 * Adds all of the default registry testing types to the <i>types</i> ArrayList
	 * 
	 * @return The <i>types</i>
	 */
	private static ArrayList<Class<?>> addRegistryTestingTypes() {
		VODLog4J.LOGGER.debug("Adding registryTestingTypes!");

		types.add(ModItemTest.class);
		types.add(Tile.class);
		types.add(Entity.class);

		return types;
	}

	/**
	 * Appends a type to the <i>types</i> ArrayList
	 * 
	 * @param clazz
	 */
	public static void addRegistryTestingType(Class<?> clazz) {
		types.add(clazz);
	}
}
