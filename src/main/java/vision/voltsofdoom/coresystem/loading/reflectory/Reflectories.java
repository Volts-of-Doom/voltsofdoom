package vision.voltsofdoom.coresystem.loading.reflectory;

import java.io.File;
import java.net.URL;
import java.util.LinkedHashMap;

import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import vision.voltsofdoom.coresystem.loading.VODClassLoader;
import vision.voltsofdoom.coresystem.loading.resource.jar.JarMapper;

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

				Reflectory.Builder builder = new Reflectory.Builder();
				builder.withClassLoader(new VODClassLoader(new URL[] { file.toURI().toURL() }));
				builder.withScanner(new TypeAnnotationsScanner());
				builder.withScanner(new MethodAnnotationsScanner());
				Reflectory reflectory = builder.build();
				reflectories.putIfAbsent(file.getName(), reflectory);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
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
}
