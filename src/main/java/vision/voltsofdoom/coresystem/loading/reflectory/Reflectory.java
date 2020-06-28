package vision.voltsofdoom.coresystem.loading.reflectory;

import java.util.ArrayList;

import org.reflections.Reflections;
import org.reflections.scanners.Scanner;

import vision.voltsofdoom.coresystem.loading.VODClassLoader;

/**
 * A place for the Volts of Doom monks to search through jar files in peace.
 * 
 * @author GenElectrovise
 *
 */
public class Reflectory {

	private ClassLoader classLoader;
	private Scanner[] scanners;
	private Reflections reflections;

	/**
	 * Prevents usage of the blank constructor. <b>Use
	 * {@link Reflectory.Builder}</b>
	 */
	private Reflectory() {

	}

	/**
	 * Creates this {@link Reflectory}'s instance of {@link Reflections}, which
	 * indexes the classes given in its {@link ClassLoader}
	 * 
	 * @return this {@link Reflectory}
	 */
	public Reflectory index() {
		reflections = new Reflections(classLoader, scanners);
		return this;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public Scanner[] getScanners() {
		return scanners;
	}
	
	public Reflections getReflections() {
		return reflections;
	}

	/**
	 * Constructs a {@link Reflectory} using chained methods. Retrieve the completed
	 * {@link Reflectory} using {@link #build()}
	 * 
	 * @author GenElectrovise
	 *
	 */
	public static class Builder {

		private Reflectory reflectory;
		private ArrayList<Scanner> scanners = new ArrayList<Scanner>();

		public Builder() {
			this.reflectory = new Reflectory();
		}

		/**
		 * Gives the provided {@link ClassLoader} to the constructing
		 * {@link Reflectory}, which will be used to index the correct tree of jars.
		 * 
		 * @param classLoader
		 * @return This {@link Builder}
		 */
		public Builder withClassLoader(VODClassLoader classLoader) {
			reflectory.classLoader = classLoader;
			return this;
		}

		/**
		 * Applies a {@link Scanner} to the constructing {@link Reflectory}, which will
		 * be used to retrieve attributes from an indexed jar.
		 * 
		 * @param scanner
		 * @return This {@link Builder}
		 */
		public Builder withScanner(Scanner scanner) {
			scanners.add(scanner);
			return this;
		}

		public Reflectory build() {
			reflectory.scanners = scanners.toArray(new Scanner[scanners.size()]);
			reflectory.index();
			return reflectory;
		}
	}
}