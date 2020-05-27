package genelectrovise.voltsofdoom_coresystem.universal.opengl;

import static genelectrovise.voltsofdoom_coresystem.universal.util.GLUtils.createShader;
import static genelectrovise.voltsofdoom_coresystem.universal.util.IOUtil.ioResourceToByteBuffer;
import static org.lwjgl.opengl.GL30C.*;
import static org.lwjgl.stb.STBImage.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import genelectrovise.voltsofdoom_coresystem.play.key.KeyHandler;
import genelectrovise.voltsofdoom_coresystem.play.key.TestLevelKeyDictionary;
import genelectrovise.voltsofdoom_coresystem.universal.main.SystemControl;
import genelectrovise.voltsofdoom_coresystem.universal.main.VODCoreSystemStart;
import genelectrovise.voltsofdoom_coresystem.universal.opengl.render.AdventureSelectionRenderer;
import genelectrovise.voltsofdoom_coresystem.universal.opengl.render.LevelRenderer;
import genelectrovise.voltsofdoom_coresystem.universal.opengl.render.LoadingScreenRenderer;
import genelectrovise.voltsofdoom_coresystem.universal.opengl.render.TestLevelRenderer;

/**
 * This is a big one. Handles and delegate the drawing of the game screen.
 * 
 * @author adam_
 *
 */
public class RenderEngine {
	public static RenderEngine instance = new RenderEngine();
	private boolean isSystemControlLoaded = false;

	public static final float[] TEX_COORDS_FULL_SCREEN = new float[] { 0f, 1f, 1f, 1f, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f };
	public static final float[] POS_COORDS_FULL_SCREEN = new float[] { -1f, -1f, 1f, -1f, 1f, 1f, 1f, 1f, -1f, 1f, -1f, -1f };

	/**
	 * The {@link LevelRenderer} to take objects from to draw.
	 */
	private LevelRenderer currentLevelRenderer;

	int quadProgram_inputPosition;
	int quadProgram_inputTextureCoords;
	private SystemControl systemcontrol;

	public RenderEngine getInstance() {
		return instance;
	}

	/**
	 * Creates a {@link RenderableObj} for a quad, including a texture
	 * 
	 * @param textureLocation Link to the location of the texture.
	 * @param pos             A float[] of positions for the vertices of this quad.
	 * @param tex             A float[] for the coords of the vertices of this
	 *                        quad's texture
	 * @return A {@link RenderableObj} filled with these parameters.
	 * @throws IOException If the texture can't be found.
	 */
	public RenderableObj createTexturedQuad(String textureLocation, float[] pos, float[] tex) throws IOException {
		int texture = createTexture(textureLocation);
		int program = createQuadProgram();
		int vao = createFullScreenQuad(pos, tex);
		return new RenderableObj(vao, program, texture, pos, tex);
	}

	/**
	 * @param textureLocation The location of the texture to load.
	 * @return An int ID for OpenGL to find the texture by.
	 * @throws IOException If the texture cannot be found.
	 */
	private int createTexture(String textureLocation) throws IOException {
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer components = BufferUtils.createIntBuffer(1);
		ByteBuffer data = stbi_load_from_memory(ioResourceToByteBuffer(textureLocation, 1024), width, height,
				components, 4);
		int id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
		stbi_image_free(data);
		return id;
	}

	/**
	 * Creates a new shader program for a quad.
	 * 
	 * @return An int ID for OpenGL to find the generated shader program by.
	 * @throws IOException If the shader files cannot be found.
	 */
	private int createQuadProgram() throws IOException {
		int program = glCreateProgram();
		int vshader = createShader("src/main/resources/opengl/shader/vertex/texturedQuad.vs", GL_VERTEX_SHADER);
		int fshader = createShader("src/main/resources/opengl/shader/fragment/texturedQuad.fs", GL_FRAGMENT_SHADER);
		glAttachShader(program, vshader);
		glAttachShader(program, fshader);
		glLinkProgram(program);
		int linked = glGetProgrami(program, GL_LINK_STATUS);
		String programLog = glGetProgramInfoLog(program);
		if (programLog.trim().length() > 0)
			System.err.println(programLog);
		if (linked == 0)
			throw new AssertionError("Could not link program");
		glUseProgram(program);
		int texLocation = glGetUniformLocation(program, "tex");
		glUniform1i(texLocation, 0);
		quadProgram_inputPosition = glGetAttribLocation(program, "position");
		quadProgram_inputTextureCoords = glGetAttribLocation(program, "texCoords");
		glUseProgram(0);
		return program;
	}

