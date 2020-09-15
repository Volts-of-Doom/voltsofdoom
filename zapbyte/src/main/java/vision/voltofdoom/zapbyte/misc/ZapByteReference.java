package vision.voltofdoom.zapbyte.misc;

import java.io.File;

public class ZapByteReference {

	/**
	 * The directory the application is running in (the user's home directory)
	 */
	public static final String EXECUTION_DIRECTORY = System.getProperty("user.dir");

	/**
	 * The system file path section separator
	 */
	public static final String SEP = File.separator;

	/**
	 * The game's %App Data%/Roaming directory
	 */
	public static final String ZAP_BYTE_ROAMING = System.getProperty("user.home") + SEP + "AppData" + SEP + "Roaming" + SEP + "zapbyte" + SEP;
}
