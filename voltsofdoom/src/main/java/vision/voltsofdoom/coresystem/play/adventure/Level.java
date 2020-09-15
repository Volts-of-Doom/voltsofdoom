package vision.voltsofdoom.coresystem.play.adventure;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import vision.voltsofdoom.coresystem.universal.log.Loggers;
import vision.voltsofdoom.coresystem.universal.resource.zip.ZipFileReader;

/**
 * A uhhhhh... {@link Level} in an {@link Adventure}...
 * 
 * @author GenElectrovise
 *
 */
public class Level {

	private LevelConfiguration levelConfiguration;
	private EntityMap entityMap;
	private TileMap tileMap;
	public ArrayList<Puzzle> puzzles = new ArrayList<Puzzle>();
	public ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>();

	private Level() {
	}

	public LevelConfiguration getLevelConfiguration() {
		return levelConfiguration;
	}

	public EntityMap getEntityMap() {
		return entityMap;
	}

	public TileMap getTileMap() {
		return tileMap;
	}

	@Override
	public String toString() {

		Loggers.CORESYSTEM_LOADING.severe("Levels not loaded yet, either at loading or runtime!");

		StringBuilder builder = new StringBuilder();
		builder.append("Level{");

		builder.append(levelConfiguration.toString());

		builder.append("}");
		return builder.toString();
	}

	public static Level fromZip(Adventure parent, ZipFile zip, LevelConfiguration config) {
		Level.Builder builder = new Builder();

		try {
			Gson gson = new Gson();
			ZipFileReader reader = new ZipFileReader(zip);
			String base = "levels/" + config.getIdentifier().getEntry() + "/";

			// Configuration
			builder.withConfiguration(config);

			// TileMap
			builder.withTileMap(TileMap.fromJson(gson
					.fromJson(ZipFileReader.asJsonReader(reader.getStream(base + "tiles.json")), JsonObject.class)));

			// EntityMap
			builder.withEntityMap(EntityMap.fromJson(gson
					.fromJson(ZipFileReader.asJsonReader(reader.getStream(base + "entities.json")), JsonObject.class)));

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
				builder.withBehaviour(Behaviour.fromJson(
						gson.fromJson(ZipFileReader.asJsonReader(reader.getStream(behaviourName)), JsonObject.class)));
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
				builder.withPuzzle(Puzzle.fromJson(
						gson.fromJson(ZipFileReader.asJsonReader(reader.getStream(puzzleName)), JsonObject.class)));
			}

		} catch (Exception e) {
			e.printStackTrace();
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

		public void withTileMap(TileMap tileMap) {
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
