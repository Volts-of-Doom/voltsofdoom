package vision.voltsofdoom.zapbyte.util;

import java.util.function.Function;
import java.util.function.Supplier;

public class ConditionalVerifier {

  public static final Function<Object, Boolean> IS_NULL = (t) -> {
    return t == null;
  };
  public static final Function<Object, Boolean> NOT_NULL = (t) -> {
    return t != null;
  };

  /**
   * Check the object against the given function by applying it.
   * 
   * @param <T>
   * @param condition
   * @param object
   * @return
   */
  public static <T> boolean check(Function<T, Boolean> condition, T object) {
    return condition.apply(object).booleanValue();
  }

  /**
   * {@link #checkOrThrow(Function, Object, Supplier, boolean)}
   * 
   * @param <T>
   * @param condition
   * @param object
   * @param exception
   * @return
   * @throws Exception
   */
  public static <T> boolean checkOrThrow(Function<T, Boolean> condition, T object, Supplier<Exception> exception) throws Exception {
    return checkOrThrow(condition, object, exception, true);
  }

  /**
   * 
   * Check the object against the given function by applying it, throwing an exception if failing.
   * 
   * @param <T>
   * @param condition The condition
   * @param object The object
   * @param exception The exception to throw if fails
   * @param rdState The 'success' state - an exception will be thrown when
   *        {@link Function#apply(Object)} does not equal this state.
   * @return The result of {@link Function#apply(Object)}
   * @throws Exception
   */
  public static <T> boolean checkOrThrow(Function<T, Boolean> condition, T object, Supplier<Exception> exception, boolean successState) throws Exception {
    boolean b = condition.apply(object).booleanValue();
    if (b != successState) {
      throw exception.get();
    }
    return b;
  }
}
