package genelectrovise.voltsofdoom_coresystem.resource.finder;

import java.io.File;
import java.util.ArrayList;

import genelectrovise.voltsofdoom_coresystem.adventure.Adventure;
import genelectrovise.voltsofdoom_coresystem.log.VODLog4J;
import genelectrovise.voltsofdoom_coresystem.util.Reference;

public class LevelMapFinder {
	private Adventure adventure;
	private StringBuilder nameBuilder;
	private ArrayList<File> levels = new ArrayList<File>();

	public LevelMapFinder(Adventure adventure) {
		this.adventure = adventure;
		nameBuilder = new StringBuilder().append(Reference.ROAMING).append("\\resources\\levelmap\\")
				.append(adventure.getModid()).append("\\" + this.adventure.getRegistryname() + "\\");

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
