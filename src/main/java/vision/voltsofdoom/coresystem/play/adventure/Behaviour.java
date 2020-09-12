package vision.voltsofdoom.coresystem.play.adventure;

import com.google.gson.JsonObject;

import vision.voltsofdoom.coresystem.universal.log.Loggers;

public class Behaviour {

	public static Behaviour fromJson(JsonObject fromJson) {
		Loggers.CORESYSTEM_LOADING_RESOURCE.warning("Behaviour#fromJson not implemented");
		return new Behaviour();
	}

}
