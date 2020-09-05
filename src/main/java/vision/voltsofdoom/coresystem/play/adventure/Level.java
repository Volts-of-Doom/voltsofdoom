package vision.voltsofdoom.coresystem.play.adventure;

import vision.voltsofdoom.coresystem.universal.log.Loggers;

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

	@Override
	public String toString() {

		Loggers.CORESYSTEM_LOADING.severe("Levels not loaded yet, either at loading or runtime!");

		StringBuilder builder = new StringBuilder();
		builder.append("Level{");

		builder.append(levelConfiguration.toString());

		builder.append("}");
		return builder.toString();
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
