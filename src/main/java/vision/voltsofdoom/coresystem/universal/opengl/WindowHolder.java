package vision.voltsofdoom.coresystem.universal.opengl;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.glfw.GLFW.nglfwGetFramebufferSize;
import static org.lwjgl.opengl.GL11C.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAddress;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;
import org.lwjgl.system.MemoryStack;

import vision.voltsofdoom.coresystem.play.key.KeyHandler;
import vision.voltsofdoom.coresystem.universal.log.VODLog4J;
import vision.voltsofdoom.coresystem.universal.main.SystemControl;
import vision.voltsofdoom.coresystem.universal.opengl.render.LoadingScreenRenderer;

/**
 * Holds the game window, meaning that it is the manager and delegator for the
 * rendering/game loop.
 * 
 * @author GenElectrovise
 *
 */
public class WindowHolder extends Thread {

	public static final boolean SHOULD_DO_OPENGL_DEBUG = false;

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
