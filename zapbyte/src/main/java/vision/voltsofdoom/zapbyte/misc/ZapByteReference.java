package vision.voltsofdoom.zapbyte.misc;

import java.io.File;

import com.google.inject.name.Named;

import vision.voltsofdoom.zapbyte.main.ZapByte;

public class ZapByteReference {
	
	public static String getSep(){
		return File.separator;
	}

	/**
	 * The system file path section separator
	 */
	public static final String SEP = File.separator;

	/**
	 * Populated at startup by {@link ZapByte#run()}
	 */
	@Named("applicationNamespace")
	public static String APPLICATION_NAMESPACE = null;

	/**
	 * The directory the application is running in (the user's home directory)
	 */
	public static final String USER_HOME = System.getProperty("user.dir");

	/**
	 * The game's %App Data%/Roaming directory
	 */
	public static final String ZAP_BYTE_ROAMING = System.getProperty("user.home") + SEP + "AppData" + SEP + "Roaming" + SEP + "zapbyte" + SEP;

	/**
	 * The roaming directory of this particular application.
	 */
	public static final String APPLICATION_ROAMING = ZAP_BYTE_ROAMING + SEP + APPLICATION_NAMESPACE + SEP;

	/**
	 * The output location of log files
	 */
	public static final String LOGS = ZAP_BYTE_ROAMING + SEP + APPLICATION_NAMESPACE + SEP + "logs" + SEP;

	/**
	 * The location of this application's configuration files' folder
	 */
	public static final String CONFIG = APPLICATION_ROAMING + "config" + SEP;
}
