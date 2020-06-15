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
* Denotes a class which serves as the Registry for a Mod. The class should
* contain all of the GenericRegistry objects for a Mod.
* 
* @author GenElectrovise
*
*/
public @interface GenericRegistryHolder {
	String holderId();
	
}
