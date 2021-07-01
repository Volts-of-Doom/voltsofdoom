package vision.voltsofdoom.astar.maths;

import java.util.ArrayList;

/**
 * A vector (or position) which contains a float array of unknown length to represent its values.
 * 
 * @author GenElectrovise
 *
 */
public class FloatArrayVector {
  private float[] values;

  public FloatArrayVector(float[] values) {
    this.values = values;
  }

  public float[] getValues() {
    return values;
  }

  /**
   * Subtract the given value from this {@link FloatArrayVector}.
   * 
   * @param subtract
   */
  public FloatArrayVector subtract(FloatArrayVector subtract) {
    float[] aSubtract = subtract.getValues();
    float[] aFrom = this.getValues();

    if (!areOperable(subtract, this)) {
      throw new IllegalArgumentException("FloatArrayVector#substract value and from are not operable!");
    }

    for (int i = 0; i < aSubtract.length; i++) {
      this.values[i] = aFrom[i] - aSubtract[i];
    }

    return this;
  }

  /**
   * Can the two given {@link FloatArrayVector}s be operated on together? 
   * @param value
   * @param from
   */
  public static boolean areOperable(FloatArrayVector value, FloatArrayVector from) {
    if (value.getValues().length != from.getValues().length) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {

    ArrayList<Float> floats = new ArrayList<Float>();
    for (float f : getValues()) {
      floats.add(Float.valueOf(f));
    }

    return floats.toString();
  }
}
