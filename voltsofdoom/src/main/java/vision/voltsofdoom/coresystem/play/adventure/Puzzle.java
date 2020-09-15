package vision.voltsofdoom.coresystem.play.adventure;

import com.google.gson.JsonObject;

import vision.voltsofdoom.coresystem.universal.log.Loggers;

public class Puzzle {

	public static Puzzle fromJson(JsonObject fromJson) {
		Loggers.CORESYSTEM_LOADING_RESOURCE.warning("Level#fromJson not implemented");
		return new Puzzle();
	}

}
