package vision.voltsofdoom.coresystem.universal.log;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class Formatters {

	public static final Formatter DEFAULT = new Formatter() {

		@Override
		public String format(LogRecord record) {
			return record.toString();
		}
	};

}
