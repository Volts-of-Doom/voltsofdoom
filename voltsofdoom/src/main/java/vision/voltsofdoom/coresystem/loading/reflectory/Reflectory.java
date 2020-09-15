package vision.voltsofdoom.coresystem.loading.reflectory;

import java.util.ArrayList;

import org.reflections.Reflections;
import org.reflections.scanners.Scanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

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
	public String visibleName;

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
		ConfigurationBuilder configBuilder = new ConfigurationBuilder();
		configBuilder.setUrls(ClasspathHelper.forClassLoader(classLoader));
		configBuilder.addClassLoader(classLoader);
		configBuilder.addScanners(scanners);

		reflections = new Reflections(configBuilder);
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
		public Builder withClassLoader(ClassLoader classLoader) {
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

		public Builder withVisibleName(String name) {
			reflectory.visibleName = name;
			return this;
		}
	}
}
