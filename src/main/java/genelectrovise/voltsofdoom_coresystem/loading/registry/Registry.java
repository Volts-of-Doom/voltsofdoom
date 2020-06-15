package genelectrovise.voltsofdoom_coresystem.loading.registry;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import genelectrovise.voltsofdoom_coresystem.universal.log.VODLog4J;

public class Registry {

	private static boolean created = false;

	private static HashMap<String, ModRegistry> VOLTS_OF_DOOM_GAME_REGISTRY = new HashMap<String, ModRegistry>();
	private static Registry instance = new Registry();

	public HashMap<String, ModRegistry> retrieve() {
		return VOLTS_OF_DOOM_GAME_REGISTRY;
	}

	public ModRegistry add(ModRegistry reg) {
		return retrieve().put(reg.getModid(), reg);
	}

	public static Registry getInstance() {
		return instance;
	}

	public static RegistryObject<?> getObjFromModRegistry(String modid, String registryname) {
		try {
			RegistryObject<?> obj = Registry.getInstance().retrieve().get(modid).getREGISTRY().get(registryname);
			if (obj == null) {
				throw new NullPointerException("No RegistryObject found of key " + modid + ":" + registryname);
			}
			return obj;

		} catch (NullPointerException n) {
			VODLog4J.LOGGER.error("That object doesn't exist! Failed to retrieve " + modid + ":" + registryname);
			n.printStackTrace();
		}

		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Registry{");

		builder.append(VOLTS_OF_DOOM_GAME_REGISTRY.toString());

		builder.append("}");
		return builder.toString();
	}

	public static Registry createInThreadedFashion() {
		
		if(Registry.isCreated()) {
			throw new IllegalStateException("Cannot create Registry twice!");
		}
		
		try {

			Future<Registry> future = Executors.newSingleThreadExecutor().submit(() -> {
				return RegistryLoader.create();
			});

			Registry reg = future.get();
			created = true;

			return reg;

		} catch (Exception e) {
			e.printStackTrace();

			throw new IllegalStateException(
					"Error loading registry! An exception was caught during registry loading in the Registry class, which will lead to the failiure of execution. Program should now exit with failiure.");
		}
	}
	
	public static boolean isCreated() {
		return created;
	}

}
