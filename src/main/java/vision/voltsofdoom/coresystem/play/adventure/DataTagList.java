package vision.voltsofdoom.coresystem.play.adventure;

import java.util.List;

/**
 * Represents a <code>data</code> tag.
 * 
 * @author GenElectrovise
 *
 */
public class DataTagList {
	private List<DataTag> tags;

	public DataTagList(List<DataTag> tags) {
		this.tags = tags;
	}

	public List<DataTag> getTags() {
		return tags;
	}
}
