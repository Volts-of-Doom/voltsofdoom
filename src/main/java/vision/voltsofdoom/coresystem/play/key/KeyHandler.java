package vision.voltsofdoom.coresystem.play.key;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import vision.voltsofdoom.coresystem.play.adventure.levelcontainer.KeyDictionary;
import vision.voltsofdoom.coresystem.universal.opengl.WindowHolder;

/**
 * A handy class for handling keystrokes!
 * 
 * @author GenElectrovise
 *
 */
public class KeyHandler {

	public KeyDictionary currentKeyDictionary;
	public static KeyHandler instance = new KeyHandler();
	public static long window;

	public KeyDictionary getCurrentKeyDictionary() {
		return currentKeyDictionary;
	}

	public void setCurrentKeyDictionary(KeyDictionary currentKeyDictionary) {
		this.currentKeyDictionary = currentKeyDictionary;
	}

	public static final KeyDictionary NO_HANDLER = new KeyDictionary(window) {
		@Override
		public boolean handle() {
			return true;
		}
	};

	/**
	 * The main handle method called in the gameloop
	 */
	public void handle() {
		currentKeyDictionary.handle();

	}

	/**
	 * A secondary handle method called from the key callback in
	 * {@link WindowHolder}. This is just for hooking onto GLFW's key events and may
	 * become the norm in future.
	 * 
	 * @param window
	 * @param key
	 * @param scancode
	 * @param action
	 * @param mods
	 */
	public void handle(long window, int key, int scancode, int action, int mods) {
		if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
			glfwSetWindowShouldClose(window, true);
	}

	public void setWindow(long window) {
		KeyHandler.window = window;
	}

}
