/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package genelectrovise.voltsofdoom_coresystem.opengl;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.*;
import java.io.IOException;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30C.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Renders a simple textured quad using OpenGL 3.0.
 * 
 * @author Kai Burjack
 */
public class WindowHolder {

	private static final float[] TEX_BG = new float[] { 0f, 1f, 1f, 1f, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f };
	private static final float[] POS_BG = new float[] { -1f, -1f, 1f, -1f, 1f, 1f, 1f, 1f, -1f, 1f, -1f, -1f };

	private static final float[] TEX_BLOB = new float[] { 0f, 1f, 1f, 1f, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f };
	private static final float[] POS_BLOB = new float[] { -0.25f, -0.25f, 0.25f, -0.25f, 0.25f, 0.25f, 0.25f, 0.25f,
			-0.25f, 0.25f, -0.25f, -0.25f };

	long window;
	int width = 1200;
	int height = 720;

	GLCapabilities caps;
	GLFWKeyCallback keyCallback;
	Callback debugProc;

	void init() throws IOException {
		glfwInit();
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		window = glfwCreateWindow(width, height, "Window 11 (BG w Green Blob)", NULL, NULL);
		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
					glfwSetWindowShouldClose(window, true);
			}
		});
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		try (MemoryStack frame = MemoryStack.stackPush()) {
			IntBuffer framebufferSize = frame.mallocInt(2);
			nglfwGetFramebufferSize(window, memAddress(framebufferSize), memAddress(framebufferSize) + 4);
			width = framebufferSize.get(0);
			height = framebufferSize.get(1);
		}
		caps = GL.createCapabilities();
		debugProc = GLUtil.setupDebugMessageCallback();

		RenderablesContainer.instance.addRenderObj("background", RenderEngine.instance
				.createTexturedQuad("src/main/resources/image/environment/stitchedlevel/cobbleandwoodlog_stitchedLevel.png", POS_BG, TEX_BG));
		RenderablesContainer.instance.addRenderObj("greenblob",
				RenderEngine.instance.createTexturedQuad("src/main/resources/image/entity/greenblob.png", POS_BLOB, TEX_BLOB));
	}

	void loop() {
		while (!glfwWindowShouldClose(window)) {
			glfwPollEvents();
			glViewport(0, 0, width, height);
			RenderEngine.instance.render();
			KeyHandler.setWindow(window);
			KeyHandler.instance.handle();
			glfwSwapBuffers(window);
		}
	}

	void run() throws IOException {
		init();
		loop();
		if (debugProc != null)
			debugProc.free();
		keyCallback.free();
		glfwDestroyWindow(window);
		glfwTerminate();
	}

	public static void main(String[] args) throws IOException {
		new WindowHolder().run();
	}
}
