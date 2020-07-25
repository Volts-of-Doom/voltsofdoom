package vision.voltsofdoom.coresystem.play.adventure;

import java.io.File;
import java.util.ArrayList;

import vision.voltsofdoom.coresystem.play.adventure.levelcontainer.LevelContainer;
import vision.voltsofdoom.coresystem.universal.resource.VODJsonReader;

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

	public ArrayList<LevelContainer> getLevels() {
		return levels;
	}

	public VODJsonReader getReader() {
		return reader;
	}
}
