package genelectrovise.voltsofdoom_coresystem.opengl;
public class RenderableObj {
		int vao;
		int program;
		int texture;
		float[] pos;
		float[] tex;

		public RenderableObj(int vao, int program, int texture, float[] pos, float[] tex) {
			this.vao = vao;
			this.program = program;
			this.texture = texture;
			this.pos = pos;
			this.tex = tex;
		}
	}