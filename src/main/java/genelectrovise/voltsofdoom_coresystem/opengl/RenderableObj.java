package genelectrovise.voltsofdoom_coresystem.opengl;

/**
 * Lemme guess.... an object which you can render.s
 * 
 * @author adam_
 *
 */
public class RenderableObj {
	/** @see RenderableObj */
	int vao;
	/** @see RenderableObj */
	int program;
	/** @see RenderableObj */
	int texture;
	/** @see RenderableObj */
	float[] pos;
	/** @see RenderableObj */
	float[] tex;

	/**
	 * @param vao     The ID of the OpenGL VAO to use.
	 * @param program The ID of the OpenGL Shader Program to use.
	 * @param texture The ID of the OpenGL Texture to use.
	 * @param pos     An array of floats denoting this objects postion.
	 * @param tex     Another array of floats showing the coords of this obj's
	 *                texture in relation to this obj.
	 */
	public RenderableObj(int vao, int program, int texture, float[] pos, float[] tex) {
		this.vao = vao;
		this.program = program;
		this.texture = texture;
		this.pos = pos;
		this.tex = tex;
	}

	/**
	 * Rewrites this object with the given params. Handy for if you want to change
	 * this whilst it's tied up elsewhere :)
	 * 
	 * @param obj The object for this to mirror
	 * @return This once it mirrors the param obj.
	 */
	public RenderableObj edit(RenderableObj obj) {
		this.vao = obj.vao;
		this.program = obj.program;
		this.texture = obj.texture;
		this.pos = obj.pos;
		this.tex = obj.tex;

		return this;
	}
}