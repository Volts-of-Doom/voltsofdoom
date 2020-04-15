package genelectrovise.voltsofdoom_coresystem.util;

import genelectrovise.voltsofdoom_coresystem.log.VODLog4J;
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
		VODLog4J.LOGGER.debug("Float out : " + Dimensions.intPixelPosToFloatCenteredOnOrigin(EnumAxis.HOROZONTAL, 900));
		VODLog4J.LOGGER.debug("Float out : " + Dimensions.intPixelPosToFloatCenteredOnOrigin(EnumAxis.VERTICAL, 540));
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
			VERTICAL, HOROZONTAL;
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
		 * @return Dependent on mode, a variation on the screen's width, as in {@link WindowHolder}
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
		 * @return Dependent on mode, a variation on the screen's width, as in {@link WindowHolder}
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
					screenHeight = 1200;
				}
				break;
			case 3:
				try {
					screenHeight = VODCoreSystemStart.getSYSTEM_CONTROL().getWindowHolder().height;
				} catch (NullPointerException n) {
					screenHeight = 1200;
				}
				break;
			}

			return screenHeight;
		}

		/**
		 * Converts a Screen Pixel Coordinate to an OpenGL Float position.
		 * @param axis The axis you are converting on.
		 * @param pixel The value of the pixel you are converting.
		 * @return A float representation of the pixel input.
		 */
		public static float intPixelPosToFloatCenteredOnOrigin(EnumAxis axis, int pixel) {
			float screenAxisly;

			switch (axis) {
			case HOROZONTAL:
				screenAxisly = getScreenWidth(3);
				break;
			case VERTICAL:
				screenAxisly = getScreenHeight(3);
				break;
			default:
				throw new IllegalArgumentException(
						"If you're seeing this, someone should give you a medal. I don't know how you did it, but you have failed to set the axis, even though it is a required parameter for this method.");
			}

			float halfScreenAxisly = screenAxisly / 2;
			boolean floatWillBePositive;

			if (pixel > halfScreenAxisly) {
				floatWillBePositive = true;
			} else if (pixel < halfScreenAxisly) {
				floatWillBePositive = false;
			} else {
				return 0;
			}

			float pixelDividedByHalfScreenWidth = pixel / halfScreenAxisly;

			if (floatWillBePositive)
				return pixelDividedByHalfScreenWidth - 1;
			else
				return pixelDividedByHalfScreenWidth - 1;
		}
	}
}
