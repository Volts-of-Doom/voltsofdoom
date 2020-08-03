package vision.voltsofdoom.coresystem.universal.resource.jar;

import java.io.File;
import java.util.ArrayList;

import vision.voltsofdoom.coresystem.universal.util.Reference;

public class JarMapper {

	/**
	 * Finds all {@link File}s ending in ".jar" in the {@link Reference#ROAMING}
	 * directory.
	 * 
	 * @return
	 */
	public static ArrayList<File> find() {
		ArrayList<File> out = new ArrayList<File>();

		File modsDir = new File(Reference.MODS_DIRECTORY);
		for (File file : modsDir.listFiles()) {
			if (file.getName().endsWith(".jar")) {
				out.add(file);
			}
		}

		return out;
	}
}