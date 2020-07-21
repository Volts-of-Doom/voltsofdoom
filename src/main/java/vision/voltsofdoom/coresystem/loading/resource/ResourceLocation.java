package vision.voltsofdoom.coresystem.loading.resource;

import java.util.function.IntPredicate;

public class ResourceLocation {

	private String modid;
	private String entry;

	public ResourceLocation(String modid, String entry) {
		this.modid = modid;
		this.entry = entry;
	}

	public String getEntry() {
		return entry;
	}

	public String getModid() {
		return modid;
	}

	public static boolean validate(ResourceLocation resource) {
		return resource.toString().chars().anyMatch(new IntPredicate() {
			@Override
			public boolean test(int value) {
				return true;
			}
		});
	}

	@Override
	public String toString() {
		return modid + ":" + entry;
	}

	public String absolute() {
		return null;
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof ResourceLocation)) {
			return false;
		}

		if (!((ResourceLocation) obj).getModid().equals(modid)) {
			return false;
		}
		
		if (!((ResourceLocation) obj).getEntry().equals(entry)) {
			return false;
		}

		return true;
	}
}
