package genelectrovise.voltsofdoom_coresystem.universal.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

public class VODLog4J {
	public static final VODLog4J LOGGER = new VODLog4J();
	private Logger log;

	public VODLog4J() {
		log = LogManager.getLogger(VODLog4J.class.getSimpleName());
		configure();
	}

	private void configure() {
		/*
		 * ConfigurationBuilder<BuiltConfiguration> configBuilder =
		 * ConfigurationBuilderFactory.newConfigurationBuilder();
		 * AppenderComponentBuilder appenderBuilder = FileAppender.newBuilder(appender);
		 * 
		 * //https://logging.apache.org/log4j/2.x/manual/appenders.html#FileAppender
		 * FileAppender appender = FileAppender.createAppender(generateFileName(), true,
		 * false, "vod_fileappender", true, false, true, 8192,
		 * "[%-5level] %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %c{1} - %msg%n", filter,
		 * advertise, advertiseUri, config);
		 * 
		 * configBuilder.add(appenderBuilder);
		 */
	}
	
	private String generateFileName() {
		return "loggingfile_ln30_vodlog4j.log";
	}

	public void info(String msg) {
		log.info(msg);
	}

	public void warn(String msg) {
		log.warn(msg);
	}

	public void debug(String msg) {
		log.debug(msg);
	}

	public void fatal(String msg) {
		log.info(msg);
	}

	public void trace(String msg) {
		log.trace(msg);
	}

	public void error(String msg) {
		log.error(msg);
	}

	public void infoWithPrefix(String prefixMsg, String... msgs) {
		info(prefixMsg);
		for (String msg : msgs) {
			info(msg.toString());
		}
	}

	public void infoWithSuffix(String prefixMsg, String suffixMsg, String... msgs) {
		infoWithPrefix(prefixMsg, msgs);
		System.out.println(suffixMsg);
	}

	public void infoWithSuffixAndObject(String prefixMsg, String suffixMsg, Object forEach) {
		infoWithPrefix(prefixMsg, forEach.toString());
		System.out.println(suffixMsg);
	}

	public void status(String statusMsg) {
		warn(">>> " + statusMsg);
	}

}
