package vision.voltsofdoom.coresystem.play.adventure.levelcontainer;

import vision.voltsofdoom.coresystem.loading.resource.json.VODJsonReader;

/**
 * A convenience class containing all of the metadata for a levelcontainer.
 * 
 * @author GenElectrovise
 *
 */
public class LevelMeta {
	private String name;
	private int gridwidth;
	private int gridheight;

	public LevelMeta(VODJsonReader json) {
		this.name = json.getObj().get("name").getAsString();
		this.gridwidth = json.getObj().get("gridwidth").getAsInt();
		this.gridheight = json.getObj().get("gridheight").getAsInt();
	}

	public String getName() {
		return name;
	}

	public int getGridwidth() {
		return gridwidth;
	}

	public int getGridheight() {
		return gridheight;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		String open = "Level : [";
		String close = "]";
		String concatName = "Name {" + name + "}, ";
		String concatWidth = "width=" + gridwidth;
		String concatHeight = "height=" + gridheight;
		String concatDimensions = "Grid Dimensions {" + concatWidth + ", " + concatHeight + "}";

		builder.append(open).append(concatName).append(concatDimensions).append(close);
		return builder.toString();
	}
}
