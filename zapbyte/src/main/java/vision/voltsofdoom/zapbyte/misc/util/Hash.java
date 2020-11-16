package vision.voltsofdoom.zapbyte.misc.util;

public class Hash {

  /**
   * adapted from String.hashCode()
   * 
   * @return The hashed {@link String}.
   */
  public static long hash(String string) {
    long h = 1125899906842597L; // prime
    int len = string.length();

    for (int i = 0; i < len; i++) {
      h = 31 * h + string.charAt(i);
    }
    return h;
  }
}
