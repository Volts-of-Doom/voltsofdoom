package vision.voltsofdoom.coresystem.universal.util;

import java.io.File;

/**
 * Holds static final variables which must be visible to the rest of the game.
 * This primarily includes directory paths.
 * 
 * @author GenElectrovise
 *
 */
public class Reference {
	/**
	 * 
	 */
	public static final String EXECUTION_DIRECTORY = System.getProperty("user.dir");
	/**
	 * The system file path section separator
	 */
	public static final String SEP = File.separator;

	// Roaming

	/**
	 * The game's %App Data%/Roaming directory
	 */
	public static final String ROAMING = System.getProperty("user.home") + SEP + "AppData" + SEP + "Roaming" + SEP
			+ "voltsofdoom" + SEP;
	public static final String ROAMING_RESOURCES = ROAMING + "resources" + SEP;
	public static final String MOD_RESOURCES = ROAMING + "resources" + SEP;
	public static final String ADVENTURE = ROAMING_RESOURCES + SEP + "adventure" + SEP;
	public static final String MODS_DIRECTORY = ROAMING + "mods" + SEP;
	public static final String PROGRAM_FILES = "C:\\Program Files" + SEP + "voltsofdoom";
	public static final String CONFIG = ROAMING + SEP + "config" + SEP;

	// Internal
	public static final String INTERNAL_RESOURCES = "src" + SEP + "main" + SEP + "resources" + SEP;
}
