package vision.voltsofdoom.coresystem.universal.util;

/**
 * A class with assorted mathematical methods, divided into subclasses by topic,
 * Eg. For methods regarding screen dimensions, see {@link Math.Dimensions}.
 * <br>
 * <br>
 * <i>For anyone who might (or might not) care, I have chosen the American
 * spelling of "Math" above the British, more natural for me, spelling of
 * "Maths" (which is obviously superior :-) ... just kidding), as it is more
 * natural for me to type "Math" when coding in Java.</i>
 * 
 * @author GenElectrovise
 *
 */
public class Math {

	/**
	 * Handles Geometry related operations, such as translating shapes.
	 * 
	 * @author GenElectrovise
	 *
	 */
	public static class Geometry {

		/**
		 * Creates a new array of vertices for a quad, making it seem to glide in a
		 * direction. Moves Horizontally.
		 * 
		 * @param pos The current array of vertices
		 * @param i   The amount to increment the quad's position.
		 * @return A new float[] of positions for a quad.
		 */
		public static float[] incrBasicQuadX(float[] pos, float i) {
			pos[0] = pos[0] + i;
			pos[2] = pos[2] + i;
			pos[4] = pos[4] + i;
			pos[6] = pos[6] + i;
			pos[8] = pos[8] + i;
			pos[10] = pos[10] + i;
			return pos;
		}

		/**
		 * Creates a new array of vertices for a quad, making it seem to glide in a
		 * direction. Moves vertically.
		 * 
		 * @param pos The current array of vertices
		 * @param i   The amount to increment the quad's position.
		 * @return A new float[] of positions for a quad.
		 */
		public static float[] incrBasicQuadY(float[] pos, float i) {
			pos[1] = pos[1] + i;
			pos[3] = pos[3] + i;
			pos[5] = pos[5] + i;
			pos[7] = pos[7] + i;
			pos[9] = pos[9] + i;
			pos[11] = pos[11] + i;
			return pos;
		}
	}
}
