package vision.voltsofdoom.zapbyte.log;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Logging {@link Formatter}s.
 * 
 * @author GenElectrovise
 *
 */
public class Formatters {

	public static final Formatter DEFAULT = new Formatter() {

		@Override
		public String format(LogRecord record) {

			Level level = record.getLevel();
			String loggerName = record.getLoggerName();
			String message = record.getMessage();
			String sourceClass = record.getSourceClassName();
			String sourceMethod = record.getSourceMethodName();
			Throwable thrown = record.getThrown();
			int threadId = record.getThreadID();

			StringBuilder builder = new StringBuilder();

			if (thrown != null) {
				builder.append("\nEXCEPTION");
			}

			builder.append("[" + level.getName() + "]");
			builder.append(" {" + loggerName + ", " + "ThreadID=" + threadId + "} | ");
			builder.append(" " + sourceClass + "." + sourceMethod + " | ");
			builder.append(message);

			if (thrown != null) {
				builder.append("\nThrown:\n");
				builder.append(thrown.toString());
				builder.append("\n");
			}

			builder.append("\n");

			return builder.toString();
		}
	};

}
