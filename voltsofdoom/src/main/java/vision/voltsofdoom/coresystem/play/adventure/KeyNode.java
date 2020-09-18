package vision.voltsofdoom.coresystem.play.adventure;

import vision.voltsofdoom.api.zapyte.misc.IResourceLocation;

public class KeyNode {
	private String key;
	private IResourceLocation identifier;
	private DataTagList data;
	private Sheet sheet;
	
	public String getKey() {
		return key;
	}
	
	public IResourceLocation getIdentifier() {
		return identifier;
	}
	
	public DataTagList getData() {
		return data;
	}
	
	public Sheet getSheet() {
		return sheet;
	}
}
