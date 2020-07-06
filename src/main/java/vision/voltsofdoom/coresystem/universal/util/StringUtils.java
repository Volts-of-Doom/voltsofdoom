package vision.voltsofdoom.coresystem.universal.util;

/**
 * Utility methods for working with {@link String}s
 * 
 * @author GenElectrovise
 *
 */
public class StringUtils {

	/**
	 * Converts the given {@link Object}[] into a {@link String}. Equivalent of a
	 * {@link #toString()} method for an array.
	 * 
	 * @param arr
	 * @return {@link #toString()} for the array given array.
	 */
	public static String arrayToString(Object[] arr) {
		StringBuilder builder = new StringBuilder();

		builder.append("[");
		for (int i = 0; i < arr.length; i++) {
			builder.append(arr[i] != null ? arr[i].toString() : "null");

			if (i != (arr.length - 1)) {
				builder.append(", ");
			}
		}
		builder.append("]");

		return builder.toString();
	}
}
