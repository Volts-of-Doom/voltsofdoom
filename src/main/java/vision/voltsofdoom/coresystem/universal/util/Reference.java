package vision.voltsofdoom.coresystem.universal.util;

import java.io.File;

public class Reference {
	public static final String EXECUTION_DIRECTORY = System.getProperty("user.dir");
	public static final String SEP = File.separator;
	public static final String ROAMING = System.getProperty("user.home") + SEP + "AppData" + SEP + "Roaming" + SEP
			+ "voltsofdoom" + SEP;
	public static final String ADVENTURE = ROAMING + "resources" + SEP + "adventure" + SEP;
	public static final String MODS_DIRECTORY = ROAMING + "mods" + SEP;
	public static final String PROGRAM_FILES = "C:\\Program Files" + SEP + "voltsofdoom";
	public static final String RESOURCES = "src" + SEP + "main" + SEP + "resources" + SEP;
	public static final String CONFIG = ROAMING + SEP + "config" + SEP;
}
