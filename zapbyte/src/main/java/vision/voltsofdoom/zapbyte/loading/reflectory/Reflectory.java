package vision.voltsofdoom.zapbyte.loading.reflectory;

import java.util.ArrayList;
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

  private ClassLoader classLoader;
  private Scanner[] scanners;
  private Reflections reflections;
  private String visibleName;
  private FilterBuilder filterBuilder;

  /**
   * Prevents usage of the blank constructor. <b>Use {@link Reflectory.Builder}</b>
   */
  private Reflectory() {

  }

  /**
   * Creates this {@link Reflectory}'s instance of {@link Reflections}, which indexes the classes
   * given in its {@link ClassLoader}
   * 
   * @return this {@link Reflectory}
   */
  public Reflectory index() {
    ConfigurationBuilder configBuilder = new ConfigurationBuilder();
    configBuilder.setUrls(ClasspathHelper.forClassLoader(classLoader));
    configBuilder.addClassLoader(classLoader);
    configBuilder.addScanners(scanners);
    configBuilder.setInputsFilter(filterBuilder);

    setReflections(new Reflections(configBuilder));
    return this;
  }

  public ClassLoader getClassLoader() {
    return classLoader;
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
  private void setFilterBuilder(FilterBuilder filterBuilder) {
    this.filterBuilder = filterBuilder;
  }

  /**
   * @param classLoader the classLoader to set
   */
  private void setClassLoader(ClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  /**
   * @param scanners the scanners to set
   */
  private void setScanners(Scanner[] scanners) {
    this.scanners = scanners;
  }

  /**
   * @param reflections the reflections to set
   */
  private void setReflections(Reflections reflections) {
    this.reflections = reflections;
  }

  /**
   * Constructs a {@link Reflectory} using chained methods. Retrieve the completed
   * {@link Reflectory} using {@link #build()}
   * 
   * @author GenElectrovise
   *
   */
  public static class Builder {

    private Reflectory reflectory;
    private ArrayList<Scanner> scanners = new ArrayList<Scanner>();

    public Builder() {
      this.reflectory = new Reflectory();
    }

    /**
     * Gives the provided {@link ClassLoader} to the constructing {@link Reflectory}, which will be
     * used to index the correct tree of jars.
     * 
     * @param classLoader
     * @return This {@link Builder}
     */
    public Builder withClassLoader(ClassLoader classLoader) {
      reflectory.setClassLoader(classLoader);
      return this;
    }

    /**
     * Applies a {@link Scanner} to the constructing {@link Reflectory}, which will be used to
     * retrieve attributes from an indexed jar.
     * 
     * @param scanner
     * @return This {@link Builder}
     */
    public Builder withScanner(Scanner scanner) {
      this.scanners.add(scanner);
      return this;
    }

    public Reflectory build() {
      reflectory.setScanners(this.scanners.toArray(new Scanner[scanners.size()]));
      reflectory.index();
      return reflectory;
    }

    public Builder withVisibleName(String name) {
      reflectory.setVisibleName(name);
      return this;
    }

    public Builder withFilterBuilder(FilterBuilder filterBuilder) {
      reflectory.setFilterBuilder(filterBuilder);
      return this;
    }
  }
}