	/**
	 * Creates a quad using the provides coords
	 * 
	 * @param pos The coords of the vertices for this quad.
	 * @param tex The coords of the vertices of the texture of this quad.
	 * @return The ID of this quad's VAO
	 */
	private int createFullScreenQuad(float[] pos, float[] tex) {
		int vao = glGenVertexArrays();
		glBindVertexArray(vao);
		int positionVbo = glGenBuffers();

		FloatBuffer fb = BufferUtils.createFloatBuffer(2 * 6);
		fb.put(pos);

		fb.flip();
		glBindBuffer(GL_ARRAY_BUFFER, positionVbo);
		glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
		glVertexAttribPointer(quadProgram_inputPosition, 2, GL_FLOAT, false, 0, 0L);
		glEnableVertexAttribArray(quadProgram_inputPosition);
		int texCoordsVbo = glGenBuffers();

		fb = BufferUtils.createFloatBuffer(2 * 6);

		fb.put(tex);

		fb.flip();
		glBindBuffer(GL_ARRAY_BUFFER, texCoordsVbo);
		glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
		glVertexAttribPointer(quadProgram_inputTextureCoords, 2, GL_FLOAT, true, 0, 0L);
		glEnableVertexAttribArray(quadProgram_inputTextureCoords);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);

		glDeleteBuffers(positionVbo);
		glDeleteBuffers(texCoordsVbo);

		return vao;
	}

	/**
	 * A very important method. Renders everything in the current
	 * {@link LevelRenderer}'s {@link RenderablesContainer} using their shader
	 * program and textures.
	 */
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		testIsSystemControlLoaded();

		try {
			
			if(currentLevelRenderer instanceof AdventureSelectionRenderer) {
				System.out.println("RenderEngine.render()");
			}
			
			// for (RenderableObj obj : RenderablesContainer.instance.renderObjs.values()) {
			for (RenderableObj obj : currentLevelRenderer.getRenderablesContainer().renderObjs.values()) {
				glBindTexture(GL_TEXTURE_2D, obj.texture);
				glUseProgram(obj.program);
				glBindVertexArray(obj.vao);
				glDrawArrays(GL_TRIANGLES, 0, 6);
			}

			glBindVertexArray(0);
			glUseProgram(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void testIsSystemControlLoaded() {
		try {
			if (!isSystemControlLoaded) {
				isSystemControlLoaded = systemcontrol.loadingComplete;
			} else if (isSystemControlLoaded) {
				//setCurrentLevelRenderer(new TestLevelRenderer());
				//KeyHandler.instance
				//		.setCurrentKeyDictionary(new TestLevelKeyDictionary(systemcontrol.getWindowHolder().window));

				if(!(currentLevelRenderer instanceof AdventureSelectionRenderer)) {
					setCurrentLevelRenderer(new AdventureSelectionRenderer());
				}
				
				KeyHandler.instance.setCurrentKeyDictionary(KeyHandler.NO_HANDLER);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param renderer The new current {@link LevelRenderer}
	 */
	public void setCurrentLevelRenderer(LevelRenderer renderer) {
		currentLevelRenderer = renderer;
	}

	/**
	 * @return The current {@link LevelRenderer}
	 */
	public LevelRenderer getCurrentLevelRenderer() {
		return currentLevelRenderer;
	}

	public void queueNewLevelRenderer(LevelRenderer renderer) {
		currentLevelRenderer = renderer;
	}

	public RenderEngine setSystemControl(SystemControl systemcontrol) {
		this.systemcontrol = systemcontrol;
		return this;
	}
}
