package vision.voltsofdoom.zapbyte.config;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ConfigurationFile {
	public static final ConfigurationFile BLANK = new ConfigurationFile();
	private Map<String, String> arguments;

	public void putArgument(String key, String value) {
		arguments.put(key, value);
	}

	public Map<String, String> getArguments() {
		return arguments;
	}

	public static ConfigurationFile fromJson(JsonObject jsonObject) {
		ConfigurationFile configurationFile = new Gson().fromJson(jsonObject, ConfigurationFile.class);
		return configurationFile;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
