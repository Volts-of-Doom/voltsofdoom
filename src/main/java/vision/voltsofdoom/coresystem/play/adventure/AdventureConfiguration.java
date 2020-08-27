package vision.voltsofdoom.coresystem.play.adventure;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import vision.voltsofdoom.coresystem.universal.resource.ResourceLocation;

/**
 * The configuration for an {@link Adventure}, in order to abstract away the
 * basic setting-of-things and unclog the {@link Adventure} class.
 * 
 * @author GenElectrovise
 *
 */
public class AdventureConfiguration {
	private ResourceLocation identifier;
	private String displayName;
	private String description;
	private String lobbyname;
	private List<String> levelNames;
	
	public AdventureConfiguration() {
		this.levelNames = new ArrayList<String>();
	}

	public AdventureConfiguration fromJson(JsonObject json) {

		withIdentifier(new ResourceLocation(json.get("registryname").getAsString(), json.get("modid").getAsString()));
		withDisplayName(json.get("displayname").getAsString());
		withDescription(json.get("description").getAsString());
		withLobbyName(json.get("lobby").getAsString());

		this.levelNames = new ArrayList<String>();
		json.get("levels").getAsJsonArray().forEach((item) -> {
			levelNames.add(item.getAsString());
		});
		return this;
	}

	// With
	public AdventureConfiguration withIdentifier(ResourceLocation identifier) {
		this.identifier = identifier;
		return this;
	}

	public AdventureConfiguration withDisplayName(String displayName) {
		this.displayName = displayName;
		return this;
	}

	public AdventureConfiguration withDescription(String description) {
		this.description = description;
		return this;
	}

	public AdventureConfiguration withLobbyName(String lobbyName) {
		this.lobbyname = lobbyName;
		return this;
	}

	public AdventureConfiguration withLevelNames(String... levelNames) {
		for (int i = 0; i < levelNames.length; i++) {
			this.levelNames.add(levelNames[i]);
		}
		return this;
	}

	// Get
	public ResourceLocation getIdentifier() {
		return identifier;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getDescription() {
		return description;
	}

	public String getLobbyname() {
		return lobbyname;
	}

	public List<String> getLevelNames() {
		return levelNames;
	}
}
