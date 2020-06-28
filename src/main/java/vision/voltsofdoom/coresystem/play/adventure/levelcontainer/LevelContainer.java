package vision.voltsofdoom.coresystem.play.adventure.levelcontainer;

import vision.voltsofdoom.coresystem.loading.resource.external.json.VODJsonReader;
import vision.voltsofdoom.coresystem.loading.resource.image.LevelTextureStitcher;

public class LevelContainer {
	private LevelMeta meta;
	private LevelMap map;
	private LevelTextureStitcher stitcher;

	/**
	 * A container for a level. Contains all resources for a level.
	 * 
	 * @param reader A VODJsonReader containing an instance of the file the level
	 *               should be constructed from.
	 */
	public LevelContainer(VODJsonReader reader) {
		this.meta = new LevelMeta(reader);
		this.map = new LevelMap(reader, meta);
		stitcher = new LevelTextureStitcher(map);
		
		stitcher.stitchAll();
	}

	public LevelMeta getMeta() {
		return meta;
	}

	public LevelMap getMap() {
		return map;
	}
}
