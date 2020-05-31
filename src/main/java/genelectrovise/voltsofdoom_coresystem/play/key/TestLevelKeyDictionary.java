package genelectrovise.voltsofdoom_coresystem.play.key;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import java.io.IOException;

import genelectrovise.voltsofdoom_coresystem.play.adventure.levelcontainer.KeyDictionary;
import genelectrovise.voltsofdoom_coresystem.universal.opengl.RenderEngine;
import genelectrovise.voltsofdoom_coresystem.universal.util.Math;

public class TestLevelKeyDictionary extends KeyDictionary {

	public TestLevelKeyDictionary(long window) {
		super(window);
	}

	@Override
	public boolean handle() {
		try {
			if (keyIs(GLFW_KEY_W))
				return handleW(0.01f);
			if (keyIs(GLFW_KEY_A))
				return handleA(-0.01f);
			if (keyIs(GLFW_KEY_S))
				return handleS(-0.01f);
			if (keyIs(GLFW_KEY_D))
				return handleD(0.01f);
			return false;

		} catch (Exception e) {
			System.err.println("Error handling keystrokes! : ");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Handles the W keystroke
	 * 
	 * @param incr The amount to increment the Y pos of the player.
	 * @throws IOException Creating the new buffers to pass to OpenGL for the new
	 *                     position of the player.
	 */
	private boolean handleW(float incr) throws IOException {
		float[] gbPos = RenderEngine.instance.getCurrentLevelRenderer().getRenderablesContainer().renderObjs
				.get("greenblob").pos;
		float[] pos = Math.Geometry.incrBasicQuadY(gbPos, incr);
		float[] tex = RenderEngine.instance.getCurrentLevelRenderer().getRenderablesContainer().renderObjs
				.get("greenblob").tex;

		RenderEngine.instance.getCurrentLevelRenderer().getRenderablesContainer().renderObjs.get("greenblob").edit(
				RenderEngine.instance.createTexturedQuad("src/main/resources/image/entity/greenblob.png", pos, tex));
		return true;
	}

	/**
	 * Handles the A keystroke
	 * 
	 * @param incr The amount to decrement the X pos of the player.
	 * @throws IOException Creating the new buffers to pass to OpenGL for the new
	 *                     position of the player.
	 */
	private boolean handleA(float incr) throws IOException {
		float[] gbPos = RenderEngine.instance.getCurrentLevelRenderer().getRenderablesContainer().renderObjs
				.get("greenblob").pos;
		float[] pos = Math.Geometry.incrBasicQuadX(gbPos, incr);
		float[] tex = RenderEngine.instance.getCurrentLevelRenderer().getRenderablesContainer().renderObjs
				.get("greenblob").tex;
		RenderEngine.instance.getCurrentLevelRenderer().getRenderablesContainer().renderObjs.get("greenblob").edit(
				RenderEngine.instance.createTexturedQuad("src/main/resources/image/entity/greenblob.png", pos, tex));
		return true;
	}

	/**
	 * Handles the S keystroke
	 * 
	 * @param incr The amount to decrement the Y pos of the player.
	 * @throws IOException Creating the new buffers to pass to OpenGL for the new
	 *                     position of the player.
	 */
	private boolean handleS(float incr) throws IOException {
		float[] gbPos = RenderEngine.instance.getCurrentLevelRenderer().getRenderablesContainer().renderObjs
				.get("greenblob").pos;
		float[] pos = Math.Geometry.incrBasicQuadY(gbPos, incr);
		float[] tex = RenderEngine.instance.getCurrentLevelRenderer().getRenderablesContainer().renderObjs
				.get("greenblob").tex;
		RenderEngine.instance.getCurrentLevelRenderer().getRenderablesContainer().renderObjs.get("greenblob").edit(
				RenderEngine.instance.createTexturedQuad("src/main/resources/image/entity/greenblob.png", pos, tex));
		return true;
	}

	/**
	 * Handles the D keystroke
	 * 
	 * @param incr The amount to increment the X pos of the player.
	 * @throws IOException Creating the new buffers to pass to OpenGL for the new
	 *                     position of the player.
	 */
	private boolean handleD(float incr) throws IOException {
		float[] gbPos = RenderEngine.instance.getCurrentLevelRenderer().getRenderablesContainer().renderObjs
				.get("greenblob").pos;
		float[] pos = Math.Geometry.incrBasicQuadX(gbPos, incr);
		float[] tex = RenderEngine.instance.getCurrentLevelRenderer().getRenderablesContainer().renderObjs
				.get("greenblob").tex;
		RenderEngine.instance.getCurrentLevelRenderer().getRenderablesContainer().renderObjs.get("greenblob").edit(
				RenderEngine.instance.createTexturedQuad("src/main/resources/image/entity/greenblob.png", pos, tex));
		return true;
	}

}
