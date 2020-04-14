package genelectrovise.voltsofdoom_coresystem.registry;

import java.util.HashMap;

import genelectrovise.voltsofdoom_coresystem.log.VODLog4J;

public class Registry {
	private HashMap<String, ModRegistry> VOLTS_OF_DOOM_GAME_REGISTRY = new HashMap<String, ModRegistry>();
	private static Registry instance = new Registry();

	public HashMap<String, ModRegistry> getREGISTRY() {
		return VOLTS_OF_DOOM_GAME_REGISTRY;
	}

	public ModRegistry add(ModRegistry reg) {
		return getREGISTRY().put(reg.getModid(), reg);
	}

	public static Registry getInstance() {
		return instance;
	}

	public static RegistryObject<?> getObjFromModRegistry(String modid, String registryname) {
		try {
			RegistryObject<?> obj = Registry.getInstance().getREGISTRY().get(modid).getREGISTRY().get(registryname);
			if (obj == null) {
				throw new NullPointerException("No RegistryObject found of key " + modid + ":" + registryname);
			}
			return obj;

		} catch (NullPointerException n) {
			VODLog4J.LOGGER.error("That object doesn't exist!");
			VODLog4J.LOGGER.error("Failed to retrieve " + modid + ":" + registryname);
			n.printStackTrace();
		}

		return null;
	}
}
