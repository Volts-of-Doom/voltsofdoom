package vision.voltsofdoom.coresystem.universal.log;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;

public class Handlers {
	public static FileHandler FILE_HANDLER;
	public static ConsoleHandler CONSOLE_HANDLER;

	static {
		try {
			
			FILE_HANDLER = new FileHandler("logs/log.log", 512, 10, false);
			CONSOLE_HANDLER = new ConsoleHandler();
			
			FILE_HANDLER.setFormatter(Formatters.DEFAULT);
			CONSOLE_HANDLER.setFormatter(Formatters.DEFAULT);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
