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
import vision.voltsofdoom.coresystem.universal.registry.TypeRegistries;
import vision.voltsofdoom.coresystem.universal.resource.json.GsonHandler;
import vision.voltsofdoom.coresystem.universal.resource.zip.ZipFileReader;
import vision.voltsofdoom.coresystem.universal.util.ExitCodes;
import vision.voltsofdoom.coresystem.universal.util.Reference;
import vision.voltsofdoom.zapbyte.event.RegistryEvent;
import vision.voltsofdoom.zapbyte.event.Stowaway;
import vision.voltsofdoom.zapbyte.log.Loggers;
import vision.voltsofdoom.zapbyte.main.ZapByteReference;

/**
 * Generates a list of {@link Adventure}s to register.
 * 
 * @author GenElectrovise
 *
 */
public class AdventureLoader {

  /**
   * Constructs the Adventure objects for a list of JSON files during the
   * {@link RegistryEvent.GenerateAdventuresEvent}. Actually delegates all of the hard work to the
   * Adventure class, which further delegates the work to its contained classes (eg
   * {@link LevelMeta}, {@link Level} and {@link LevelMap})<br>
   * <br>
   * <b>Notes on ZIP files:</b><br>
   * <ul>
   * <li>Zip files treat all of their contents ({@link ZipEntry}s) as if they were in the same
   * folder, just with different names.
   * <li>i.e. A ZIP containing a file called "file.json", and a folder called "folder_1", containing
   * a file called "data.txt", would have two entries: "file.json", and "folder_1/data.txt".
   * <li>This differs from the typical {@link File} in that it works on a baser layer, and means
   * that, to check the locations of files, you have to use something along the lines of
   * {@link String#startsWith(String)}.
   * </ul>
   * 
   * @param zip
   * 
   * @param adventureFiles The list of JSON files.
   * @throws FileNotFoundException If an error occurs finding a listed {@link Adventure} file.
   * @see Adventure
   * @see Level
   * @see LevelMeta
   * @see LevelMeta
   */
  @Stowaway
  private static void generateAdventures(GenerateAdventuresEvent event)
      throws FileNotFoundException {
    List<ZipFile> adventureZips = new ArrayList<ZipFile>();
    File adventureFolder = new File(Reference.ADVENTURE);
    if (!adventureFolder.exists() || !adventureFolder.isDirectory()) {
      adventureFolder.mkdir();
      throw new FileNotFoundException("Adventure folder in the located Volts of Doom directory"
          + ZapByteReference.getApplicationRoaming() + " cannot be located (" + Reference.ADVENTURE
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
        Throwable t = new Throwable(
            "Error reading ZIP file " + zip.getName() + " >> " + e.getMessage(), e.getCause());
        t.printStackTrace();
      }
    });
  }

  /**
   * Uses variants on:<br>
   * <code>
   * ZipFileReader reader = new ZipFileReader(zip); <br>
   * Class.fromJson(GsonHandler.GSON.fromJson(ZipFileReader.asJsonReader(reader.getStream("path")), JsonObject.class))
   * </code> <br>
   * to load parts of {@link Adventure}s from ZIP files.
   * 
   * @param zip The ZIP file to load from.
   * @throws IOException
   */
  private static void fromZip(ZipFile zip) throws IOException {

    try {

      ZipFileReader reader = new ZipFileReader(zip);
      List<ZipEntry> entries = new ArrayList<ZipEntry>();

      Enumeration<? extends ZipEntry> entriesS = zip.entries();
      while (entriesS.hasMoreElements()) {
        ZipEntry zipEntry = (ZipEntry) entriesS.nextElement();
        entries.add(zipEntry);
      }

      Adventure.Builder adventureBuilder = new Adventure.Builder();

      // AdventureConfiguration
      AdventureConfiguration adventureConfiguration =
          AdventureConfiguration.fromJson(GsonHandler.GSON.fromJson(
              ZipFileReader.asJsonReader(reader.getStream("adventure.json")), JsonObject.class));
      adventureBuilder.withConfiguration(adventureConfiguration);

      // Sheets
      Map<ZipEntry, ISheetType> entryMap = new HashMap<ZipEntry, ISheetType>();
      for (ZipEntry zipEntry : entries) {

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

            entryMap.put(zipEntry, type);
          }
        }
      }

      for (ZipEntry zipEntry : entryMap.keySet()) {
        Sheet sheet = Sheet.fromJson(GsonHandler.GSON.fromJson(
            ZipFileReader.asJsonReader(reader.getStream(zipEntry.getName())), JsonObject.class));
        adventureBuilder.withSheet(sheet, entryMap.get(zipEntry));
      }

      // Levels

      List<String> levelEntries = new ArrayList<String>();
      for (ZipEntry zipEntry : entries) {

        String name = zipEntry.getName();
        String[] split = name.split("/");

        if (split[0].contentEquals("levels")) {
          levelEntries.add(name);
        }
      }

      List<String> levelFolderNames = new ArrayList<String>();
      for (String name : levelEntries) {
        String[] split = name.split("/");

        if (!levelFolderNames.contains(split[1])) {
          levelFolderNames.add(split[1]);
        }
      }

      for (String levelFolderName : levelFolderNames) {
        String base = "levels/" + levelFolderName + "/";

        // LevelConfiguration
        LevelConfiguration levelConfiguration = LevelConfiguration.fromJson(GsonHandler.GSON
            .fromJson(ZipFileReader.asJsonReader(reader.getStream(base + "level.json")),
                JsonObject.class));
        adventureBuilder.withLevelConfiguration(levelConfiguration);
      }

      // Register

      Adventure adventure = adventureBuilder.build();

      TypeRegistries.ADVENTURES.register(adventure.getIdentifier(), () -> adventure);

      // Cannot test level generation here as registry not yet loaded.

      Loggers.ZAPBYTE_LOADING_RESOURCE.info(
          "Loaded Adventure by name: " + adventure.getConfiguration().getIdentifier().stringify());

    } catch (Exception e) {
      e.printStackTrace();

      Loggers.ZAPBYTE_LOADING_RESOURCE.severe("EXCEPTION LOADING ADVENTURES!");
      Loggers.ZAPBYTE_LOADING_RESOURCE.severe(e.toString());
      System.exit(ExitCodes.ADVENTURE_LOADING_FAILIURE);
    }
  }

  @SuppressWarnings("unused")
  private static void generatePreMadeObjects() {

    try {
      Gson gson = GsonHandler.GSON;

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
      RawTileMap tm = RawTileMap.fromJson(gson.fromJson(
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
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
