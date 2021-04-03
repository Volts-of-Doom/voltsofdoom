package vision.voltsofdoom.zapbyte.reflectory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.FilterBuilder;
import com.google.common.collect.Lists;

/**
 * Use {@link Reflectories} to get instances. Constructs a {@link Reflectory} using chained methods.
 * Retrieve the completed {@link Reflectory} using {@link #build()}
 * 
 * @author GenElectrovise
 *
 */
public class ReflectoryBuilder {

  private Reflectory reflectory;
  private ArrayList<Scanner> scanners = new ArrayList<Scanner>();

  public ReflectoryBuilder() {
    this.reflectory = new Reflectory();
  }

  public static List<Scanner> getDefaultScanners() {
    return Lists.newArrayList(new TypeAnnotationsScanner(), new MethodAnnotationsScanner(), new SubTypesScanner(false));
  }

  /**
   * Excludes a ton of stuff. Trust me you don't want this stuff. If you do want this stuff, but it's
   * excluded here, just use this, and use {@link FilterBuilder#include(String)} or one of the
   * companion methods to get it back. This way is better trust me... (cuts logs from 2.5MB to 0.1MB,
   * as of 17/11/20 by excluding jars which give "failed to scan" logs)
   */
  public static FilterBuilder getDefaultFilterBuilder() {
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
   * Gives the provided {@link ClassLoader} to the constructing {@link Reflectory}, which will be used
   * to index the correct tree of jars.
   * 
   * @param classLoader
   * @return This {@link ReflectoryBuilder}
   */
  public ReflectoryBuilder withClassLoaders(ClassLoader[] classLoaders) {
    reflectory.setClassLoaders(classLoaders);
    return this;
  }

  /**
   * {@link #withClassLoaders(ClassLoader[])}
   * 
   * @param loader
   */
  public ReflectoryBuilder withClassLoader(ClassLoader loader) {
    withClassLoaders(new ClassLoader[] {loader});
    return this;
  }

  /**
   * Applies a {@link Scanner} to the constructing {@link Reflectory}, which will be used to retrieve
   * attributes from an indexed jar.
   * 
   * @param scanner
   * @return This {@link ReflectoryBuilder}
   */
  public ReflectoryBuilder withScanners(Collection<Scanner> scanners) {
    this.scanners.addAll(scanners);
    return this;
  }

  public Reflectory build() {
    reflectory.setScanners(this.scanners.toArray(new Scanner[scanners.size()]));
    reflectory.index();
    return reflectory;
  }

  public ReflectoryBuilder withVisibleName(String name) {
    reflectory.setVisibleName(name);
    return this;
  }

  public ReflectoryBuilder withFilterBuilder(FilterBuilder filterBuilder) {
    reflectory.setFilterBuilder(filterBuilder);
    return this;
  }
}
