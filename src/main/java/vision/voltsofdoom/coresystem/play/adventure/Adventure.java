package vision.voltsofdoom.coresystem.play.adventure;

import java.io.File;
import java.util.ArrayList;

import vision.voltsofdoom.coresystem.loading.resource.json.VODJsonReader;
import vision.voltsofdoom.coresystem.loading.resource.search.LevelMapFinder;
import vision.voltsofdoom.coresystem.play.adventure.levelcontainer.LevelContainer;

/**
 * Contains all of the LevelContainers for a new Adventure! Contains a lot of
 * metadata!
 * 
 * @author GenElectrovise
 *
 */
public class Adventure {
	private File json;
	private String registryname;
	private String displayname;
	private String description;
	private String modid;
	private String lobbyname;
	private LevelMapFinder levelfinder;
	private ArrayList<LevelContainer> levels = new ArrayList<LevelContainer>();
	private VODJsonReader reader;

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Adventure adventure = new Adventure(new File(
				"C:\\Users\\adam_\\AppData\\Roaming\\voltsofdoom\\resources\\adventure\\voltsofdoom-coregame\\casketofazamgarath.json"));
	}

	/**
	 * @param json The JSON file to read this Adventure object from.
	 */
	public Adventure(File json) {
		this.json = json;
		this.reader = new VODJsonReader(json);
		this.registryname = reader.getObj().get("registryname").getAsString();
		this.displayname = reader.getObj().get("displayname").getAsString();
		this.description = reader.getObj().get("description").getAsString();
		this.modid = reader.getObj().get("modid").getAsString();
		this.lobbyname = reader.getObj().get("lobby").getAsString();
		this.levelfinder = new LevelMapFinder(this);
		this.levels = getLevelContainers();
	}

	private ArrayList<LevelContainer> getLevelContainers() {
		for (File json : levelfinder.getLevels()) {
			levels.add(new LevelContainer(new VODJsonReader(json)));
		}

		return levels;
	}

	public File getJson() {
		return json;
	}

	public String getRegistryname() {
		return registryname;
	}

	public String getDisplayname() {
		return displayname;
	}

	public String getDescription() {
		return description;
	}

	public String getModid() {
		return modid;
	}

	public String getLobbyname() {
		return lobbyname;
	}

	public LevelMapFinder getLevelfinder() {
		return levelfinder;
	}

	public ArrayList<LevelContainer> getLevels() {
		return levels;
	}

	public VODJsonReader getReader() {
		return reader;
	}
}
