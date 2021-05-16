package vision.voltsofdoom.voltsofdoom.adventure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHandler {
  public static final Gson GSON = fromOptions();

  private static Gson fromOptions() {
    GsonBuilder builder = new GsonBuilder();

    builder.setPrettyPrinting();
    
    builder.registerTypeAdapter(DataTag.class, DataTag.DESERIALIZER);
    builder.registerTypeAdapter(DataTagMap.class, DataTagMap.DESERIALIZER);

    return builder.create();
  }

  
}
