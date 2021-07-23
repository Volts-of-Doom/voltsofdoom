package vision.voltsofdoom.zapbyte.mod;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Lists;
import vision.voltsofdoom.zapbyte.reflectory.Reflectory;

/**
 * Handles the handling of {@link Mod} annotations.
 * 
 * @author GenElectrovise
 *
 */
public class Mods {
  private static final Logger LOGGER = LoggerFactory.getLogger(Mods.class);
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

    StringBuilder builder = new StringBuilder("Found mods:");
    mods.forEach((id, mod) -> {
      builder.append(" " + mod.modid() + "=" + mod);
    });
    LOGGER.info(builder.toString());
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

    List<Class<?>> alreadyCreated = Lists.newArrayList();

    for (Reflectory reflectory : values) {
      Set<Class<?>> set = reflectory.getReflections().getTypesAnnotatedWith(Mod.class);
      for (Class<?> clazz : set) {
        try {

          if (alreadyCreated.contains(clazz)) {
            LOGGER.debug("Duplicate Mod class found (will not reinstantiate) " + clazz.getName());
            continue;
          }

          LOGGER.info("Constructing Mod class: " + clazz.getSimpleName());

          @SuppressWarnings("deprecation")
          Object modInstance = clazz.newInstance();
          instances.put(clazz, modInstance);

          alreadyCreated.add(clazz);

        } catch (InstantiationException in) {
          in.printStackTrace();
        } catch (IllegalAccessException ia) {
          ia.printStackTrace();
        }
      }
    }
  }
}
