package vision.voltsofdoom.zapbyte.main;

import vision.voltsofdoom.zapbyte.loading.LoadingManager;

/**
 * Holds pre-made {@link ZapBit}s.
 * 
 * @author GenElectrovise
 *
 */
public class ZapBits {
	
	/**
	 * Runs {@link LoadingManager#load()}
	 */
	public static final ZapBit LOAD_MODS = new ZapBit(10, () -> {
		LoadingManager.load();
	});
}
