package vision.voltsofdoom.coresystem.loading.resource.image;

import java.awt.image.BufferedImage;

import vision.voltsofdoom.coresystem.play.adventure.levelcontainer.LevelMeta;

public class StitchedLevelTexture {
	private LevelMeta meta;
	private BufferedImage stitchedLevelTexture;
	public static final int TILE_SIZE_CONSTANT = 64;

	public StitchedLevelTexture(LevelMeta meta) {
		this.setMeta(meta);
		this.stitchedLevelTexture = new BufferedImage(meta.getGridwidth() * TILE_SIZE_CONSTANT, meta.getGridheight() * TILE_SIZE_CONSTANT,
				BufferedImage.TYPE_INT_RGB);
	}
	
	public BufferedImage getStitchedLevelTexture() {
		return stitchedLevelTexture;
	}

	/**
	 * @return the meta
	 */
	public LevelMeta getMeta() {
		return meta;
	}

	/**
	 * @param meta the meta to set
	 */
	public void setMeta(LevelMeta meta) {
		this.meta = meta;
	}
}
