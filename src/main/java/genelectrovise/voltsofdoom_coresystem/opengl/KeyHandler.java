package genelectrovise.voltsofdoom_coresystem.opengl;

import static org.lwjgl.glfw.GLFW.*;

import java.io.IOException;

/**
 * A handy class for handling keystrokes!
 * 
 * @author adam_
 *
 */
public class KeyHandler {

	public static KeyHandler instance = new KeyHandler();
	public static long window;

	/**
	 * The main handle method called in the gameloop
	 */
	public void handle() {
		try {
			if (keyIs(GLFW_KEY_W))
				handleW(0.01f);
			if (keyIs(GLFW_KEY_A))
				handleA(-0.01f);
			if (keyIs(GLFW_KEY_S))
				handleS(-0.01f);
			if (keyIs(GLFW_KEY_D))
				handleD(0.01f);

		} catch (Exception e) {
			System.err.println("Error handling keystrokes! : ");
			e.printStackTrace();
		}
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

	/**
	 * Handles the W keystroke
	 * 
	 * @param incr The amount to increment the Y pos of the player.
	 * @throws IOException Creating the new buffers to pass to OpenGL for the new
	 *                     position of the player.
	 */
	private void handleW(float incr) throws IOException {
		float[] gbPos = RenderEngine.getInstance().getCurrentLevelRenderer().getRenderablesContainer().renderObjs
				.get("greenblob").pos;
		float[] pos = incrBasicQuadY(gbPos, incr);
		float[] tex = RenderEngine.getInstance().getCurrentLevelRenderer().getRenderablesContainer().renderObjs
				.get("greenblob").tex;

		RenderEngine.getInstance().getCurrentLevelRenderer().getRenderablesContainer().renderObjs.get("greenblob").edit(
				RenderEngine.instance.createTexturedQuad("src/main/resources/image/entity/greenblob.png", pos, tex));
	}

	/**
	 * Handles the A keystroke
	 * 
	 * @param incr The amount to decrement the X pos of the player.
	 * @throws IOException Creating the new buffers to pass to OpenGL for the new
	 *                     position of the player.
	 */
	private void handleA(float incr) throws IOException {
		float[] gbPos = RenderEngine.getInstance().getCurrentLevelRenderer().getRenderablesContainer().renderObjs
				.get("greenblob").pos;
		float[] pos = incrBasicQuadX(gbPos, incr);
		float[] tex = RenderEngine.getInstance().getCurrentLevelRenderer().getRenderablesContainer().renderObjs
				.get("greenblob").tex;
		RenderEngine.getInstance().getCurrentLevelRenderer().getRenderablesContainer().renderObjs.get("greenblob").edit(
				RenderEngine.instance.createTexturedQuad("src/main/resources/image/entity/greenblob.png", pos, tex));
	}

	/**
	 * Handles the S keystroke
	 * 
	 * @param incr The amount to decrement the Y pos of the player.
	 * @throws IOException Creating the new buffers to pass to OpenGL for the new
	 *                     position of the player.
	 */
	private void handleS(float incr) throws IOException {
		float[] gbPos = RenderEngine.getInstance().getCurrentLevelRenderer().getRenderablesContainer().renderObjs
				.get("greenblob").pos;
		float[] pos = incrBasicQuadY(gbPos, incr);
		float[] tex = RenderEngine.getInstance().getCurrentLevelRenderer().getRenderablesContainer().renderObjs
				.get("greenblob").tex;
		RenderEngine.getInstance().getCurrentLevelRenderer().getRenderablesContainer().renderObjs.get("greenblob").edit(
				RenderEngine.instance.createTexturedQuad("src/main/resources/image/entity/greenblob.png", pos, tex));
	}

	/**
	 * Handles the D keystroke
	 * 
	 * @param incr The amount to increment the X pos of the player.
	 * @throws IOException Creating the new buffers to pass to OpenGL for the new
	 *                     position of the player.
	 */
	private void handleD(float incr) throws IOException {
		float[] gbPos = RenderEngine.getInstance().getCurrentLevelRenderer().getRenderablesContainer().renderObjs
				.get("greenblob").pos;
		float[] pos = incrBasicQuadX(gbPos, incr);
		float[] tex = RenderEngine.getInstance().getCurrentLevelRenderer().getRenderablesContainer().renderObjs
				.get("greenblob").tex;
		RenderEngine.getInstance().getCurrentLevelRenderer().getRenderablesContainer().renderObjs.get("greenblob").edit(
				RenderEngine.instance.createTexturedQuad("src/main/resources/image/entity/greenblob.png", pos, tex));
	}

	private float[] incrBasicQuadX(float[] pos, float i) {
		pos[0] = pos[0] + i;
		pos[2] = pos[2] + i;
		pos[4] = pos[4] + i;
		pos[6] = pos[6] + i;
		pos[8] = pos[8] + i;
		pos[10] = pos[10] + i;
		return pos;
	}

	private float[] incrBasicQuadY(float[] pos, float i) {
		pos[1] = pos[1] + i;
		pos[3] = pos[3] + i;
		pos[5] = pos[5] + i;
		pos[7] = pos[7] + i;
		pos[9] = pos[9] + i;
		pos[11] = pos[11] + i;
		return pos;
	}

	/**
	 * @param key The key to test against
	 * @return Whether the current pressed key is the same as the key parameter
	 */
	private boolean keyIs(int key) {
		if (glfwGetKey(window, key) == GLFW_TRUE)
			return true;
		else
			return false;
	}

	public static void setWindow(long window) {
		KeyHandler.window = window;
	}

}
