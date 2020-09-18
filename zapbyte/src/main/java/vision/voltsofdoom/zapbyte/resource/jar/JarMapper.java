package vision.voltsofdoom.zapbyte.resource.jar;

import java.io.File;
import java.util.ArrayList;

import vision.voltsofdoom.zapbyte.misc.ZapByteReference;

/**
 * Maps out the locations of jar files in the mods directory.
 * 
 * @author GenElectrovise
 *
 */
public class JarMapper {

	/**
	 * Finds all {@link File}s ending in ".jar" in the {@link Reference#ROAMING}
	 * directory.
	 * 
	 * @return
	 */
	public static ArrayList<File> find() {
		ArrayList<File> out = new ArrayList<File>();

		File modsDir = new File(ZapByteReference.getModsDir());
		for (File file : modsDir.listFiles()) {
			if (file.getName().endsWith(".jar")) {
				out.add(file);
			}
		}

		return out;
	}
}
