package vision.voltsofdoom.coresystem.play.adventure;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class EntityMap {
	private List<KeyNode> key;
	private List<EntityKeyPlacementNode> map;

	public List<KeyNode> getKey() {
		return key;
	}

	public List<EntityKeyPlacementNode> getMap() {
		return map;
	}

	public static EntityMap fromJson(JsonObject json) {
		return new Gson().fromJson(json, EntityMap.class);
	}
}
