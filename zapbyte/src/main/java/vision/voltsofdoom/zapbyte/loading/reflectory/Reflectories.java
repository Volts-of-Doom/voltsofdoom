package vision.voltsofdoom.zapbyte.loading.reflectory;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.FilterBuilder;
import vision.voltsofdoom.zapbyte.loading.VODClassLoader;
import vision.voltsofdoom.zapbyte.main.ZapByte;
import vision.voltsofdoom.zapbyte.resource.JarMapper;

public class Reflectories {
  /**
   * A {@link LinkedHashMap} of {@link Reflectory}s -- one for each jar file found by
   * {@link JarMapper#find()}, with their keys being the names of the files which they are bound to.
   */
  private static LinkedHashMap<String, Reflectory> reflectories =
      new LinkedHashMap<String, Reflectory>();

  private static HashMap<String, Class<?>> additionalClassesToGenerateReflectories =
      new HashMap<String, Class<?>>();

  /**
   * @return A {@link Reflectory.Builder} with default properties. Does not contain a
   *         {@link ClassLoader}. <br>
   *         Excludes packages:
   *         <ul>
   *         <li>java
   *         <li>com.sun
   *         <li>org.reflections
   *         <li>com.google
   *         </ul>
   */
  public static Reflectory.Builder defaultBuilder() {
    Reflectory.Builder builder = new Reflectory.Builder();
    builder.withScanner(new TypeAnnotationsScanner());
    builder.withScanner(new MethodAnnotationsScanner());
    builder.withScanner(new SubTypesScanner(false));
    builder.withFilterBuilder(new FilterBuilder().excludePackage("java")
        .excludePackage("org.reflections").excludePackage("com.google").excludePackage("com.sun"));
    return builder;
  }

  /**
   * Searches the {@link #reflectories} list, and returns the {@link Reflectory} bound to the given
   * key.
   * 
   * @param key
   * @return
   */
  public static Reflectory get(String key) {
    return reflectories.get(key);
  }

  public static Set<String> keyset() {
    return reflectories.keySet();
  }

  public static Collection<Reflectory> values() {
    return reflectories.values();
  }

  /**
   * Generates the {@link #reflectories} list using .jar files found by {@link JarMapper#find()}
   */
  public static void generate() {
    for (File file : JarMapper.find()) {

      try {

        Reflectory.Builder builder = defaultBuilder();
        URL[] urls = new URL[] {file.toURI().toURL()};
        builder.withClassLoader(new VODClassLoader(urls));
        builder.withVisibleName(file.getName());
        Reflectory reflectory = builder.build();
        reflectories.putIfAbsent(file.getName(), reflectory);

      } catch (Exception e) {
        e.printStackTrace();
      }

    }

    // Additional
    for (String key : additionalClassesToGenerateReflectories.keySet()) {
      Reflectory.Builder builder = defaultBuilder();

      ClassLoader loader = additionalClassesToGenerateReflectories.get(key).getClassLoader();
      builder.withVisibleName(key);
      builder.withClassLoader(loader);

      Reflectory ref = builder.build();
      reflectories.putIfAbsent(key, ref);
    }

    // ZapByte
    Reflectory.Builder builder = defaultBuilder();

    ClassLoader loader = ZapByte.class.getClassLoader();
    builder.withVisibleName("zapbyte");
    builder.withClassLoader(loader);

    Reflectory ref = builder.build();
    reflectories.putIfAbsent("zapbyte", ref);

    // Finish
    ZapByte.LOGGER.info("Generated Reflectories with IDs: " + reflectories.keySet());
  }

  public static void addAdditionalClass(String key, Class<?> clazz) {
    additionalClassesToGenerateReflectories.put(key, clazz);
  }
}
