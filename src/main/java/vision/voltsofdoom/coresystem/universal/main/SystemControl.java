package vision.voltsofdoom.coresystem.universal.main;

import java.io.IOException;

import vision.voltsofdoom.coresystem.play.adventure.AdventureLoader;
import vision.voltsofdoom.coresystem.universal.opengl.WindowHolder;

public class SystemControl {
	SystemControl System_Control = this;

	// PreInit
	private WindowHolder windowholder = new WindowHolder(this);

	// Stage 2(Load registries)
	public AdventureLoader adventureloader = new AdventureLoader();

	public boolean loadingComplete = false;

	public void begin() throws IOException {
		postInit();
	}

	public void initAdventures() {
		adventureloader.init();
	}

	/**
	 * Final stage of initialisation. Opens the game window.
	 * 
	 * @throws IOException
	 */
	public void postInit() throws IOException {
		loadingComplete = true;
		windowholder.start();
	}

	// GETTERS ==================

	public SystemControl getSystem_Control() {
		return System_Control;
	}

	public AdventureLoader getAdventureloader() {
		return adventureloader;
	}

	public WindowHolder getWindowHolder() {
		return windowholder;
	}

}
