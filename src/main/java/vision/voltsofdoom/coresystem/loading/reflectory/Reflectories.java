package vision.voltsofdoom.coresystem.loading.reflectory;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;

import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import vision.voltsofdoom.coresystem.loading.VODClassLoader;
import vision.voltsofdoom.coresystem.loading.reflectory.Reflectory.Builder;
import vision.voltsofdoom.coresystem.universal.main.VoltsOfDoomCoreSystem;
import vision.voltsofdoom.coresystem.universal.resource.jar.JarMapper;

public class Reflectories {

	/**
	 * A {@link LinkedHashMap} of {@link Reflectory}s -- one for each jar file found
	 * by {@link JarMapper#find()}, with their keys being the names of the files
	 * which they are bound to.
	 */
	private static LinkedHashMap<String, Reflectory> reflectories = new LinkedHashMap<String, Reflectory>();

	/**
	 * Generates the {@link #reflectories} list using .jar files found by
	 * {@link JarMapper#find()}
	 */
	public static void generate() {
		for (File file : JarMapper.find()) {

			try {

				Reflectory.Builder builder = defaultBuilder();
				URL[] urls = new URL[] { file.toURI().toURL() };
				builder.withClassLoader(new VODClassLoader(urls));
				Reflectory reflectory = builder.build();
				reflectories.putIfAbsent(file.getName(), reflectory);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		Reflectory vodRefl = defaultBuilder().withClassLoader(VoltsOfDoomCoreSystem.class.getClassLoader()).build();
		reflectories.putIfAbsent("volts_of_doom_core_system", vodRefl);
	}

	/**
	 * A {@link Builder} with default properties. Does not contain a
	 * {@link ClassLoader}.
	 * 
	 * @return The builder.
	 */
	public static Reflectory.Builder defaultBuilder() {
		Reflectory.Builder builder = new Reflectory.Builder();
		builder.withScanner(new TypeAnnotationsScanner());
		builder.withScanner(new MethodAnnotationsScanner());
		builder.withScanner(new SubTypesScanner(false));
		return builder;
	}

	/**
	 * Searches the {@link #reflectories} list, and returns the {@link Reflectory}
	 * bound to the given key.
	 * 
	 * @param key
	 * @return
	 */
	public static Reflectory get(String key) {
		return reflectories.get(key);
	}

	public static Set<String> keyset() {
		return reflectories.keySet();
	}

	public static Collection<Reflectory> values() {
		return reflectories.values();
	}
}
