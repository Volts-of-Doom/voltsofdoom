package genelectrovise.voltsofdoom_coresystem.loading.resource.search;

import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import genelectrovise.voltsofdoom_coresystem.loading.LoaderReference;
import genelectrovise.voltsofdoom_coresystem.loading.VODClassLoader;
import genelectrovise.voltsofdoom_coresystem.universal.annotation.Mod;
import genelectrovise.voltsofdoom_coresystem.universal.log.VODLog4J;

public class JarScanner {

	Reflections reflections;

	/**
	 * Initiates finding @Mod annotations by feeding the given VODClassLoader into a new Reflections instance.
	 * @param loader A VODClassLoader containing a URLClassLoader with a URL[] of jar files to scan.
	 * @param lref The LoaderMissionControl instance of LoaderReference.
	 * @return An ArrayList of classes annotated with @Mod
	 */
	public ArrayList<Class<?>> findModAnnotations(VODClassLoader loader, LoaderReference lref) {

		VODLog4J.LOGGER.info("Searching for @Mod Annotations in the files: ");
		for (URL url : loader.getUrls()) {
			VODLog4J.LOGGER.info(">: " + url);
		}

		return reflect(loader);
	}

	/**
	 * Uses Reflections to find classes annotated with @Mod and ascertain their modids.
	 * @param loader An instance of VODClassLoader, which contains a URLClassLoader.
	 * @return An ArrayList of annotated classes. Can access modids using <code>class.getAnnotation(Mod.class).modid();</code>.
	 */
	private ArrayList<Class<?>> reflect(VODClassLoader loader) {

		VODLog4J.LOGGER.status("Reflecting jars to find annotated classes!");
		reflections = new Reflections(loader.getLoader(), new SubTypesScanner(false), new TypeAnnotationsScanner());
		ArrayList<Class<?>> annotatedClasses = new ArrayList<Class<?>>();
		
		Set<Class<?>> set = reflections.getTypesAnnotatedWith(Mod.class, true);
		
		for (Class<?> clazz : set) {
			VODLog4J.LOGGER
					.info("Found class : " + clazz.getName() + " : with modid : " + clazz.getAnnotation(Mod.class).modid());
			annotatedClasses.add((Class<?>) clazz);
		}

		return annotatedClasses;

	}

	public Reflections getReflectionsInst() {
		return reflections;
	}
}
