package vision.voltsofdoom.coresystem.universal.resource.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import vision.voltsofdoom.coresystem.play.tile.Tile;
import vision.voltsofdoom.coresystem.universal.util.Reference;
import vision.voltsofdoom.coresystem.play.adventure.levelcontainer.LevelMap;

public class LevelTextureStitcher {
	private LevelMap levelmap;
	private StitchedLevelTexture stitchedLevelTexture;
	private ArrayList<BufferedImage> bufferedImages = new ArrayList<BufferedImage>();

	public LevelTextureStitcher(LevelMap levelmap) {
		this.levelmap = levelmap;
		stitchedLevelTexture = new StitchedLevelTexture(levelmap.getMeta());
	}

	public BufferedImage stitchAll() {
		try {
			fetchBufferedImagesFromTileMap();

			for (int i = 0; i < bufferedImages.size(); i++) {
				stitch(bufferedImages.get(i), i);
			}

			File file = new File(
					Reference.ROAMING + "img/level/out/" + levelmap.getMeta().getName() + "_stitchedLevel.png");
			file.mkdirs();
			ImageIO.write(stitchedLevelTexture.getStitchedLevelTexture(), "png", file);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return stitchedLevelTexture.getStitchedLevelTexture();
	}

	public void fetchBufferedImagesFromTileMap() {
		for (ArrayList<Tile> arr : levelmap.getTileMap()) {
			for (Tile tile : arr) {
				try {
					Tile.Properties p = tile.getProperties();
					bufferedImages.add(ImageIO.read(p.getImage().getUrl()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void stitch(BufferedImage addon, int index) {

		Graphics2D g2d = stitchedLevelTexture.getStitchedLevelTexture().createGraphics();

		// (index / gridwidth), rounded down
		int rowsAbove = (int) Math.floor(index / levelmap.getMeta().getGridwidth());
		int posY = rowsAbove;
		int posX = index - (rowsAbove * levelmap.getMeta().getGridwidth());

		g2d.drawImage(addon.getScaledInstance(64, 64, 8), posX * StitchedLevelTexture.TILE_SIZE_CONSTANT,
				posY * StitchedLevelTexture.TILE_SIZE_CONSTANT, null);
	}
}
