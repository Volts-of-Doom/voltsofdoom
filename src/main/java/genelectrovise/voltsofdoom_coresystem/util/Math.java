package genelectrovise.voltsofdoom_coresystem.util;

import genelectrovise.voltsofdoom_coresystem.main.VODCoreSystemStart;
import genelectrovise.voltsofdoom_coresystem.opengl.WindowHolder;
import genelectrovise.voltsofdoom_coresystem.util.Math.Dimensions.EnumAxis;

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
 * @author adam_
 *
 */
public class Math {

	public static void main(String[] args) {
		System.out.println(Math.Dimensions.pixelPosToFloatCenteredOnOrigin(EnumAxis.VERTICAL, 64));
		// System.out.println(Dimensions.findSameDistanceOnOpposingAxis(EnumAxis.VERTICAL,
		// Dimensions.pixelPosToFloatCenteredOnOrigin(EnumAxis.HORIZONTAL, 64)));
	}

	/**
	 * Handles Geometry related operations, such as translating shapes.
	 * 
	 * @author adam_
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

	/**
	 * A subclass of Math for methods relating to the Dimensions of the game screen,
	 * for example converting <b>Integer Game Coordinates</b> to <b>Integer Screen
	 * Coordinates</b> to <b>OpenGL's Float Coordinate System</b>, and so on.
	 * 
	 * @author adam_
	 *
	 */
	public static class Dimensions {

		/**
		 * A nice little Enum for the two values: <b>HOROZONTAL</b> and <b>VERTICAL</b>
		 * 
		 * @author adam_
		 *
		 */
		public static enum EnumAxis {
			VERTICAL, HORIZONTAL;
		}

		/**
		 * 
		 * @param mode If none specified or is not one of the following, returns 0 <br>
		 *             0 = Get the screen width with no fallback and no extra data. <br>
		 *             1 = Get the screen width, catching NullPointerExceptions and
		 *             printing a stacktrace. Will return null if an Exception
		 *             occurs.<br>
		 *             2 = As 1, but defaults to a preset if an Exception occurs. <br>
		 *             3 = As 2, but does not print a stacktrace. <br>
		 * @return Dependent on mode, a variation on the screen's width, as in
		 *         {@link WindowHolder}
		 */
		@SuppressWarnings("null")
		public static int getScreenWidth(int mode) {
			int screenWidth = 0;

			switch (mode) {
			case 0:
				screenWidth = VODCoreSystemStart.getSYSTEM_CONTROL().getWindowHolder().width;
				break;
			case 1:
				try {
					screenWidth = VODCoreSystemStart.getSYSTEM_CONTROL().getWindowHolder().width;
				} catch (NullPointerException n) {
					n.printStackTrace();
					screenWidth = (Integer) null;
				}
				break;
			case 2:
				try {
					screenWidth = VODCoreSystemStart.getSYSTEM_CONTROL().getWindowHolder().width;
				} catch (NullPointerException n) {
					n.printStackTrace();
					screenWidth = 1200;
				}
				break;
			case 3:
				try {
					screenWidth = VODCoreSystemStart.getSYSTEM_CONTROL().getWindowHolder().width;
				} catch (NullPointerException n) {
					screenWidth = 1200;
				}
				break;
			}

			return screenWidth;
		}

		/**
		 * 
		 * @param mode If none specified, returns 0 <br>
		 *             0 = Get the screen width with no fallback and no extra data. <br>
		 *             1 = Get the screen width, catching NullPointerExceptions and
		 *             printing a stacktrace. Will return null if an Exception
		 *             occurs.<br>
		 *             2 = As 1, but defaults to a preset if an Exception occurs. <br>
		 *             3 = As 2, but does not print a stacktrace. <br>
		 * @return Dependent on mode, a variation on the screen's width, as in
		 *         {@link WindowHolder}
		 */
		@SuppressWarnings("null")
		public static int getScreenHeight(int mode) {
			int screenHeight = 0;

			switch (mode) {
			case 0:
				screenHeight = VODCoreSystemStart.getSYSTEM_CONTROL().getWindowHolder().height;
				break;
			case 1:
				try {
					screenHeight = VODCoreSystemStart.getSYSTEM_CONTROL().getWindowHolder().height;
				} catch (NullPointerException n) {
					n.printStackTrace();
					screenHeight = (Integer) null;
				}
				break;
			case 2:
				try {
					screenHeight = VODCoreSystemStart.getSYSTEM_CONTROL().getWindowHolder().height;
				} catch (NullPointerException n) {
					n.printStackTrace();
					screenHeight = 720;
				}
				break;
			case 3:
				try {
					screenHeight = VODCoreSystemStart.getSYSTEM_CONTROL().getWindowHolder().height;
				} catch (NullPointerException n) {
					screenHeight = 720;
				}
				break;
			}

			return screenHeight;
		}

		/**
		 * Converts a Screen Pixel Coordinate to an OpenGL Float position.
		 * 
		 * @param axis  The axis you are converting on.
		 * @param pixel The value of the pixel you are converting.
		 * @return A float representation of the pixel input.
		 */
		public static float pixelPosToFloatCenteredOnOrigin(EnumAxis axis, float pixel) {

			float screenDimensionAxisly;

			switch (axis) {
			case HORIZONTAL:
				screenDimensionAxisly = getScreenWidth(3);
				break;
			case VERTICAL:
				screenDimensionAxisly = getScreenHeight(3);
				break;
			default:
				throw new IllegalArgumentException(
						"If you're seeing this, someone should give you a medal. I don't know how you did it, but you have failed to set the axis, even though it is a required parameter for this method and should throw a compiler error.");
			}

			if (pixel == screenDimensionAxisly) {
				return 1f;
			}

			if (pixel == 0) {
				return -1f;
			}

			float halfScreenAxisly = screenDimensionAxisly / 2;
			@SuppressWarnings("unused")
			boolean floatWillBePositive;

			if (pixel > halfScreenAxisly) {
				floatWillBePositive = true;
			} else if (pixel < halfScreenAxisly) {
				floatWillBePositive = false;
			} else {
				return 0;
			}

			float pixelDividedByHalfScreenAxisly = pixel / halfScreenAxisly;

			if (floatWillBePositive)
				return pixelDividedByHalfScreenAxisly;
			else
				return pixelDividedByHalfScreenAxisly - 1;

		}

		/**
		 * Finds the float multiplier you must multiply a float by on one axis to find
		 * an equivalent distance on the opposing axis (toAxis). Not very useful alone,
		 * but enables you to draw squares in OpenGL.
		 * 
		 * @param toAxis The axis to find the multiplier for.
		 * @return toAxis' multiplier
		 */
		public static float getAxisMultiplier(EnumAxis toAxis) {
			float screenHeight = getScreenHeight(3);
			float screenWidth = getScreenWidth(3);

			switch (toAxis) {
			case HORIZONTAL:
				return screenWidth / screenHeight;
			case VERTICAL:
				return screenHeight / screenWidth;
			default:
				return screenWidth / screenHeight;
			}
		}

		/**
		 * @param axisToFindOn The axis to find the equivalent distance on.
		 * @param fl           Distance to find the equivalent of.
		 * @return The equivalent distance.
		 */
		public static float findSameDistanceOnOpposingAxis(EnumAxis axisToFindOn, float fl) {
			switch (axisToFindOn) {
			case HORIZONTAL:
				return fl * getAxisMultiplier(EnumAxis.HORIZONTAL);
			case VERTICAL:
				return fl * getAxisMultiplier(EnumAxis.VERTICAL);
			default:
				return fl * getAxisMultiplier(axisToFindOn);
			}
		}
	}
}
