package vision.voltsofdoom.coresystem.play.adventure;

import vision.voltsofdoom.zapbyte.misc.ResourceLocation;

public class KeyNode {
	private String key;
	private ResourceLocation identifier;
	private DataTagList data;
	private Sheet sheet;
	
	public String getKey() {
		return key;
	}
	
	public ResourceLocation getIdentifier() {
		return identifier;
	}
	
	public DataTagList getData() {
		return data;
	}
	
	public Sheet getSheet() {
		return sheet;
	}
}
