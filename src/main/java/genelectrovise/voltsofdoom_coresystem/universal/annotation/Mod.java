package genelectrovise.voltsofdoom_coresystem.universal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Make the annotation available at runtime:
@Retention(RetentionPolicy.RUNTIME)

//Allow to use only on types:
@Target(ElementType.TYPE)

/**
 * Denotes a class which serves as the main class for a Mod. Class should also implement IModMainClass.
 * 
 * @author GenElectrovise
 *
 */
public @interface Mod {
	String modid();
}
