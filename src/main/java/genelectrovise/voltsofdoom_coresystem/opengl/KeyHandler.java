package genelectrovise.voltsofdoom_coresystem.opengl;

import static org.lwjgl.glfw.GLFW.*;

import java.io.IOException;

public class KeyHandler {

	public static KeyHandler instance = new KeyHandler();
	public static long window;

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

	private void handleW(float incr) throws IOException {
		float[] gbPos = RenderablesContainer.instance.renderObjs.get("greenblob").pos;
		float[] pos = incrBasicQuadY(gbPos, incr);
		float[] tex = RenderablesContainer.instance.renderObjs.get("greenblob").tex;
		RenderableObj obj = RenderEngine.instance.createTexturedQuad("src/main/resources/image/entity/greenblob.png", pos,
				tex);

		RenderablesContainer.instance.renderObjs.put("greenblob", obj);
	}

	private void handleA(float incr) throws IOException {
		float[] gbPos = RenderablesContainer.instance.renderObjs.get("greenblob").pos;
		float[] pos = incrBasicQuadX(gbPos, incr);
		float[] tex = RenderablesContainer.instance.renderObjs.get("greenblob").tex;
		RenderableObj obj = RenderEngine.instance.createTexturedQuad("src/main/resources/image/entity/greenblob.png", pos,
				tex);

		RenderablesContainer.instance.renderObjs.put("greenblob", obj);
	}

	private void handleS(float incr) throws IOException {
		float[] gbPos = RenderablesContainer.instance.renderObjs.get("greenblob").pos;
		float[] pos = incrBasicQuadY(gbPos, incr);
		float[] tex = RenderablesContainer.instance.renderObjs.get("greenblob").tex;
		RenderableObj obj = RenderEngine.instance.createTexturedQuad("src/main/resources/image/entity/greenblob.png", pos,
				tex);

		RenderablesContainer.instance.renderObjs.put("greenblob", obj);
	}

	private void handleD(float incr) throws IOException {
		float[] gbPos = RenderablesContainer.instance.renderObjs.get("greenblob").pos;
		float[] pos = incrBasicQuadX(gbPos, incr);
		float[] tex = RenderablesContainer.instance.renderObjs.get("greenblob").tex;
		RenderableObj obj = RenderEngine.instance.createTexturedQuad("src/main/resources/image/entity/greenblob.png", pos,
				tex);

		RenderablesContainer.instance.renderObjs.put("greenblob", obj);
	}

	private float[] incrBasicQuadX(float[] pos, float i) {
		pos[0] = pos[0] + i;
		pos[2] = pos[2] + i;
		pos[4] = pos[4] + i;
		pos[6] = pos[6] + i;
		pos[8] = pos[8] + i;
		pos[10] = pos[10] + i;
		for (float fl : pos) {
			System.out.println(fl);
		}
		return pos;
	}

	private float[] incrBasicQuadY(float[] pos, float i) {
		pos[1] = pos[1] + i;
		pos[3] = pos[3] + i;
		pos[5] = pos[5] + i;
		pos[7] = pos[7] + i;
		pos[9] = pos[9] + i;
		pos[11] = pos[11] + i;
		for (float fl : pos) {
			System.out.println(fl);
		}
		return pos;
	}

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
