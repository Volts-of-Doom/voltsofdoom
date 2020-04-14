package genelectrovise.voltsofdoom_coresystem.resource.finder;

import java.io.File;
import java.util.ArrayList;

import genelectrovise.voltsofdoom_coresystem.log.VODLog4J;
import genelectrovise.voltsofdoom_coresystem.util.Reference;

/**
 * Searches for JSON files in the /adventures resources folder.
 * 
 * @author adam_
 *
 */
public class AdventureFinder {
	private ArrayList<File> adventures = new ArrayList<File>();

	public AdventureFinder() {
		adventures = findAdventures(new File(Reference.ROAMING + "\\resources\\adventure"));
		VODLog4J.LOGGER.debug(adventures.toString());
	}

	/**
	 * You guessed it -- finds the adventure JSON files I mentioned in the class
	 * javadoc!
	 * 
	 * @param startFile The file to start from. Will search every file below that
	 *                  and load every JSON file it finds.
	 * @return An ArrayList of found Files
	 */
	private ArrayList<File> findAdventures(File startFile) {
		ArrayList<File> adventures = new ArrayList<File>();

		for (File f : startFile.listFiles()) {
			if (f.isFile() && f.getName().endsWith(".json")) {
				adventures.add(f);
			} else if (f.isDirectory()) {
				adventures.addAll(findAdventures(f.getAbsoluteFile()));
			}
		}

		return adventures;
	}

	public ArrayList<File> getAdventures() {
		return adventures;
	}
}
