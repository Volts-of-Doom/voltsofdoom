package vision.voltsofdoom.coresystem.play.adventure;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class TileMap {
	private List<KeyNode> key;
	private List<List<String>> map;
	
	/*
	 * public List<KeyNode> getKey() { return key; }
	 */
	
	public List<List<String>> getMap() {
		return map;
	}

	public static TileMap fromJson(JsonObject json) {
		return new Gson().fromJson(json, TileMap.class);
	}
}
