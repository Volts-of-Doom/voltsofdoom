package vision.voltsofdoom.zapbyte.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import vision.voltsofdoom.zapbyte.loading.reflectory.Reflectory;
import vision.voltsofdoom.zapbyte.log.ZBLoggers;

/**
 * Sick of hearing about event buses? Well Volts of Doom has a {@link BandWagon}!
 * 
 * @author GenElectrovise
 *
 */
public class BandWagon {

  /** The methods found to be annotated with {@link Stowaway} */
  public static ArrayList<Method> stowawayMethods = new ArrayList<Method>();

  public static void stowawayMethod(Method method) {
    stowawayMethods.add(method);
  }

  /**
   * Play the given {@link Event} for all listeners of the {@link Event}'s type... Like a song! <i>a
   * - 1...! a - 2...! a - 1! 2! 3! 4!</i>
   * 
   * <br>
   * <br>
   * <a href=
   * "https://stackoverflow.com/questions/4584541/check-if-a-class-object-is-subclass-of-another-class-object-in-java">Getting
   * subclasses (for my own sanity)</a>
   * 
   * @param event
   */
  public static void playEvent(Event event) {

    ZBLoggers.ZAPBYTE.info("Playing Event: " + event);

    stowawayMethods.forEach((method) -> {
      try {

        Class<?> parameterType = method.getParameters()[0].getType();
        if (event.getClass().isAssignableFrom(parameterType)) {
          method.setAccessible(true);
          method.invoke(method, event);
        }

      } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
        if (e.getCause() != null) {
          ZBLoggers.ZAPBYTE.error("Throwing: " + e.getClass().getName());
          ZBLoggers.ZAPBYTE.error("Message: " + e.getLocalizedMessage());
          e.getCause().printStackTrace();
        } else {
          e.printStackTrace();
        }

      }
    });
  }

  public static void collectSubscribers(Collection<Reflectory> reflectories) {

    /** All of the potential subscribed methods */
    HashSet<Method> allMethods = new HashSet<Method>();

    // Collect all available methods
    for (Reflectory reflectory : reflectories) {

      // Add stowaway methods
      Set<Method> annotatedMethods =
          reflectory.getReflections().getMethodsAnnotatedWith(Stowaway.class);

      for (Method method : annotatedMethods) {
        allMethods.add(method);
      }

      // Add all methods in stowaway classes
      Set<Class<?>> types = reflectory.getReflections().getTypesAnnotatedWith(Stowaway.class);

      for (Class<?> type : types) {
        Method[] declaredTypeMethods = type.getDeclaredMethods();

        for (int i = 0; i < declaredTypeMethods.length; i++) {
          allMethods.add(declaredTypeMethods[i]);
        }
      }
    }

    // Validate methods and add to the stowaway list
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
      ZBLoggers.ZAPBYTE.debug("Could not validate Method : " + method
          + " : to the BandWagon because it is not static.");
      return false;
    }

    // If has not-one parameter
    if (!(method.getParameterCount() == 1)) {
      ZBLoggers.ZAPBYTE.debug("Could not validate Method : " + method
          + " : to the BandWagon because it does not have only 1 parameter");
      return false;
    }

    // If the superclass is not Event
    Class<?> parameterType = method.getParameters()[0].getType();
    if (!Event.class.isAssignableFrom(parameterType)) {
      ZBLoggers.ZAPBYTE.debug("Could not validate Method : " + method
          + " : to the BandWagon because it does not extend the Volts of Doom Event directly.");
      return false;
    }

    ZBLoggers.ZAPBYTE.debug("Validated Method : " + method
        + " : for subscription to the BandWagon, as it meets all required criteria. ");
    return true;
  }

  @Stowaway
  private static void createBandWagonEventListener(LoadingEvent.BandWagonCreation event) {
    ZBLoggers.ZAPBYTE.info("Creating BandWagon: Playing creation event.");
  }

}
