package genelectrovise.voltsofdoom_coresystem.adventure.levelcontainer;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import genelectrovise.voltsofdoom_coresystem.entity.Coordinate;
import genelectrovise.voltsofdoom_coresystem.entity.Entity;
import genelectrovise.voltsofdoom_coresystem.log.VODLog4J;
import genelectrovise.voltsofdoom_coresystem.resource.json.VODJsonReader;
import genelectrovise.voltsofdoom_coresystem.tile.Tile;

/**
 * A map of a level. Held in a LevelContainer.
 * 
 * @author adam_
 * 
 * @see LevelContainer
 *
 */
public class LevelMap {
	private LevelMeta meta;
	private VODJsonReader reader;
	private ArrayList<ArrayList<Tile>> tileMap = new ArrayList<ArrayList<Tile>>();
	private HashMap<Integer, String> tileKeys = new HashMap<Integer, String>();
	private HashMap<Entity, Coordinate> entityMap = new HashMap<Entity, Coordinate>();
	private HashMap<Integer, String> entityKeys = new HashMap<Integer, String>();

	public LevelMap(VODJsonReader reader, LevelMeta meta) {
		this.meta = meta;
		this.reader = reader;

		// Tiles
		this.tileKeys = constructTileKeys();
		this.tileMap = constructTileMap();

		// Entities
		this.entityKeys = constructEntityKeys();
		this.entityMap = constructEntityMap();

		VODLog4J.LOGGER.info("Done!");
	}

	/**
	 * @return The LevelMeta object for this LevelMap (given by its LevelContainer)
	 */
	public LevelMeta getMeta() {
		return meta;
	}

	public VODJsonReader getReader() {
		return reader;
	}

	public HashMap<Integer, String> getEntityKeys() {
		return entityKeys;
	}

	public HashMap<Entity, Coordinate> getEntityMap() {
		return entityMap;
	}

	public HashMap<Integer, String> getTileKeys() {
		return tileKeys;
	}

	public ArrayList<ArrayList<Tile>> getTileMap() {
		return tileMap;
	}

