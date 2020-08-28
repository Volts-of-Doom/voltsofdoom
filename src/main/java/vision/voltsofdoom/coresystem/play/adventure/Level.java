package vision.voltsofdoom.coresystem.play.adventure;

/**
 * A uhhhhh... {@link Level} in an {@link Adventure}...
 * 
 * @author GenElectrovise
 *
 */
public class Level {
	
	private String registryName;
	private String displayName;

	/**
	 * A container for a level. Contains all resources for a level.
	 * 
	 * @param configuration A VODJsonReader containing an instance of the file the
	 *                      level should be constructed from.
	 */
	public Level(LevelConfiguration configuration) {

	}
	
	public String getRegistryName() {
		return registryName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
}
