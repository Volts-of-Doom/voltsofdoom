package vision.voltsofdoom.coresystem.play.key;

import vision.voltsofdoom.coresystem.play.adventure.levelcontainer.KeyDictionary;
import vision.voltsofdoom.coresystem.universal.log.VODLog4J;

public class LoadingScreenKeyDictionary extends KeyDictionary {

	public LoadingScreenKeyDictionary(long window) {
		super(window);
	}

	@Override
	public boolean handle() {
		VODLog4J.LOGGER.info("Loading not finished! Please don't press keys yet! It won't work!");
		return false;
	}

}
