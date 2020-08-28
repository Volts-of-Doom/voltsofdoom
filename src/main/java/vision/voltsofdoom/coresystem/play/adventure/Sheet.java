package vision.voltsofdoom.coresystem.play.adventure;

import vision.voltsofdoom.coresystem.universal.resource.ResourceLocation;

/**
 * Represents a <code>sheet</code> file from an {@link Adventure}.
 * 
 * @author GenElectrovise
 *
 */
public class Sheet {
	private ResourceLocation identifier;
	private DataTagList data;

	public Sheet(ResourceLocation identifier, DataTagList data) {
		this.identifier = identifier;
		this.data = data;
	}

	public ResourceLocation getIdentifier() {
		return identifier;
	}

	public DataTagList getData() {
		return data;
	}
}
