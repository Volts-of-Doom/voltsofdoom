package vision.voltsofdoom.zapbyte.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vision.voltsofdoom.zapbyte.main.ZapByte;
import vision.voltsofdoom.zapbyte.reflectory.Reflectory;

/**
 * Sick of hearing about event buses? Well Volts of Doom has a {@link BandWagon}!
 * 
 * @author GenElectrovise
 *
 */
public class BandWagon {

  private static Logger LOGGER = LoggerFactory.getLogger(BandWagon.class);

  /** The methods found to be annotated with {@link Stowaway} */
  public static Map<Class<? extends IEvent>, List<Method>> stowawayMethods = new HashMap<Class<? extends IEvent>, List<Method>>();

  /**
   * Adds a method to the list of methods that can be invoked.
   * 
   * @param method
   */
  public static void stowawayMethodIfNotADuplicate(Class<? extends IEvent> type, Method method) {

    List<Method> methodsListToAddTo = stowawayMethods.get(type);
    
    if(isADuplicate(stowawayMethods, type, method)) {
      LOGGER.warn("Duplicate instance of method " + method + " will not be stowed away again");
      return;
    }

    if (methodsListToAddTo == null) {
      methodsListToAddTo = new ArrayList<Method>();
      stowawayMethods.put(type, methodsListToAddTo);
    }

    methodsListToAddTo.add(method);
  }

  private static boolean isADuplicate(Map<Class<? extends IEvent>, List<Method>> methodsMap, Class<? extends IEvent> type, Method method) {
    
    List<Method> methodsList = methodsMap.get(type);
    
    if(methodsList == null) {
      return false;
    }
    
    return methodsList.contains(method);
  }

  /**
   * CAN BE STREAMLINED BY LOADING METHODS TO A MAP!<br>
   * Play the given {@link IEvent} for all listeners of the {@link IEvent}'s type... Like a song!
   * 
   * <br>
   * <br>
   * <a href=
   * "https://stackoverflow.com/questions/4584541/check-if-a-class-object-is-subclass-of-another-class-object-in-java">Getting
   * subclasses </a>
   * 
   * @param event
   */
  public static void playEvent(IEvent event) {

    LOGGER.debug("Playing Event: " + event);

    List<Method> methodsToRun = stowawayMethods.get(event.getClass());
    methodsToRun.forEach((method) -> invokeMethod(event, method));
  }

  private static void invokeMethod(IEvent event, Method method) {

    try {

      method.setAccessible(true);
      method.invoke(method, event);

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
  }

  /**
   * Finds {@link Stowaway} methods from the class path.
   * 
   * @param reflectories
   */
  public static void collectSubscribers(Collection<Reflectory> reflectories) {

    /** All of the potential subscribed methods */
    Map<Class<? extends IEvent>, List<Method>> allMethods = new HashMap<Class<? extends IEvent>, List<Method>>();

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
  @SuppressWarnings("unchecked")
  private static void addMethodsAnnotatedWithStowaway(Map<Class<? extends IEvent>, List<Method>> allMethods, Reflectory reflectory) {

    Set<Method> annotatedMethods = reflectory.getReflections().getMethodsAnnotatedWith(Stowaway.class);

    for (Method method : annotatedMethods) {

      if (!validateMethod(method)) {
        LOGGER.error("Did not validate method " + method);
        return;
      }

      Class<?> rawType = method.getParameterTypes()[0];

      Class<? extends IEvent> castType = (Class<? extends IEvent>) rawType;

      List<Method> methodsListToAddTo = allMethods.get(castType);
      if (methodsListToAddTo == null) {
        methodsListToAddTo = new ArrayList<Method>();
        allMethods.put(castType, methodsListToAddTo);
      }

      methodsListToAddTo.add(method);
    }
  }

  /**
   * Add all methods in {@link Stowaway} classes
   * 
   * @param allMethods
   * @param reflectory
   */
  @SuppressWarnings("unchecked")
  private static void addMethodsFromTypesAnnotatedWithStowaway(Map<Class<? extends IEvent>, List<Method>> allMethods, Reflectory reflectory) {
    Set<Class<?>> types = reflectory.getReflections().getTypesAnnotatedWith(Stowaway.class);
    for (Class<?> type : types) {
      Method[] declaredTypeMethods = type.getDeclaredMethods();

      for (int i = 0; i < declaredTypeMethods.length; i++) {

        Method method = declaredTypeMethods[i];

        if (!validateMethod(method)) {
          LOGGER.error("Did not validate method " + method);
          return;
        }

        Class<?> rawType = method.getParameterTypes()[0];

        Class<? extends IEvent> castType = (Class<? extends IEvent>) rawType;

        List<Method> methodsListToAddTo = allMethods.get(castType);
        if (methodsListToAddTo == null) {
          methodsListToAddTo = new ArrayList<Method>();
          allMethods.put(castType, methodsListToAddTo);
        }

        methodsListToAddTo.add(method);
      }

    }
  }


  /**
   * Validate methods and adds valid ones to the stowaway list. Validation here may be a duplicate but
   * I think that's ok.
   * 
   * @param allMethods
   */
  private static void validateAndStowawayProposedMethods(Map<Class<? extends IEvent>, List<Method>> allMethods) {
    allMethods.forEach((type, methods) -> {
      methods.forEach((method) -> {
        ifValidThenStowaway(type, method);
      });
    });
  }

  /**
   * Performs {@link #validateMethod(Method)}, and if true stows the method away.
   * 
   * @param type
   * @param method
   */
  private static void ifValidThenStowaway(Class<? extends IEvent> type, Method method) {
    if (validateMethod(method)) {
      stowawayMethodIfNotADuplicate(type, method);
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
    if (!IEvent.class.isAssignableFrom(parameterType)) {
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
