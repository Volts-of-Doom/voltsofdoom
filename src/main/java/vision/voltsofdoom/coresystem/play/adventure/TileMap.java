package vision.voltsofdoom.coresystem.play.adventure;

import java.util.List;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class TileMap {
	private List<KeyNode> key;
	private List<List<String>> map;
	
	public List<List<String>> getMap() {
		return map;
	}
	
	public List<KeyNode> getKey() {
		return key;
	}

	public static TileMap fromJson(JsonObject json) {
		TileMap tileMap = new Gson().fromJson(json, TileMap.class);
		Objects.requireNonNull(tileMap.key, () -> "TileMap#fromJson found key to be null.");
		Objects.requireNonNull(tileMap.map, () -> "TileMap#fromJson found map to be null.");

		tileMap.key.forEach((key) -> {
			Objects.requireNonNull(key, () -> "TileMap#fromJson found a KeyNode to be null.");
		});

		tileMap.map.forEach((map) -> {
			Objects.requireNonNull(map, () -> "TileMap#fromJson found an EntityKeyPlacementNode to be null.");
		});

		return tileMap;
	}
}
