package vision.voltsofdoom.coresystem.play.adventure;

import java.io.File;
import java.util.ArrayList;

import vision.voltsofdoom.coresystem.universal.log.VODLog4J;
import vision.voltsofdoom.coresystem.loading.resource.external.AdventureFinder;
import vision.voltsofdoom.coresystem.play.adventure.levelcontainer.LevelContainer;
import vision.voltsofdoom.coresystem.play.adventure.levelcontainer.LevelMeta;

public class AdventureLoader {
	private AdventureFinder finder = new AdventureFinder();

	private ArrayList<Adventure> adventures = new ArrayList<Adventure>();

	public AdventureLoader init() {
		adventures = constructAdventures(finder.getAdventures());

		for (Adventure adv : adventures) {
			for (LevelContainer cont : adv.getLevels()) {
				VODLog4J.LOGGER.debug(cont.getMeta().toString());
				VODLog4J.LOGGER.debug(cont.getMap().toString());
			}
		}

		return this;
	}

	/**
	 * Constructs the Adventure objects for a list of JSON files. Actually delegates
	 * all of the hard work to the Adventure class, which further delegates the work
	 * to its contained classes (eg LevelMeta, LevelContainer and LevelMap)
	 * 
	 * @param adventureFiles The list of JSON files.
	 * @return An ArrayList of Adventures.
	 * @see Adventure
	 * @see LevelContainer
	 * @see LevelMeta
	 * @see LevelMeta
	 */
	private ArrayList<Adventure> constructAdventures(ArrayList<File> adventureFiles) {
		ArrayList<Adventure> adventures = new ArrayList<Adventure>();

		for (File file : adventureFiles) {
			adventures.add(new Adventure(file));
		}

		return adventures;
	}

	public ArrayList<Adventure> getAdventures() {
		return adventures;
	}

	public AdventureFinder getFinder() {
		return finder;
	}
}