	/**
	 * Converts a JsonArray of Integer keys into an ArrayList(ArrayList(Tile)) which
	 * the game can use.
	 * 
	 * @return That ArrayList(ArrayList(Tile))
	 */
	public ArrayList<ArrayList<Tile>> constructTileMap() {
		JsonElement in = reader.elementFromKey("tiles");
		//// VODLog4J.LOGGER.debug("In : " + in);
		JsonArray jsonArray = in.getAsJsonArray();
		//// VODLog4J.LOGGER.debug("JsonArray : " + jsonArray);
		ArrayList<ArrayList<Tile>> out = new ArrayList<ArrayList<Tile>>();

		// Adds all elements in JsonArray from map json to an ArrayList
		ArrayList<JsonElement> componentJsonArrayElements = new ArrayList<JsonElement>();
		for (JsonElement elem : jsonArray) {
			componentJsonArrayElements.add(elem);
		}
		//// VODLog4J.LOGGER.debug("JsonElements : " + componentJsonArrayElements);

		// For each of those added JsonArrays:
		for (JsonElement elem : componentJsonArrayElements) {
			JsonArray arr = elem.getAsJsonArray();

			// Makes an ArrayList of Tiles to store created tiles before being added to the
			// "out" ArrayList
			ArrayList<Tile> miniTileArray = new ArrayList<Tile>();
			// For each item in the current JsonArray:
			for (JsonElement subElem : arr) {
				// Gets the integer value
				int i = subElem.getAsInt();

				// Creates a new instance of each tile using the newInstance().withProperties()
				// method //TODO Make sure withProperties() works!
				try {
					miniTileArray.add(new Tile(Tile.Archetype.fromString(tileKeys.get(i))));
					//// VODLog4J.LOGGER.debug("Arr : " + miniTileArray);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//// Adds the mini tile array to the "out" ArrayList
			out.add(miniTileArray);
		}

		//// VODLog4J.LOGGER.debug("Out : " + out);
		return out;
	}

	/**
	 * @return A HashMap of Integer keys and Tiles
	 */
	private HashMap<Integer, String> constructTileKeys() {
		HashMap<Integer, String> keys = new HashMap<Integer, String>();
		JsonArray arr = reader.elementFromKey("tilekey").getAsJsonArray();

		for (JsonElement elem : arr) {
			JsonObject obj = elem.getAsJsonObject();
			int id = obj.get("id").getAsInt();
			@SuppressWarnings("unused")
			String modid = obj.get("modid").getAsString();
			String registryname = obj.get("registryname").getAsString();

			try {
				keys.put(id, registryname);
			} catch (ClassCastException c) {
				VODLog4J.LOGGER.error(
						"You can't cast that! Either you tried to create a new Tile from a RegistryObject<? NOT A TILE>, or you tried to retrieve something that is not a Tile, and create a LevelMap from it! Please check your tilemap section of your JSON!");
				c.printStackTrace();

			}
		}

		return keys;
	}

	/**
	 * @return An ArrayList of Entities present in this level, with their starting
	 *         positions.
	 */
	public HashMap<Entity, Coordinate> constructEntityMap() {
		JsonArray arr = reader.elementFromKey("entities").getAsJsonArray();

		for (JsonElement elem : arr) {
			int type = elem.getAsJsonObject().get("type").getAsInt();
			int x = elem.getAsJsonObject().get("x").getAsInt();
			int y = elem.getAsJsonObject().get("y").getAsInt();

			try {
				entityMap.put(new Entity(Entity.Archetype.fromString(entityKeys.get(type))), new Coordinate(x, y));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return entityMap;
	}

	/**
	 * @return A HashMap of Integer keys and Entities.
	 */
	private HashMap<Integer, String> constructEntityKeys() {
		HashMap<Integer, String> keys = new HashMap<Integer, String>();
		JsonArray arr = reader.elementFromKey("entitykey").getAsJsonArray();

		for (JsonElement elem : arr) {
			JsonObject obj = elem.getAsJsonObject();
			int id = obj.get("id").getAsInt();
			@SuppressWarnings("unused")
			String modid = obj.get("modid").getAsString();
			String registryname = obj.get("registryname").getAsString();

			try {
				keys.put(id, registryname);
			} catch (ClassCastException c) {
				VODLog4J.LOGGER.error(
						"You can't cast that! Either you tried to create a new Tile from a RegistryObject<? NOT A TILE>, or you tried to retrieve something that is not a Tile, and create a LevelMap from it! Please check your tilemap section of your JSON!");
				c.printStackTrace();

			}
		}

		return keys;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LevelMap : [");

		// Tile Keys
		builder.append("Tile keys [");
		for (Integer key : tileKeys.keySet()) {
			builder.append("{" + key.toString() + "=" + tileKeys.get(key) + "}");
		}
		builder.append("],");

		// Entity Keys
		builder.append(" Entity keys [");
		for (Integer key : entityKeys.keySet()) {
			builder.append("{" + key.toString() + "=" + entityKeys.get(key) + "}");
		}
		builder.append("]");

		builder.append("]");
		return builder.toString();
	}

	/*
	 * @Override public String toString() { StringBuilder builder = new
	 * StringBuilder(); builder.append("LevelMap : [");
	 * 
	 * // Tile Keys builder.append("Tile keys ["); for (Integer key :
	 * tileKeys.keySet()) { builder.append("{" + key.toString() + "=" +
	 * tileKeys.get(key) + "}"); } builder.append("],");
	 * 
	 * // Tiles builder.append(" Tiles " + tileMap);
	 * 
	 * // Entity Keys builder.append(" Entity keys ["); for (Integer key :
	 * entityKeys.keySet()) { builder.append("{" + key.toString() + "=" +
	 * entityKeys.get(key) + "}"); } builder.append("],");
	 * 
	 * // Entities builder.append(" Entities ["); for (Entity e :
	 * entityMap.keySet()) { builder.append("{" + e.toString() + "=" +
	 * entityMap.get(e).toString() + "}"); } builder.append("]");
	 * 
	 * builder.append("]"); return builder.toString(); }
	 */
}
