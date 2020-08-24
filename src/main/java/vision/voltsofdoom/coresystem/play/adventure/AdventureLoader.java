package vision.voltsofdoom.coresystem.play.adventure;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import vision.voltsofdoom.coresystem.universal.band_wagon.Stowaway;
import vision.voltsofdoom.coresystem.universal.event.RegistryEvent;
import vision.voltsofdoom.coresystem.universal.event.RegistryEvent.GenerateAdventuresEvent;
import vision.voltsofdoom.coresystem.universal.util.Reference;

/**
 * Generates a list of {@link Adventure}s to register.
 * 
 * @author GenElectrovise
 *
 */
public class AdventureLoader {

	public static void main(String[] args) {
		try {
			generateAdventures(new GenerateAdventuresEvent());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructs the Adventure objects for a list of JSON files during the
	 * {@link RegistryEvent.GenerateAdventuresEvent}. Actually delegates all of the
	 * hard work to the Adventure class, which further delegates the work to its
	 * contained classes (eg {@link LevelMeta}, {@link Level} and
	 * {@link LevelMap})<br>
	 * <br>
	 * <b>Notes on ZIP files:</b><br>
	 * <ul>
	 * <li>Zip files treat all of their contents ({@link ZipEntry}s) as if they were
	 * in the same folder, just with different names.
	 * <li>i.e. A ZIP containing a file called "file.json", and a folder called
	 * "folder_1", containing a file called "data.txt", would have two entries:
	 * "file.json", and "folder_1/data.txt".
	 * <li>This differs from the typical {@link File} in that it works on a baser
	 * layer, and means that, to check the locations of files, you have to use
	 * something along the lines of {@link String#startsWith(String)}.
	 * </ul>
	 * 
	 * @param zip
	 * 
	 * @param adventureFiles The list of JSON files.
	 * @return An ArrayList of Adventures.
	 * @throws FileNotFoundException
	 * @see Adventure
	 * @see Level
	 * @see LevelMeta
	 * @see LevelMeta
	 */
	@Stowaway
	private static void generateAdventures(RegistryEvent.GenerateAdventuresEvent event) throws FileNotFoundException {
		List<ZipFile> adventureZips = new ArrayList<ZipFile>();
		File adventureFolder = new File(Reference.ADVENTURE);
		if (!adventureFolder.exists() || !adventureFolder.isDirectory()) {
			throw new FileNotFoundException("Adventure folder in the located Volts of Doom directory"
					+ Reference.ROAMING + " cannot be located (" + Reference.ADVENTURE
					+ ")! This is an error! Program will terminate.");
		}

		// Add ZipFiles to the list of ZipFiles.
		for (File file : adventureFolder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".zip");
			}
		})) {
			try {
				ZipFile zip = new ZipFile(file);
				adventureZips.add(zip);
			} catch (ZipException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// For each of the ZIPs found
		adventureZips.forEach((zip) -> {
			
		});
	}
}
