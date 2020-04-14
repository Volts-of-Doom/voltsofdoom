package genelectrovise.voltsofdoom_coresystem.loader;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import genelectrovise.voltsofdoom_coresystem.log.VODLog4J;
import genelectrovise.voltsofdoom_coresystem.util.Reference;

/**
 * Loads the <b>.JAR</b> files in the <b>Reference.MODS_DIRECTORY</b>
 * 
 * @author adam_
 *
 */
public class JarFinder {

	/**
	 * @return A list of <b>.JAR</b> files in the <b>Reference.MODS_DIRECTORY</b>
	 */
	public ArrayList<File> getListOfJarsInDirectory() {
		File modDir = new File(Reference.MODS_DIRECTORY);
		ArrayList<File> out = new ArrayList<File>();

		if (modDir.isDirectory()) {
			for (final File entry : modDir.listFiles()) {
				if (isJar(entry)) {
					out.add(entry);
					VODLog4J.LOGGER.info("Added .JAR file : " + entry.getName());
				} else {
					VODLog4J.LOGGER.info("File '" + entry.getName() + "' was not a .JAR so was not added.");
				}
			}
		} else {
			VODLog4J.LOGGER.info("Mods directory is not a valid folder!");
		}

		return out;
	}

	/**
	 * 
	 * @param entry A file to query
	 * @return Whether the file has a <b>.JAR</b> extension
	 */
	private boolean isJar(File entry) {
		return entry.getName().endsWith(".jar") || entry.getName().endsWith(".java") ? true : false;
	}

	/**
	 * Converts the filesIn to a URL[]
	 * 
	 * @param filesIn The ArrayList{File} to convert
	 * @return An array of the ArrayList{File} put in
	 */
	public URL[] fileArrToUrls(ArrayList<File> filesIn) {
		try {
			Vector<URL> urls = new Vector<URL>();
			for (File file : filesIn) {
				urls.add(file.toURI().toURL());
			}
			return urls.toArray(new URL[urls.size()]);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
