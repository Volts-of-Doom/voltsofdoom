package vision.voltsofdoom.zapbyte.reflectory;

import org.reflections.Reflections;
import org.reflections.scanners.Scanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

/**
 * A place for the Volts of Doom monks to search through jar files in peace.
 * 
 * @author GenElectrovise
 *
 */
public class Reflectory {

  private ClassLoader[] classLoaders;
  private Scanner[] scanners;
  private Reflections reflections;
  private String visibleName;
  private FilterBuilder filterBuilder;
  private String[] packages;

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
    configBuilder.setUrls(ClasspathHelper.forClassLoader(classLoaders));
    configBuilder.addClassLoaders(classLoaders);
    configBuilder.addScanners(scanners);
    configBuilder.setInputsFilter(filterBuilder);
    configBuilder.forPackages(packages);

    setReflections(new Reflections(configBuilder));
    return this;
  }

  public ClassLoader[] getClassLoaders() {
    return classLoaders;
  }

  public Scanner[] getScanners() {
    return scanners;
  }

  public Reflections getReflections() {
    return reflections;
  }

  public String getVisibleName() {
    return visibleName;
  }

  public void setVisibleName(String visibleName) {
    this.visibleName = visibleName;
  }

  /**
   * @return the filterBuilder
   */
  public FilterBuilder getFilterBuilder() {
    return filterBuilder;
  }

  /**
   * @param filterBuilder the filterBuilder to set
   */
  void setFilterBuilder(FilterBuilder filterBuilder) {
    this.filterBuilder = filterBuilder;
  }

  /**
   * @param classLoader the classLoader to set
   */
  void setClassLoaders(ClassLoader[] classLoaders) {
    this.classLoaders = classLoaders;
  }

  /**
   * @param scanners the scanners to set
   */
  void setScanners(Scanner[] scanners) {
    this.scanners = scanners;
  }

  /**
   * @param reflections the reflections to set
   */
  private void setReflections(Reflections reflections) {
    this.reflections = reflections;
  }
}
