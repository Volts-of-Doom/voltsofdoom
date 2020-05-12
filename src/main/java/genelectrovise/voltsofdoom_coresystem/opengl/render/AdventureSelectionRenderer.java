package genelectrovise.voltsofdoom_coresystem.opengl.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import genelectrovise.voltsofdoom_coresystem.adventure.Adventure;
import genelectrovise.voltsofdoom_coresystem.main.VODCoreSystemStart;
import genelectrovise.voltsofdoom_coresystem.opengl.RenderEngine;
import genelectrovise.voltsofdoom_coresystem.opengl.RenderablesContainer;
import genelectrovise.voltsofdoom_coresystem.util.Reference;

public final class AdventureSelectionRenderer extends LevelRenderer {
	private static final float[] TEX = new float[] { 0f, 1f, 1f, 1f, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f };

	private float yIncr = 0.1f;

	private ArrayList<Adventure> adventures;
	private HashMap<String, String> textureLocations = null;
	RenderablesContainer container = new RenderablesContainer();

	public AdventureSelectionRenderer() throws IOException {
		adventures = VODCoreSystemStart.getSystemControl().getAdventureloader().getAdventures();
		textureLocations = buildTexturesForStrings(adventures);

		generateRenderables();
	}

	private HashMap<String, String> buildTexturesForStrings(ArrayList<Adventure> adventures) {
		HashMap<String, String> imagePaths = new HashMap<String, String>();

		for (Adventure adventure : adventures) {
			String displayName = adventure.getDisplayname();

			try {

				Character chars[] = stringToCharArr(displayName);
				BufferedImage base = new BufferedImage(FontHandler.TEXT_WIDTH * chars.length, FontHandler.TEXT_HEIGHT,
						1);

				int letterOffsetX = 0;
				int letterOffsetY = 0;
				
				File out = null;
				
				for (Character c : chars) {
					c = Character.toLowerCase(c);

					File textureFile = new File(
							"src/main/resources/font/adventure/grey/letter_" + Character.toLowerCase(c) + ".png");
					File absolute = new File(textureFile.getAbsolutePath());

					BufferedImage patch = ImageIO.read(absolute);

					Graphics2D g = base.createGraphics();
					g.setBackground(Color.CYAN);
					g.drawImage(patch.getScaledInstance(FontHandler.TEXT_WIDTH, FontHandler.TEXT_HEIGHT, 16),
							letterOffsetX * 16, letterOffsetY * 16, null);

					letterOffsetX++;
				}

				out = new File(Reference.ROAMING + "img/font/out/" + adventure.getRegistryname() + ".png");
				ImageIO.write(base, "png", out);
				imagePaths.put(adventure.getRegistryname(), out.getAbsolutePath());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return imagePaths;
	}

	private Character[] stringToCharArr(String str) {
		int l = str.length();
		Character chars[] = new Character[l];
		for (int i = 0; i < l; i++) {
			chars[i] = str.charAt(i);
		}
		return chars;
	}

	private RenderablesContainer generateRenderables() throws IOException {
		for (Adventure adventure : adventures) {
			createAdventureSelectionRenderableObj(adventure, container);
		}

		return container;
	}

	@Override
	public RenderablesContainer getRenderablesContainer() throws IOException {
		return container;
	}

	private void createAdventureSelectionRenderableObj(Adventure adventure, RenderablesContainer container)
			throws IOException {

		float pos[] = generatePos();
		float miniPos[] = new float[] { -0.25f, -0.05f, 0.25f, -0.05f, 0.25f, 0.05f, 0.25f, 0.05f, -0.25f, 0.05f,
				-0.25f, -0.05f };

		container.addRenderObj("background",
				RenderEngine.instance.createTexturedQuad("src/main/resources/image/gui/component/cobble_bg.png",
						RenderEngine.POS_COORDS_FULL_SCREEN, RenderEngine.TEX_COORDS_FULL_SCREEN));

		container.addRenderObj(adventure.getRegistryname(), RenderEngine.instance
				.createTexturedQuad("src/main/resources/image/gui/component/adventure_button.png", pos, TEX));

		container.addRenderObj("text_" + adventure.getRegistryname(), RenderEngine.instance
				.createTexturedQuad(textureLocations.get(adventure.getRegistryname()), miniPos, TEX));
	}

	private float[] generatePos() {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(12);

		buffer.put(-0.5f);
		buffer.put(-0.1f);

		buffer.put(0.5f);
		buffer.put(-0.1f);

		buffer.put(0.5f);
		buffer.put(0.1f);

		buffer.put(0.5f);
		buffer.put(0.1f);

		buffer.put(-0.5f);
		buffer.put(0.1f);

		buffer.put(-0.5f);
		buffer.put(-0.1f);

		yIncr = yIncr - 0.1f;

		float out[] = new float[buffer.capacity()];
		for (int i = 0; i < buffer.capacity(); i++) {
			out[i] = buffer.get(i);
		}
		return out;
	}

}
