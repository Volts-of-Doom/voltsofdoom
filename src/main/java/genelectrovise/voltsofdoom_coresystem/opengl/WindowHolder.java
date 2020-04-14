package genelectrovise.voltsofdoom_coresystem.opengl;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.*;

import genelectrovise.voltsofdoom_coresystem.log.VODLog4J;
import genelectrovise.voltsofdoom_coresystem.opengl.render.LoadingScreenRenderer;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30C.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Holds the game window, meaning that it is the manager and delegator for the rendering/game loop.
 * @author adam_
 *
 */
public class WindowHolder extends Thread {

	public static final boolean SHOULD_DO_OPENGL_DEBUG = false;

	long window;
	int width = 1200;
	int height = 720;

	GLCapabilities caps;
	GLFWKeyCallback keyCallback;
	Callback debugProc;

	/**
	 * Handles initialisation of the game window using GLFW.
	 */
	private void init() {
		glfwInit();
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		window = glfwCreateWindow(width, height, "Volts of Doom", NULL, NULL);

		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
			public void invoke(long window, int key, int scancode, int action, int mods) {
				KeyHandler.instance.handle(window, key, scancode, action, mods);
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

		if (SHOULD_DO_OPENGL_DEBUG) {
			debugProc = GLUtil.setupDebugMessageCallback();
			VODLog4J.LOGGER.warn("OpenGL debugging is enabled! This may cause some flooding....");
		} else {
			VODLog4J.LOGGER
					.warn("OpenGL debugging is disabled! This may hide the creation of excessive quantities of data!");
		}

	}

	/**
	 * The main game loop. Handles events in the window and delegates them to their
	 * handlers/engines.
	 */
	private void loop() {
		while (!glfwWindowShouldClose(window)) {
			glfwPollEvents();
			glViewport(0, 0, width, height);
			RenderEngine.instance.render();
			KeyHandler.setWindow(window);
			KeyHandler.instance.handle();
			glfwSwapBuffers(window);
		}
	}

	/**
	 * Begins initialising the game window. Called when the render thread is
	 * started.
	 */
	public void run() {

		try {
			init();

			RenderEngine.instance.setCurrentLevelRenderer(new LoadingScreenRenderer());

			loop();
			if (debugProc != null)
				debugProc.free();
			keyCallback.free();
			glfwDestroyWindow(window);
			glfwTerminate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
