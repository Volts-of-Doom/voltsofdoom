package vision.voltsofdoom.zapbyte.misc.util;

public class StacktraceUtils {

	public static String stacktraceToString(StackTraceElement[] stackTraceElements) {

		StringBuilder builder = new StringBuilder();
		for (StackTraceElement stackTraceElement : stackTraceElements) {
			builder.append(stackTraceElement.toString());
		}
		return builder.toString();
	}
}
