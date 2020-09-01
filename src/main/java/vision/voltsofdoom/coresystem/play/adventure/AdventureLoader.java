package vision.voltsofdoom.coresystem.play.adventure;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import vision.voltsofdoom.coresystem.play.adventure.Sheet.ISheetType;
import vision.voltsofdoom.coresystem.universal.band_wagon.Stowaway;
import vision.voltsofdoom.coresystem.universal.event.RegistryEvent;
import vision.voltsofdoom.coresystem.universal.event.RegistryEvent.GenerateAdventuresEvent;
import vision.voltsofdoom.coresystem.universal.resource.zip.ZipFileReader;
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

			// generatePreMadeObjects();
		} catch (Exception e) {
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
	 * @throws FileNotFoundException If an error occurs finding a listed
	 *                               {@link Adventure} file.
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
			adventureFolder.mkdir();
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
			try {
				fromZip(zip);
			} catch (IOException e) {
				Throwable t = new Throwable("Error reading ZIP file " + zip.getName() + " >> " + e.getMessage(),
						e.getCause());
				t.printStackTrace();
			}
		});
	}

	/**
	 * Uses variants on:<br>
	 * <code>
	 * ZipFileReader reader = new ZipFileReader(zip); <br>
	 * Class.fromJson(new Gson().fromJson(ZipFileReader.asJsonReader(reader.getStream("path")), JsonObject.class))
	 * </code> <br>
	 * to load parts of {@link Adventure}s from ZIP files.
	 * 
	 * @param zip The ZIP file to load from.
	 * @throws IOException
	 */
	private static void fromZip(ZipFile zip) throws IOException {
		ZipFileReader reader = new ZipFileReader(zip);
		Gson gson = new Gson();

		Adventure.Builder builder = new Adventure.Builder();

		// AdventureConfiguration
		AdventureConfiguration adventureConfiguration = AdventureConfiguration.fromJson(
				gson.fromJson(ZipFileReader.asJsonReader(reader.getStream("adventure.json")), JsonObject.class));
		builder.withConfiguration(adventureConfiguration);

		// Sheets
		Map<ZipEntry, ISheetType> entries = new HashMap<ZipEntry, ISheetType>();
		Enumeration<? extends ZipEntry> e = zip.entries();
		while (e.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) e.nextElement();

			// IF in "sheets" directory && IF in the 2nd directory down && IF file extension
			// is ".json"
			String name = zipEntry.getName();
			if (name.startsWith("sheets/")) {
				if (name.endsWith(".json")) {

					ISheetType type = ISheetType.EMPTY;
					for (ISheetType sheetType : Sheet.ISheetType.types) {
						if (name.contains(sheetType.folderName())) {
							type = sheetType;
						}
					}

					entries.put(zipEntry, type);
				}
			}
		}

		for (ZipEntry zipEntry : entries.keySet()) {
			Sheet sheet = Sheet.fromJson(
					gson.fromJson(ZipFileReader.asJsonReader(reader.getStream(zipEntry.getName())), JsonObject.class));
			builder.withSheet(sheet, entries.get(zipEntry));
		}

		System.out.println(builder.build());

		// Levels

	}

	@SuppressWarnings("unused")
	private static void generatePreMadeObjects() {
		Gson gson = new Gson();

		// Adv config
		AdventureConfiguration ac = AdventureConfiguration.fromJson(gson.fromJson(
				"{\"identifier\":{\"domain\":\"coresystem\",\"entry\":\"example_adventure_1\"},\"displayName\":\"Example Adventure\",\"description\":\"An example adventure\",\"levelNames\":[\"example_level_1\",\"example_level_2\"]}",
				JsonObject.class));

		// Entity map
		EntityMap em = EntityMap.fromJson(gson.fromJson(
				"{\"key\":[{\"key\":\"e\",\"identifier\":{\"domain\":\"coresystem\",\"entry\":\"example_entity\"},\"data\":{}},{\"key\":\"s\",\"sheet\":{\"identifier\":{\"domain\":\"coresystem\",\"entry\":\"slime\"}}}],\"map\":[{\"key\":\"e\",\"coordinate\":{\"x\":2,\"y\":3},\"data\":{}},{\"key\":\"s\",\"coordinate\":{\"x\":2,\"y\":3}}]}",
				JsonObject.class));

		// Level config
		LevelConfiguration lc = LevelConfiguration.fromJson(gson.fromJson(
				"{\"identifier\":{\"domain\":\"coresystem\",\"entry\":\"example_level_1\"},\"displayName\":\"Example Level 1\",\"description\":\"A wonderful example of a level!\"}",
				JsonObject.class));

		// Tile map
		TileMap tm = TileMap.fromJson(gson.fromJson(
				"{\"key\":[{\"key\":\"e\",\"identifier\":{\"domain\":\"coresystem\",\"entry\":\"example_tile\"},\"data\":{}},{\"key\":\"a\",\"sheet\":{\"identifier\":{\"domain\":\"coresystem\",\"entry\":\"cobble\"}}}],\"map\":[[\"e\",\"e\",\"e\",\"e\"],[\"e\",\"a\",\"e\",\"a\"],[\"a\",\"e\",\"a\",\"e\"],[\"a\",\"a\",\"a\",\"a\"]]}",
				JsonObject.class));

		// Tile Sheet
		Sheet ts = Sheet.fromJson(gson.fromJson(
				"{\"identifier\":{\"domain\":\"coresystem\",\"entry\":\"cobble\"},\"data\":{\"tags\":[{\"key\":\"key_1\",\"value\":\"value_1\"},{\"key\":\"key_2\",\"value\":\"value_2\"}]}}",
				JsonObject.class));

		// Entity Sheet
		Sheet es = Sheet.fromJson(gson.fromJson(
				"{\"identifier\":{\"domain\":\"coresystem\",\"entry\":\"slime\"},\"data\":{\"tags\":[{\"key\":\"key_1\",\"value\":\"value_1\"},{\"key\":\"key_2\",\"value\":\"value_2\"}]}}",
				JsonObject.class));

		// Break line
		System.out.println();
	}
}
