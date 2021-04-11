package vision.voltsofdoom.api.guice;

import java.util.List;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * The home of Google {@link Guice} in Volts of Doom. <br>
 * <br>
 * To use {@link Guice} to create objects (i.e. like you ran a constructor), call
 * {@link Guicer#getInjector()}, then call {@link Injector#getInstance(Class)}.<br>
 * You can call on a bound and {@link Named} instance by annotating the constructor with an
 * {@link Named} annotation - the argument being the name of the instance you would like to invoke.
 * 
 * @author GenElectrovise
 *
 */
@Singleton
public class Guicer {
  
  private static final Guicer GUICER = new Guicer();
  private List<AbstractModule> modules;
  private Injector injector;

  public static Guicer getGuicer() {
    return GUICER;
  }

  public Guicer(AbstractModule... modules) {
    this.modules = Lists.newArrayList(modules);
    reload();
  }

  /**
   * Adds one {@link AbstractModule} to the {@link #modules}. To apply changes, call the
   * {@link #reload()} method.
   * 
   * @param abstractModule
   */
  public void addBindingModule(AbstractModule abstractModule) {
    modules.add(abstractModule);
  }

  /**
   * Reloads the {@link Guicer} and creates a new {@link Injector}.
   */
  public void reload() {
    this.injector = Guice.createInjector(getModules());
  }

  public Injector getInjector() {
    return injector;
  }

  public List<AbstractModule> getModules() {
    return modules;
  }

  public static void main(String[] args) {
    GuiceTest.main(null);
  }

  /**
   * A handy class for testing {@link Guicer}.
   * 
   * @author GenElectrovise
   *
   */
  public static class GuiceTest {

    String str;

    public static void main(String[] args) {
      Guicer guicer = new Guicer(new VODApiGuiceBindingModule());
      GuiceTest test = guicer.injector.getInstance(GuiceTest.class);
      test.test();
    }

    @Inject
    public GuiceTest(String str) {
      this.str = str;
      System.out.println("GuiceTest init, injection & comparison successful? " + test());
    }

    private boolean test() {
      return str == "thisisastring";
    }

  }

}
