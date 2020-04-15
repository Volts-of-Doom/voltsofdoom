package genelectrovise.voltsofdoom_coresystem.opengl.render;

import java.io.IOException;

import genelectrovise.voltsofdoom_coresystem.opengl.RenderEngine;
import genelectrovise.voltsofdoom_coresystem.opengl.RenderablesContainer;
import genelectrovise.voltsofdoom_coresystem.util.Math;
import genelectrovise.voltsofdoom_coresystem.util.Math.Dimensions.EnumAxis;

/**
 * The {@link LevelRenderer} for the loading screen.
 * 
 * @author adam_
 *
 */
public class LoadingScreenRenderer extends LevelRenderer {

	RenderablesContainer container = new RenderablesContainer();

	private static final float[] TEX_BG = new float[] { 0f, 1f, 1f, 1f, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f };
	private static final float[] POS_BG = new float[] { -1f, -1f, 1f, -1f, 1f, 1f, 1f, 1f, -1f, 1f, -1f, -1f };

	private static final float[] TEX_BLOB = new float[] { 0f, 1f, 1f, 1f, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f };
	private static final float[] POS_BLOB = new float[] {
			// 1 BL
			Math.Dimensions.pixelPosToFloatCenteredOnOrigin(EnumAxis.HOROZONTAL, 0),
			Math.Dimensions.pixelPosToFloatCenteredOnOrigin(EnumAxis.VERTICAL, 0),
			// 2 BR
			Math.Dimensions.pixelPosToFloatCenteredOnOrigin(EnumAxis.HOROZONTAL, 256),
			Math.Dimensions.pixelPosToFloatCenteredOnOrigin(EnumAxis.VERTICAL, 0),
			// 3 TR
			Math.Dimensions.pixelPosToFloatCenteredOnOrigin(EnumAxis.HOROZONTAL, 256),
			Math.Dimensions.findSameDistanceOnOpposingAxis(EnumAxis.VERTICAL, Math.Dimensions.pixelPosToFloatCenteredOnOrigin(EnumAxis.VERTICAL, 256)),
			// 4 TR
			Math.Dimensions.pixelPosToFloatCenteredOnOrigin(EnumAxis.HOROZONTAL, 256),
			Math.Dimensions.findSameDistanceOnOpposingAxis(EnumAxis.VERTICAL, Math.Dimensions.pixelPosToFloatCenteredOnOrigin(EnumAxis.VERTICAL, 256)),
			// 5 TL
			Math.Dimensions.pixelPosToFloatCenteredOnOrigin(EnumAxis.HOROZONTAL, 0),
			Math.Dimensions.findSameDistanceOnOpposingAxis(EnumAxis.VERTICAL, Math.Dimensions.pixelPosToFloatCenteredOnOrigin(EnumAxis.VERTICAL, 256)),
			// 6 BL
			Math.Dimensions.pixelPosToFloatCenteredOnOrigin(EnumAxis.HOROZONTAL, 0),
			Math.Dimensions.pixelPosToFloatCenteredOnOrigin(EnumAxis.VERTICAL, 0) };

	public LoadingScreenRenderer() throws IOException {
		container.addRenderObj("background",
				RenderEngine.instance.createTexturedQuad(
						"src/main/resources/image/environment/stitchedlevel/cobbleandwoodlog_stitchedLevel.png", POS_BG,
						TEX_BG));
		container.addRenderObj("greenblob", RenderEngine.instance
				.createTexturedQuad("src/main/resources/image/entity/entityblob.png", POS_BLOB, TEX_BLOB));
	}

	@Override
	public RenderablesContainer getRenderablesContainer() {
		updateContainer();
		return container;
	}

	private void updateContainer() {

	}

}
