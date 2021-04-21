package vision.voltsofdoom.zapbyte.mod;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;
import vision.voltsofdoom.zapbyte.reflectory.Reflectory;

/**
 * Handles the handling of {@link Mod} annotations.
 * 
 * @author GenElectrovise
 *
 */
public class Mods {
  public static LinkedHashMap<String, Mod> mods = new LinkedHashMap<>();
  public static LinkedHashMap<Class<?>, Object> instances = new LinkedHashMap<>();

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

  public static void instances(Collection<Reflectory> values) {
    for (Reflectory reflectory : values) {
      Set<Class<?>> set = reflectory.getReflections().getTypesAnnotatedWith(Mod.class);
      for (Class<?> clazz : set) {
        try {
          
          Object modInstance = clazz.newInstance();
          instances.put(clazz, modInstance);
          
        } catch (InstantiationException in) {
          in.printStackTrace();
        } catch (IllegalAccessException ia) {
          ia.printStackTrace();
        }
      }
    }
  }
}
