package genelectrovise.voltsofdoom_coresystem.registry;

import java.util.HashMap;


/**
 * Contains a HashMap of RegistryObjects serving as the registry for a @Mod
 * 
 * @author adam_
 *
 */
public class ModRegistry {
	/**
	 * The modid of the @Mod this is the ModRegistry for.
	 */
	private String modid;

	private HashMap<String, RegistryObject<?>> REGISTRY = new HashMap<String, RegistryObject<?>>();

	public ModRegistry(String modid) {
		this.modid = modid;
	}

	public String getModid() {
		return modid;
	}

	public HashMap<String, RegistryObject<?>> getREGISTRY() {
		return REGISTRY;
	}

	@SuppressWarnings("unchecked")
	public <T> RegistryObject<T> add(String registryName, RegistryObject<T> obj) {
		return (RegistryObject<T>) getREGISTRY().put(registryName, obj);
	}

}
