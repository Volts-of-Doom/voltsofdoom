package vision.voltsofdoom.zapbyte.loading.mod;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;

import vision.voltsofdoom.zapbyte.loading.reflectory.Reflectory;

/**
 * Handles the handling of {@link Mod} annotations. 
 * 
 * @author GenElectrovise
 *
 */
public class Mods {
	public static LinkedHashMap<String, Mod> mods = new LinkedHashMap<String, Mod>();

	public static void generate(Collection<Reflectory> collection) {
		for (Reflectory reflectory : collection) {
			Set<Class<?>> set = reflectory.getReflections().getTypesAnnotatedWith(Mod.class);
			for (Class<?> clazz : set) {
				Mod mod = clazz.getAnnotation(Mod.class);
				mods.put(mod.modid(), mod);
			}
		}
	}

	public static Mod get(String key) {
		return mods.get(key);
	}

	public static Set<String> keyset() {
		return mods.keySet();
	}

	public static Collection<Mod> values() {
		return mods.values();
	}
}
