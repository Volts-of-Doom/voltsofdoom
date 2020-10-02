package vision.voltsofdoom.coresystem.play.adventure;

import com.google.gson.JsonObject;
import vision.voltsofdoom.zapbyte.log.Loggers;

public class Puzzle {

  public static Puzzle fromJson(JsonObject fromJson) {
    Loggers.ZAPBYTE_LOADING_RESOURCE.warning("Level#fromJson not implemented");
    return new Puzzle();
  }

}
