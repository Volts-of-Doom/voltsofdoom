package genelectrovise.voltsofdoom_coresystem.loading;

import genelectrovise.voltsofdoom_coresystem.loading.resource.search.JarFinder;
import genelectrovise.voltsofdoom_coresystem.loading.resource.search.JarScanner;
import genelectrovise.voltsofdoom_coresystem.universal.log.VODLog4J;

public class LoaderMissionControl {
	private JarFinder finder;
	private JarScanner scanner;
	private VODClassLoader loader;
	private LoaderReference lref = new LoaderReference();

	/**
	 * <code>public static void main(String[] args) {
		LoaderMissionControl control = new LoaderMissionControl(new JarFinder(), new JarScanner());
		control.init();
	}</code>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LoaderMissionControl control = new LoaderMissionControl(new JarFinder(), new JarScanner());
		control.init();
	}

	/**
	 * What is says on the tin -- controls the jar loading process. Please give new instances of the three required
	 * classes.
	 * 
	 * @param finder
	 * @param scanner
	 * @param loader
	 */
	public LoaderMissionControl(JarFinder finder, JarScanner scanner) {
		this.finder = finder;
		this.scanner = scanner;
	}

	// Methods
	public LoaderMissionControl init() {
		// Gets a list of the jars / javas in the mods directory
		lref.setJarsInDir(finder.getListOfJarsInDirectory());

		// Creates a URLClassLoader for those URLs
		loader = new VODClassLoader(finder.fileArrToUrls(lref.getJarsInDir()));
		lref.setModsClassLoader(loader);

		// Finds @Mod Annotations in those loaded jars' classes
		lref.setMods(scanner.findModAnnotations(loader, lref));

		return this;
	}
	
	/**
	 * The plan of action! The master plan! This sysout has it all (for the mod loading) !
	 */
	public static void planOfAction() {
		VODLog4J.LOGGER.info(
				"Plan of action! \n1)Find jars in dir! \n2)Load jars in dir with ClassLoader 3)\nFind @Mod annotations 4)\n Get GenericRegistrys from VOD 5)\nGet GenericRegistrys from all mods 6)\nLoad GenericRegistries in correct order");

	}

	// Get and set

	/**
	 * @return the finder
	 */
	public JarFinder getFinder() {
		return finder;
	}

	/**
	 * @return the scanner
	 */
	public JarScanner getScanner() {
		return scanner;
	}

	/**
	 * @return the loader
	 */
	public VODClassLoader getLoader() {
		return loader;
	}

	/**
	 * @return The instance of LoaderReference
	 */
	public LoaderReference getLref() {
		return lref;
	}
	
	
}
