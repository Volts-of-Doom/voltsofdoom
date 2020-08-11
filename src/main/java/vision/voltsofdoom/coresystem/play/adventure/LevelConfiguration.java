package vision.voltsofdoom.coresystem.play.adventure;

import vision.voltsofdoom.coresystem.universal.resource.ResourceLocation;

public class LevelConfiguration {
	private ResourceLocation identifier;
	private String displayName;
	private String description;
	
	public LevelConfiguration withIdentifier(ResourceLocation identifier) {
		this.identifier = identifier;
		return this;
	}
	
	public ResourceLocation getIdentifier() {
		return identifier;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getDisplayName() {
		return displayName;
	}
}
