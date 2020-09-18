package vision.voltsofdoom.api.zapyte.misc.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface IVODJsonReader {

	/**
	 * @param key A String value to get the JsonElement of.
	 * @return A JsonElement associated with the name of the key.
	 */
	JsonElement fromKey(String key);

	JsonObject getObj();

}