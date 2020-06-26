package genelectrovise.voltsofdoom_coresystem.universal.band_wagon;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.google.common.reflect.Reflection;

/**
 * Sick of hearing about event buses? Well VOD has a {@link BandWagon}!
 * 
 * @author GenElectrovise
 *
 */
public class BandWagon {

	/** The types found to be annotated with {@link Stowaway} */
	public static ArrayList<Class<?>> stowawayTypes = new ArrayList<Class<?>>();
	/** The methods found to be annotated with {@link Stowaway} */
	public static ArrayList<Method> stowawayMethods = new ArrayList<Method>();
	/** The {@link Reflection} instances used for accessing mod types. */
	public static ArrayList<Reflection> reflections = new ArrayList<Reflection>();

}
