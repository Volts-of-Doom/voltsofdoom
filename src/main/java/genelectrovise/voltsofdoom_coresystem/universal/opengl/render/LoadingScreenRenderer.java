package genelectrovise.voltsofdoom_coresystem.universal.opengl.render;

import java.io.IOException;

import genelectrovise.voltsofdoom_coresystem.universal.opengl.RenderEngine;
import genelectrovise.voltsofdoom_coresystem.universal.opengl.RenderablesContainer;

public class LoadingScreenRenderer extends LevelRenderer {

	RenderablesContainer container = new RenderablesContainer();

	private static final float[] TEX_BG = new float[] { 0f, 1f, 1f, 1f, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f };
	private static final float[] POS_BG = new float[] { -1f, -1f, 1f, -1f, 1f, 1f, 1f, 1f, -1f, 1f, -1f, -1f };

	public LoadingScreenRenderer() throws IOException {

		container.addRenderObj("logo", RenderEngine.instance
				.createTexturedQuad("src/main/resources/image/tile/voltsofdoom_2-1.png", POS_BG, TEX_BG));
	}

	@Override
	public RenderablesContainer getRenderablesContainer() {
		updateContainer();
		return container;
	}

	private void updateContainer() {

	}

}
