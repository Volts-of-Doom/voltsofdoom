package vision.voltsofdoom.zapbyte.event;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates a type or method that the {@link BandWagon} should be made aware of (i.e. should
 * recieve events). This annotation should only be applied to valid static methods, in order to
 * subscribe only them, or to types, in order to subscribe all valid static methods in them.<br>
 * <br>
 * A method is considered "valid" if it:
 * <ul>
 * <li>is static</li>
 * <li>has <i>only</i> one argument</li>
 * <li>their one argument is a subclass of the event class.</li>
 * </ul>
 * <br>
 * <b>For a class:</b><br>
 * Indicates that all static methods with a single-argument constructor, where the type of their
 * argument is an Event should be registered to the {@link BandWagon}.<br>
 * <br>
 * <b>For a static method:</b> <br>
 * Indicates that only this static method should be subscribed to the {@link BandWagon}
 * 
 * @author GenElectrovise
 *
 */
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Stowaway {

}
