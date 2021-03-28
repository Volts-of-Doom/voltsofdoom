package vision.voltsofdoom.zapbyte.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vision.voltsofdoom.zapbyte.loading.reflectory.Reflectory;
import vision.voltsofdoom.zapbyte.main.ZapByte;

/**
 * Sick of hearing about event buses? Well Volts of Doom has a {@link BandWagon}!
 * 
 * @author GenElectrovise
 *
 */
public class BandWagon {

  private static Logger LOGGER = LoggerFactory.getLogger(BandWagon.class);

  /** The methods found to be annotated with {@link Stowaway} */
  public static ArrayList<Method> stowawayMethods = new ArrayList<Method>();

  /**
   * Adds a method to the list of methods that can be invoked.
   * 
   * @param method
   */
  public static void stowawayMethod(Method method) {
    stowawayMethods.add(method);
  }

  /**
   * Play the given {@link Event} for all listeners of the {@link Event}'s type... Like a song!
   * 
   * <br>
   * <br>
   * <a href=
   * "https://stackoverflow.com/questions/4584541/check-if-a-class-object-is-subclass-of-another-class-object-in-java">Getting
   * subclasses </a>
   * 
   * @param event
   */
  public static void playEvent(Event event) {

    LOGGER.info("Playing Event: " + event);

    stowawayMethods.forEach((method) -> {
      try {

        Class<?> parameterType = method.getParameters()[0].getType();
        if (event.getClass().isAssignableFrom(parameterType)) {
          invokeMethod(event, method);
        }

        // IllegalArgumentException
      } catch (InvocationTargetException inv) {
        LOGGER.error("The BandWagon failed to invoke the method " + method);
        inv.printStackTrace();
      } catch (IllegalAccessException acc) {
        LOGGER.error("The BandWagon was unable to access the method " + method);
        acc.printStackTrace();
      } catch (IllegalArgumentException arg) {
        LOGGER.error("The BandWagon attempted to invoke the method " + method + " with incorrect arguments");
        arg.printStackTrace();
      }
    });
  }

  private static void invokeMethod(Event event, Method method) throws IllegalAccessException, InvocationTargetException {
    method.setAccessible(true);
    method.invoke(method, event);
  }

  /**
   * Finds {@link Stowaway} methods from the class path.
   * 
   * @param reflectories
   */
  public static void collectSubscribers(Collection<Reflectory> reflectories) {

    /** All of the potential subscribed methods */
    HashSet<Method> allMethods = new HashSet<Method>();

    // Collect all available methods
    for (Reflectory reflectory : reflectories) {
      addMethodsAnnotatedWithStowaway(allMethods, reflectory);
      addMethodsFromTypesAnnotatedWithStowaway(allMethods, reflectory);
    }

    validateAndStowawayProposedMethods(allMethods);
  }

  /**
   * Add {@link Stowaway} methods
   * 
   * @param allMethods
   * @param reflectory
   */
  private static void addMethodsAnnotatedWithStowaway(HashSet<Method> allMethods, Reflectory reflectory) {

    Set<Method> annotatedMethods = reflectory.getReflections().getMethodsAnnotatedWith(Stowaway.class);
    for (Method method : annotatedMethods) {
      allMethods.add(method);
    }
  }

  /**
   * Add all methods in {@link Stowaway} classes
   * 
   * @param allMethods
   * @param reflectory
   */
  private static void addMethodsFromTypesAnnotatedWithStowaway(HashSet<Method> allMethods, Reflectory reflectory) {
    Set<Class<?>> types = reflectory.getReflections().getTypesAnnotatedWith(Stowaway.class);
    for (Class<?> type : types) {
      Method[] declaredTypeMethods = type.getDeclaredMethods();

      for (int i = 0; i < declaredTypeMethods.length; i++) {
        allMethods.add(declaredTypeMethods[i]);
      }
    }
  }

  /**
   * Validate methods and add to the stowaway list
   * 
   * @param allMethods
   */
  private static void validateAndStowawayProposedMethods(HashSet<Method> allMethods) {
    for (Method method : allMethods) {
      if (validateMethod(method)) {
        stowawayMethod(method);
      }
    }
  }

  /**
   * 
   * @param method The {@link Method} to test
   * @return Whether that {@link Method} can be subscribed to the {@link BandWagon}
   */
  private static boolean validateMethod(Method method) {

    // If is not static
    if (!Modifier.isStatic(method.getModifiers())) {
      ZapByte.LOGGER.info("Could not validate Method : " + method.getName() + " : to the BandWagon because it is not static.");
      return false;
    }

    // If has not-one parameter
    if (!(method.getParameterCount() == 1)) {
      ZapByte.LOGGER.info("Could not validate Method : " + method.getName() + " : to the BandWagon because it does not have only 1 parameter");
      return false;
    }

    // If the superclass is not Event
    Class<?> parameterType = method.getParameters()[0].getType();
    if (!Event.class.isAssignableFrom(parameterType)) {
      ZapByte.LOGGER.info("Could not validate Method : " + method.getName() + " : to the BandWagon because it does not extend the Volts of Doom Event directly.");
      return false;
    }

    ZapByte.LOGGER.info("Validated Method : " + method.getName() + " : for subscription to the BandWagon, as it meets all required criteria. ");
    return true;
  }

  @Stowaway
  private static void createBandWagonEventListener(LoadingEvent.BandWagonCreation event) {
    ZapByte.LOGGER.info("Creating BandWagon: Playing creation event.");
  }

}
