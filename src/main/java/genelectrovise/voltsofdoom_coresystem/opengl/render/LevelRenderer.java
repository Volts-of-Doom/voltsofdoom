package genelectrovise.voltsofdoom_coresystem.opengl.render;

import java.io.IOException;

import genelectrovise.voltsofdoom_coresystem.opengl.RenderablesContainer;

public abstract class LevelRenderer {
	
	/**
	 * @return The instance of the current RenderablesContainer for this LevelRenderer.
	 * @throws IOException If there is an error reading, for example, an image for a textured quad.
	 */
	public abstract RenderablesContainer getRenderablesContainer() throws IOException;

}
