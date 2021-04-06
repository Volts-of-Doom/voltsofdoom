package vision.voltsofdoom.zapbyte.reflectory;

import java.util.ArrayList;
import java.util.List;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

/**
 * A place for the Volts of Doom monks to search through classpath metadata in peace.
 * 
 * @author GenElectrovise
 *
 */
public class Reflectory {

  private String visibleName = "blank_reflectory_" + hashCode();
  private List<ClassLoader> classLoaders = new ArrayList<>();
  private List<Scanner> scanners = new ArrayList<>();
  private List<String> packages = new ArrayList<>();
  private FilterBuilder filterBuilder = new FilterBuilder();
  private Reflections reflections;

  /**
   * <b>Use {@link ReflectoryBuilder}</b>
   */
  protected Reflectory() {}

  /**
   * Creates this {@link Reflectory}'s instance of {@link Reflections}, which indexes the classes
   * given in its {@link ClassLoader}
   * 
   * @return this {@link Reflectory}
   */
  public Reflectory index() {
    ConfigurationBuilder configBuilder = new ConfigurationBuilder();
    configBuilder.setUrls(ClasspathHelper.forClassLoader(classLoaders.toArray(new ClassLoader[classLoaders.size()])));
    configBuilder.addClassLoaders(classLoaders);
    configBuilder.addScanners(scanners.toArray(new Scanner[scanners.size()]));
    configBuilder.setInputsFilter(filterBuilder);
    configBuilder.forPackages(packages.toArray(new String[packages.size()]));

    setReflections(new Reflections(configBuilder));
    return this;
  }

  public List<ClassLoader> getClassLoaders() {
    return classLoaders;
  }

  public void setClassLoaders(List<ClassLoader> classLoaders) {
    this.classLoaders = classLoaders;
  }

  public List<Scanner> getScanners() {
    return scanners;
  }

  public void setScanners(List<Scanner> scanners) {
    this.scanners = scanners;
  }

  public Reflections getReflections() {
    return reflections;
  }

  public void setReflections(Reflections reflections) {
    this.reflections = reflections;
  }

  public String getVisibleName() {
    return visibleName;
  }

  public void setVisibleName(String visibleName) {
    this.visibleName = visibleName;
  }

  public FilterBuilder getFilterBuilder() {
    return filterBuilder;
  }

  public void setFilterBuilder(FilterBuilder filterBuilder) {
    this.filterBuilder = filterBuilder;
  }

  public List<String> getPackages() {
    return packages;
  }

  public void setPackages(List<String> packages) {
    this.packages = packages;
  }
}
