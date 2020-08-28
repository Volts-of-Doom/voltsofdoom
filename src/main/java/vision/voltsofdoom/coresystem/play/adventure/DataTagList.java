package vision.voltsofdoom.coresystem.play.adventure;

import java.util.List;

/**
 * Represents a <code>data</code> tag.
 * 
 * @author GenElectrovise
 *
 */
public class DataTagList {
	private List<?> tags;

	public DataTagList(List<?> tags) {
		this.tags = tags;
	}

	public List<?> getTags() {
		return tags;
	}
}
