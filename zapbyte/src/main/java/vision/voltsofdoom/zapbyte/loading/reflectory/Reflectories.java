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
    builder.withFilterBuilder(defaultFilterBuilder());
    return builder;
  }

  /**
   * Excludes a ton of stuff. Trust me you don't want this stuff. If you do want this stuff, but
   * it's excluded here, just use this, and use {@link FilterBuilder#include(String)} or one of the
   * companion methods to get it back. This way is better trust me... (cuts logs from 2.5MB to 0.1MB,
   * as of 17/11/20 by excluding jars which give "failed to scan" logs)
   */
  public static FilterBuilder defaultFilterBuilder() {
    return new FilterBuilder()//
        // Exclude sources
        .excludePackage("java")//
        .excludePackage("lib/").exclude("lib/")//
        .excludePackage("lib.").exclude("lib.")//
        .excludePackage("resources/").exclude("resources/")//
        .excludePackage("resources.").exclude("resources.")//
        .excludePackage("META-INF/").exclude("META-INF/")//
        .excludePackage("META-INF.").exclude("META-INF.")//
        .excludePackage("org.reflections").excludePackage("org/reflections")//
        .excludePackage("com.google").excludePackage("com/google")//
        .excludePackage("org.lwjgl").excludePackage("org/lwjgl")//
        .excludePackage("ch.qos").excludePackage("ch/qos")//
        .excludePackage("edu.umd").excludePackage("edu/umd")//
        .excludePackage("jdk.nashorn").excludePackage("jdk/nashorn")//
        .excludePackage("jdk.internal").excludePackage("jdk/internal")//
        .excludePackage("windows.x64").excludePackage("windows/x64")//
        .excludePackage("net.jcip").excludePackage("net/jcip")//
        .excludePackage("com.sun").excludePackage("com/sun")//
        .excludePackage("sun.text").excludePackage("sun/text")//

        // Exclude resources
        .excludePackage("image/").exclude("image/")//
        .excludePackage("image.").exclude("image.")//
        .excludePackage("font/").exclude("font/")//
        .excludePackage("font.").exclude("font.")//
    ;
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
