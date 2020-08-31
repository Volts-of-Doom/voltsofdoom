package vision.voltsofdoom.coresystem.play.adventure;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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

	public ResourceLocation getIdentifier() {
		return identifier;
	}

	public DataTagList getData() {
		return data;
	}

	public static Sheet fromJson(JsonObject json) {
		return new Gson().fromJson(json, Sheet.class);
	}
}
