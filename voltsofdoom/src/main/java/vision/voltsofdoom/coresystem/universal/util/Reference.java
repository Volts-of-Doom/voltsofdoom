package vision.voltsofdoom.coresystem.universal.util;

import static vision.voltofdoom.zapbyte.misc.ZapByteReference.*;

/**
 * Holds static final variables which must be visible to the rest of the game.
 * This primarily includes directory paths.
 * 
 * @author GenElectrovise
 *
 */
public class Reference {

	// Roaming

	/**
	 * The game's %App Data%/Roaming directory
	 */
	public static final String ROAMING = ZAP_BYTE_ROAMING + "voltsofdoom" + SEP;
	public static final String ROAMING_RESOURCES = ROAMING + "resources" + SEP;
	public static final String ADVENTURE = ROAMING_RESOURCES + SEP + "adventure" + SEP;
	public static final String MODS_DIRECTORY = ROAMING + "mods" + SEP;
	public static final String CONFIG = ROAMING + SEP + "config" + SEP;
	public static final String LOGS = ROAMING + SEP + "logs" + SEP;

	// Internal
	public static final String INTERNAL_RESOURCES = "src" + SEP + "main" + SEP + "resources" + SEP;

}
