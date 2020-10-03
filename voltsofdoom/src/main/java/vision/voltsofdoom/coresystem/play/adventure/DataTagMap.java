package vision.voltsofdoom.coresystem.play.adventure;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a {@link Map} of {@link DataTag}s.
 * 
 * @author GenElectrovise
 *
 */
public class DataTagMap {

  /**
   * Typically a {@link LinkedHashMap}.
   */
  private Map<String, DataTag> tagMap;

  public DataTagMap(List<DataTag> tags) {
    this(tagsToMap(tags));
  }

  public DataTagMap(Map<String, DataTag> tagMap) {
    this.tagMap = tagMap;
  }

  public List<DataTag> getTags() {
    return new ArrayList<DataTag>(tagMap.values());
  }

  /**
   * @param tags
   * @return A {@link LinkedHashMap} of the given {@link DataTag}s.
   */
  public static Map<String, DataTag> tagsToMap(List<DataTag> tags) {

    Map<String, DataTag> map = new LinkedHashMap<String, DataTag>();

    tags.forEach((tag) -> {
      map.put(tag.getKey(), tag);
    });

    return map;
  }
}
