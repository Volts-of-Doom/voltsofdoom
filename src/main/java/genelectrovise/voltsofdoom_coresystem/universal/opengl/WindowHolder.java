package genelectrovise.voltsofdoom_coresystem.universal.opengl;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.*;

import genelectrovise.voltsofdoom_coresystem.play.key.KeyHandler;
import genelectrovise.voltsofdoom_coresystem.play.key.LoadingScreenKeyDictionary;
import genelectrovise.voltsofdoom_coresystem.universal.log.VODLog4J;
import genelectrovise.voltsofdoom_coresystem.universal.main.SystemControl;
import genelectrovise.voltsofdoom_coresystem.universal.opengl.render.AdventureSelectionRenderer;
import genelectrovise.voltsofdoom_coresystem.universal.opengl.render.LoadingScreenRenderer;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30C.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Holds the game window, meaning that it is the manager and delegator for the
 * rendering/game loop.
 * 
 * @author adam_
 *
 */
public class WindowHolder extends Thread {

	public static final boolean SHOULD_DO_OPENGL_DEBUG = false;

	private SystemControl systemcontrol;

	long window;
	public int width = 1200;
	public int height = 720;
	
	public RenderEngine renderengine;

	GLCapabilities caps;
	GLFWKeyCallback keyCallback;
	GLFWWindowSizeCallback wsCallback;
	Callback debugProc;

	public WindowHolder(SystemControl systemControl) {
		setName("render");
		this.systemcontrol = systemControl;
		renderengine = RenderEngine.instance.setSystemControl(systemControl);
	}
	
	public long getWindow() {
		return window;
	}

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
		glfwSetWindowSizeCallback(window, wsCallback = new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int w, int h) {
				if (w > 0 && h > 0) {
					width = w;
					height = h;
				}
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
			KeyHandler.instance.handle();
			RenderEngine.instance.render();
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

			// RenderEngine.instance.setCurrentLevelRenderer(new TestLevelRenderer());
			 RenderEngine.instance.setCurrentLevelRenderer(new LoadingScreenRenderer());
			 
			KeyHandler.instance.setWindow(window);
			KeyHandler.instance.setCurrentKeyDictionary(KeyHandler.NO_HANDLER);

			loop();
			if (debugProc != null)
				debugProc.free();
			keyCallback.free();
			wsCallback.free();
			glfwDestroyWindow(window);
			glfwTerminate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
