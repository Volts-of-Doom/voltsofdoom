package genelectrovise.voltsofdoom_coresystem.adventure.levelcontainer;

import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

/**
 * Extend this class to create custom keybinds for a level.
 * @author adam_
 *
 */
public abstract class KeyDictionary {
	private long window;

	/**
	 * @param window The game window.
	 */
	public KeyDictionary(long window) {
		this.window = window;
	}

	/**
	 * Handles keystrokes.
	 * 
	 * @return True if all were handled successfully.
	 */
	public abstract boolean handle();

	/**
	 * @param key The key to test against
	 * @return Whether the current pressed key is the same as the key parameter
	 */
	public boolean keyIs(int key) {
		if (glfwGetKey(window, key) == GLFW_TRUE)
			return true;
		else
			return false;
	}
}
