package vision.voltsofdoom.coresystem.universal.bgfx;

import org.lwjgl.BufferUtils;
import org.lwjgl.bgfx.*;
import org.lwjgl.glfw.*;
import org.lwjgl.system.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.*;

import javax.imageio.ImageIO;

import static org.lwjgl.bgfx.BGFX.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * https://github.com/bkaradzic/bgfx/blob/master/examples/00-helloworld/helloworld.cpp
 * 
 * @author GenElectrovise
 *
 */
public class RenderTest {

	public static void main(String[] args) {
		int width = 1024;
		int height = 480;

		// Try initialising GLFW window
		if (!glfwInit()) {
			throw new RuntimeException("Error initializing GLFW");
		}

		// the client (renderer) API is managed by bgfx
		glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);
		if (Platform.get() == Platform.MACOSX) {
			glfwWindowHint(GLFW_COCOA_RETINA_FRAMEBUFFER, GLFW_FALSE);
		}

		// Create window. If NULL, throw error.
		long window = glfwCreateWindow(width, height, "25-C99", 0, 0);
		if (window == NULL) {
			throw new RuntimeException("Error creating GLFW window");
		}

		// Set key callback
		glfwSetKeyCallback(window, (windowHnd, key, scancode, action, mods) -> {

			// If releasing, return
			if (action != GLFW_RELEASE) {
				return;
			}

			// Else, switch the pressed key
			switch (key) {

			// ESC tells window to close
			case GLFW_KEY_ESCAPE:
				glfwSetWindowShouldClose(windowHnd, true);
				break;
			}
		});

		// Refresh the memory stack
		try (MemoryStack stack = stackPush()) {

			// Create an Init obj from the stack
			BGFXInit init = BGFXInit.mallocStack(stack);

			// Updates the specified initialization parameters with default values.
			bgfx_init_ctor(init);

			// Sets the screen resolution
			init.resolution(it -> it.width(width).height(height).reset(BGFX_RESET_VSYNC));

			// Get the platform, and set the BGFX platform data.
			switch (Platform.get()) {
			case LINUX:
				init.platformData().ndt(GLFWNativeX11.glfwGetX11Display()).nwh(GLFWNativeX11.glfwGetX11Window(window));
				break;
			case MACOSX:
				init.platformData().nwh(GLFWNativeCocoa.glfwGetCocoaWindow(window));
				break;
			case WINDOWS:
				init.platformData().nwh(GLFWNativeWin32.glfwGetWin32Window(window));
				break;
			}

			// If fails to initialise BGFX, throw runtime exception
			if (!bgfx_init(init)) {
				throw new RuntimeException("Error initializing bgfx renderer");
			}
		}

		System.out.println("bgfx renderer: " + bgfx_get_renderer_name(bgfx_get_renderer_type()));

		// Enable debug text.
		bgfx_set_debug(BGFX_DEBUG_TEXT);

		// Sets "view clear" flags. I assume this is the default background?
		bgfx_set_view_clear(0, BGFX_CLEAR_COLOR | BGFX_CLEAR_DEPTH, 0x303030ff, 1.0f, 0);

		// Load the image.
		ByteBuffer logo = Logo.createLogo();

		int[] ints = new int[] {};

		try {

			File imageFile = new File("C:\\Users\\adam_\\OneDrive\\Desktop\\Pictures\\color_testing.png");

			BufferedImage img = ImageIO.read(imageFile);

			ints = new int[img.getWidth() * img.getHeight()];
			ints = ImageIO.read(imageFile).getData().getPixels(0, 0, img.getWidth(), img.getHeight(), (int[]) null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		ByteBuffer intBuffer = BufferUtils.createByteBuffer(ints.length);

		for (int i : ints) {
			intBuffer.put((byte) i);
		}

		intBuffer.flip();

		// Run render loop
		while (!glfwWindowShouldClose(window)) {
			glfwPollEvents();

			// Set view 0 default viewport.
			bgfx_set_view_rect(0, 0, 0, width, height);

			// This dummy draw call is here to make sure that view 0 is cleared
			// if no other draw calls are submitted to view 0.
			bgfx_touch(0);

			// Use debug font to print information about this example.

			// Clear internal text buffer
			bgfx_dbg_text_clear(0, false);
			// Draw image to internal text buffer from a byte buffer
			bgfx_dbg_text_image(Math.max(width / 2 / 8, 20) - 20, Math.max(height / 2 / 16, 6) - 6, 40, 12, logo, 160);
			bgfx_dbg_text_image(Math.max(width / 2 / 8, 20) - 20, Math.max(height / 2 / 16, 6) - 6, 40, 12, intBuffer,
					160);
			// Prints to internal text-character buffer
			bgfx_dbg_text_printf(0, 1, 0x1f, "bgfx/examples/25-c99");
			bgfx_dbg_text_printf(0, 2, 0x3f, "Description: Initialization and debug text with C99 API.");

			bgfx_dbg_text_printf(0, 3, 0x0f,
					"Color can be changed with ANSI \u001b[9;me\u001b[10;ms\u001b[11;mc\u001b[12;ma\u001b[13;mp\u001b[14;me\u001b[0m code too.");

			bgfx_dbg_text_printf(80, 4, 0x0f,
					"\u001b[;0m    \u001b[;1m    \u001b[; 2m    \u001b[; 3m    \u001b[; 4m    \u001b[; 5m    \u001b[; 6m    \u001b[; 7m    \u001b[0m");
			bgfx_dbg_text_printf(80, 5, 0x0f,
					"\u001b[;8m    \u001b[;9m    \u001b[;10m    \u001b[;11m    \u001b[;12m    \u001b[;13m    \u001b[;14m    \u001b[;15m    \u001b[0m");

			// Advance to next frame. Rendering thread will be kicked to
			// process submitted rendering primitives.
			bgfx_frame(false);
		}

		bgfx_shutdown();

		glfwDestroyWindow(window);
		glfwTerminate();
	}
}