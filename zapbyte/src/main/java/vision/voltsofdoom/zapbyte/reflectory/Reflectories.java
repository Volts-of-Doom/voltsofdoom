package vision.voltsofdoom.zapbyte.reflectory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vision.voltsofdoom.zapbyte.loader.VisibleURLClassLoader;
import vision.voltsofdoom.zapbyte.resource.JarMapper;

public class Reflectories {

  private static final Logger LOGGER = LoggerFactory.getLogger(Reflectories.class);

  private static Map<String, Reflectory> reflectories = new HashMap<String, Reflectory>();
  private static List<Class<?>> additionalClasses = new ArrayList<>();
  private static List<String> additionalPackages = new ArrayList<>();

  public static Reflectory get(String key) {
    return reflectories.get(key);
  }

  public static Set<String> keyset() {
    return reflectories.keySet();
  }

  public static Collection<Reflectory> values() {
    return reflectories.values();
  }
  
  public static void generateReflectories() {
    LOGGER.info("Generating Reflectories...");

    generateReflectoriesForFiles(JarMapper.find());
    generateReflectoriesForClasses(additionalClasses);
    generateReflectoriesForPackages(additionalPackages);

    LOGGER.info("Reflectories have been generated!");
    LOGGER.debug("Reflectories are: " + reflectories.toString());
  }

  private static void generateReflectoriesForFiles(List<File> files) {

    LOGGER.debug("Generating Reflectories for files: " + files);

    for (File file : files) {
      try {
        reflectories.putIfAbsent(file.getName(), generateForFile(file));
      } catch (MalformedURLException m) {
        LOGGER.error("MalformedURLException generating reflectory for file: " + file);
        m.printStackTrace();
      }
    }
  }

  private static void generateReflectoriesForClasses(List<Class<?>> classes) {

    LOGGER.debug("Generating Reflectories for classes: " + classes);

    for (Class<?> clazz : classes) {
      reflectories.putIfAbsent(clazz.getName(), generateForClass(clazz));
    }
  }

  private static void generateReflectoriesForPackages(List<String> packs) {

    LOGGER.debug("Generating Reflectories for packages: " + packs);

    for (String pack : packs) {
      reflectories.putIfAbsent(pack, generateForPackage(pack));
    }
  }

  private static Reflectory generateForFile(File file) throws MalformedURLException {
    ReflectoryBuilder builder = ReflectoryBuilder.getAllDefaults();
    builder.withClassLoader(new VisibleURLClassLoader(new URL[] {file.toURI().toURL()}));
    builder.withVisibleName(file.getName());
    return builder.build();
  }

  private static Reflectory generateForClass(Class<?> clazz) {
    ReflectoryBuilder builder = ReflectoryBuilder.getAllDefaults();
    builder.withClassLoader(clazz.getClassLoader());
    builder.withVisibleName(clazz.getName());
    return builder.build();
  }

  private static Reflectory generateForPackage(String pack) {
    ReflectoryBuilder builder = ReflectoryBuilder.getAllDefaults();
    builder.withPackage(pack);
    builder.withVisibleName(pack);
    return builder.build();
  }

  public static void addAdditionalClass(Class<?> clazz) {
    additionalClasses.add(clazz);
  }

  public static void addAdditionalPackage(String string) {
    additionalPackages.add(string);
  }
}
