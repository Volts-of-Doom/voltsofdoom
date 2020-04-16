package genelectrovise.voltsofdoom_coresystem.opengl;

import genelectrovise.voltsofdoom_coresystem.adventure.levelcontainer.KeyDictionary;
import genelectrovise.voltsofdoom_coresystem.log.VODLog4J;

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
