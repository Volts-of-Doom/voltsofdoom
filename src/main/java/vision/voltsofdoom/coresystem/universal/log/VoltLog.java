package vision.voltsofdoom.coresystem.universal.log;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * A bad implementation of Log4j, needs updating. The game's central logger.
 * 
 * @author GenElectrovise
 *
 */
public class VoltLog {
	private Logger logger;

	public VoltLog(Class<?> clazz) {
		logger = LogManager.getLogManager().getLogger(clazz.getName()) == null ? Logger.getLogger(clazz.getName())
				: LogManager.getLogManager().getLogger(clazz.getName());
	}

	public Logger getLogger() {
		return logger;
	}

	public void info(String msg) {
		logger.info(msg);
	}

	public void warn(String msg) {
		logger.warning(msg);
	}

	public void debug(String msg) {
		logger.fine(msg);
	}

	public void fatal(String sourceClass, String sourceMethod, Throwable thrown) {
		logger.throwing(sourceClass, sourceMethod, thrown);
	}

	public void trace(String msg) {
		logger.finer(msg);
	}

	public void error(String msg) {
		logger.severe(msg);
	}

	public void infoWithPrefix(String prefixMsg, String... msgs) {
		info(prefixMsg);
		for (String msg : msgs) {
			info(msg.toString());
		}
	}

	public void infoWithSuffix(String prefixMsg, String suffixMsg, String... msgs) {
		infoWithPrefix(prefixMsg, msgs);
		info(suffixMsg);
	}

	public void infoWithSuffixAndObject(String prefixMsg, String suffixMsg, Object forEach) {
		infoWithPrefix(prefixMsg, forEach.toString());
		info(suffixMsg);
	}

	public void status(String statusMsg) {
		warn(">>> " + statusMsg);
	}

}
