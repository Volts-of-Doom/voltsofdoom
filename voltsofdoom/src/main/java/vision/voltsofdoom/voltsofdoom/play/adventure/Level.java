package vision.voltsofdoom.voltsofdoom.play.adventure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import vision.voltsofdoom.voltsofdoom.universal.main.VoltsOfDoom;
import vision.voltsofdoom.voltsofdoom.universal.resource.json.GsonHandler;
import vision.voltsofdoom.voltsofdoom.universal.resource.zip.ZipFileReader;

/**
 * A {@link Level} in an {@link Adventure}... <br>
 * <br>
 * <code>//Test Level loading<br>
 * Registry.getTyped(RegistryTypes.ADVENTURES).getEntries().forEach((rl, advSuppl) -> { <br>Adventure
 * adv = (Adventure) advSuppl.get(); <br>adv.getLevels().forEach((level) -> {<br>
 * level.getTileMap().generateTwoDimensionalListOfTileObjects(); <br>}); <br>}); </code>
 * 
 * @author GenElectrovise
 *
 */
public class Level {

  private LevelConfiguration levelConfiguration;
  private EntityMap entityMap;
  private RawTileMap tileMap;
  public ArrayList<Puzzle> puzzles = new ArrayList<Puzzle>();
  public ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>();

  private Level() {}

  public LevelConfiguration getLevelConfiguration() {
    return levelConfiguration;
  }

  public EntityMap getEntityMap() {
    return entityMap;
  }

  public RawTileMap getRawTileMap() {
    return tileMap;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("Level{");

    builder.append(levelConfiguration.toString());

    builder.append("}");
    return builder.toString();
  }

  /**
   * Generates a new {@link Level} from a {@link ZipFile} objects read from .zip file on the system.
   * 
   * @param parent The parent {@link Adventure} of this level. Used to access adventure resources in
   *        the adventure .zip file.
   * @param zip The {@link ZipFile} to read from.
   * @param config The {@link LevelConfiguration} of the {@link Level} you want to create. This is
   *        normally gained from the parent {@link Adventure}.
   * @return A built {@link Level}.
   */
  public static Level fromZip(Adventure parent, ZipFile zip, LevelConfiguration config) {
    Level.Builder builder = new Builder();

    try {
      Gson gson = GsonHandler.GSON;
      ZipFileReader reader = new ZipFileReader(zip);
      String base = "levels/" + config.getIdentifier().getEntry() + "/";

      // Configuration
      builder.withConfiguration(config);

      // TileMap
      builder.withTileMap(RawTileMap.fromJson(gson.fromJson(
          ZipFileReader.asJsonReader(reader.getStream(base + "tiles.json")), JsonObject.class)));

      // EntityMap
      builder.withEntityMap(EntityMap.fromJson(gson.fromJson(
          ZipFileReader.asJsonReader(reader.getStream(base + "entities.json")), JsonObject.class)));

      // Behaviours
      ArrayList<String> behaviourFiles = new ArrayList<String>();
      @SuppressWarnings("unchecked")
      Enumeration<ZipEntry> behaviourEntries = (Enumeration<ZipEntry>) zip.entries();
      while (behaviourEntries.hasMoreElements()) {
        ZipEntry type = (ZipEntry) behaviourEntries.nextElement();
        if (type.getName().startsWith(base + "behaviours") && type.getName().endsWith(".json")) {
          behaviourFiles.add(type.getName());
        }
      }
      for (String behaviourName : behaviourFiles) {
        builder.withBehaviour(Behaviour.fromJson(gson.fromJson(
            ZipFileReader.asJsonReader(reader.getStream(behaviourName)), JsonObject.class)));
      }

      // Puzzles
      ArrayList<String> puzzleFiles = new ArrayList<String>();
      @SuppressWarnings("unchecked")
      Enumeration<ZipEntry> puzzleEntries = (Enumeration<ZipEntry>) zip.entries();
      while (puzzleEntries.hasMoreElements()) {
        ZipEntry type = (ZipEntry) puzzleEntries.nextElement();
        if (type.getName().startsWith(base + "puzzles") && type.getName().endsWith(".json")) {
          puzzleFiles.add(type.getName());
        }
      }
      for (String puzzleName : puzzleFiles) {
        builder.withPuzzle(Puzzle.fromJson(gson
            .fromJson(ZipFileReader.asJsonReader(reader.getStream(puzzleName)), JsonObject.class)));
      }

    } catch (IOException e) {
      VoltsOfDoom.getInstance().getApplicationLogger().error("System IO error loading Level : " + config.getIdentifier());
      e.printStackTrace();
    } catch (NullPointerException n) {
      VoltsOfDoom.getInstance().getApplicationLogger().error("NullPointerException thrown during Level loading.");
      n.printStackTrace();
    }

    return builder.build();
  }

  public static class Builder {

    Level level = new Level();

    public void withConfiguration(LevelConfiguration levelConfiguration) {
      level.levelConfiguration = levelConfiguration;
    }

    public void withEntityMap(EntityMap entityMap) {
      level.entityMap = entityMap;
    }

    public void withTileMap(RawTileMap tileMap) {
      level.tileMap = tileMap;
    }

    public void withBehaviour(Behaviour behaviour) {
      level.behaviours.add(behaviour);
    }

    public void withPuzzle(Puzzle puzzle) {
      level.puzzles.add(puzzle);
    }

    public Level build() {
      return level;
    }

  }
}
