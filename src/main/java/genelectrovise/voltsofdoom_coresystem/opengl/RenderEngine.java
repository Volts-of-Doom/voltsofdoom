package genelectrovise.voltsofdoom_coresystem.opengl;

import static genelectrovise.voltsofdoom_coresystem.opengl.util.DemoUtils.createShader;
import static genelectrovise.voltsofdoom_coresystem.opengl.util.IOUtil.ioResourceToByteBuffer;
import static org.lwjgl.opengl.GL30C.*;
import static org.lwjgl.stb.STBImage.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class RenderEngine {
	public static RenderEngine instance = new RenderEngine();

	int quadProgram_inputPosition;
	int quadProgram_inputTextureCoords;

	public static RenderEngine getInstance() {
		return instance;
	}

	public RenderableObj createTexturedQuad(String resource, float[] pos, float[] tex) throws IOException {
		int texture = createTexture(resource);
		int program = createQuadProgram();
		int vao = createFullScreenQuad(pos, tex);
		return new RenderableObj(vao, program, texture, pos, tex);
	}

	int createTexture(String resource) throws IOException {
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer components = BufferUtils.createIntBuffer(1);
		ByteBuffer data = stbi_load_from_memory(ioResourceToByteBuffer(resource, 1024), width, height, components, 4);
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

	int createQuadProgram() throws IOException {
		int program = glCreateProgram();
		int vshader = createShader("src/main/resources/opengl/shader/vertex/texturedQuad.vs",
				GL_VERTEX_SHADER);
		int fshader = createShader("src/main/resources/opengl/shader/fragment/texturedQuad.fs",
				GL_FRAGMENT_SHADER);
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

	int createFullScreenQuad(float[] pos, float[] tex) {
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

		return vao;
	}

	void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		updateRenderables();

		for (RenderableObj obj : RenderablesContainer.instance.renderObjs.values()) {
			glBindTexture(GL_TEXTURE_2D, obj.texture);
			glUseProgram(obj.program);
			glBindVertexArray(obj.vao);
			glDrawArrays(GL_TRIANGLES, 0, 6);
		}

		glBindVertexArray(0);
		glUseProgram(0);
	}

	private void updateRenderables() {

	}
}
