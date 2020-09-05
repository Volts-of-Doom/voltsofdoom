package vision.voltsofdoom.coresystem.play.adventure;

/**
 * A uhhhhh... {@link Level} in an {@link Adventure}...
 * 
 * @author GenElectrovise
 *
 */
public class Level {
	
	private LevelConfiguration levelConfiguration;
	
	private Level() {
	}
	
	public LevelConfiguration getLevelConfiguration() {
		return levelConfiguration;
	}
	
	public static class Builder {
		
		Level level = new Level();

		public void withConfiguration(LevelConfiguration levelConfiguration) {
			level.levelConfiguration = levelConfiguration;
		}

		public Level build() {
			return level;
		}
		
	}
}
