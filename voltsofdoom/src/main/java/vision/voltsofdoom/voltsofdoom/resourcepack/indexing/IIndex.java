package vision.voltsofdoom.voltsofdoom.resourcepack.indexing;

import java.util.Map;
import com.google.common.collect.Maps;

public interface IIndex<A, B> {

  /**
   * A blank {@link IIndex} with stubbed methods. It will not return null.
   */
  public static final IIndex<?, ?> BLANK = new IIndex<Object, Object>() {
    @Override
    public Map<Object, Object> getMappings() {
      return Maps.newHashMap();
    }

    @Override
    public void associate(Object a, Object b) {
      return;
    }
  };
  
  /**
   * 
   * @return The contained mappings.
   */
  Map<A, B> getMappings();

  /**
   * Equivalent of {@link Map#put(Object, Object)} for an {@link IIndex}.
   * 
   * @param a The key
   * @param b The value
   */
  void associate(A a, B b);

  /**
   * 
   * @param <K>
   * @param <V>
   * @return A new {@link IIndex}.
   */
  public static <K, V> IIndex<K, V> create() {
    return new IIndex<K, V>() {

      Map<K, V> mappings = Maps.newHashMap();

      @Override
      public Map<K, V> getMappings() {
        return mappings;
      }

      @Override
      public void associate(K k, V v) {
        mappings.put(k, v);
      }
    };
  }
}
