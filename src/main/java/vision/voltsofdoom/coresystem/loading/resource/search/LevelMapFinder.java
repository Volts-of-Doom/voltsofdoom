package vision.voltsofdoom.coresystem.loading.resource.search;

import vision.voltsofdoom.coresystem.universal.log.VODLog4J;
import vision.voltsofdoom.coresystem.universal.util.Reference;
import vision.voltsofdoom.coresystem.play.adventure.Adventure;

import java.io.File;
import java.util.ArrayList;

public class LevelMapFinder {
	private Adventure adventure;
	private StringBuilder nameBuilder;
	private ArrayList<File> levels = new ArrayList<File>();

	public LevelMapFinder(Adventure adventure) {
		this.adventure = adventure;
		nameBuilder = new StringBuilder().append(Reference.ROAMING).append(File.separator + "resources" + File.separator + "levelmap" + File.separator)
				.append(adventure.getModid()).append(File.separator + this.adventure.getRegistryname() + File.separator);

		levels = findLevelFiles(new File(nameBuilder.toString()));
		VODLog4J.LOGGER.debug("Found levels : " + levels.toString());
	}

	public ArrayList<File> findLevelFiles(File startFile) {
		
		VODLog4J.LOGGER.debug("Starting search in : "+startFile.getAbsolutePath());
		
		ArrayList<File> adventures = new ArrayList<File>();

		for (File f : startFile.listFiles()) {
			
			if (f.isFile() && f.getName().endsWith(".json")) {
				adventures.add(f);
			} else if (f.isDirectory()) {
				adventures.addAll(findLevelFiles(f.getAbsoluteFile()));
			}
		}

		return adventures;
	}

	public Adventure getAdventure() {
		return adventure;
	}

	public StringBuilder getNameBuilder() {
		return nameBuilder;
	}

	public ArrayList<File> getLevels() {
		return levels;
	}
}
