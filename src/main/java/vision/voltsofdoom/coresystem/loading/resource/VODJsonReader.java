package vision.voltsofdoom.coresystem.loading.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import vision.voltsofdoom.coresystem.universal.util.Reference;

/**
 * A handy class for reading the contents of .json files. Should use convenience
 * methods in Gson ( eg getAsString and getAsInteger ) to get the contents of
 * obj ( obtained by getObj ) as your wanted type. For example: <br>
 * <br>
 * <code>double myDouble = obj.getAsDouble()</code> <br>
 * <br>
 * You could then append <code>.toString</code> to the end if you so wished.
 * 
 * @author GenElectrovise
 *
 */
public class VODJsonReader {
	private Gson gson;
	private File file;
	private JsonObject obj;

	/**
	 * @param file     .json file to read from
	 * @param reciever Class type to parse to.
	 */
	public VODJsonReader(File file) {
		this.gson = new Gson();
		this.file = file;
		this.obj = fileToJsonObj();
	}

	public static void main(String[] args) {
		VODJsonReader r = new VODJsonReader(new File(Reference.ROAMING
				+ "\\resources\\adventure\\voltsofdoom-coregame\\casketofazamgarath.json"));
		System.out.println(r.getObj().get("registryname").getAsString());
	}

	/**
	 * Takes the file and parses it into a JsonObject.
	 */
	private JsonObject fileToJsonObj() {
		try {

			return gson.fromJson(new BufferedReader(new FileReader(file)), JsonObject.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @param key A String value to get the JsonElement of.
	 * @return A JsonElement associated with the name of the key.
	 */
	public JsonElement elementFromKey(String key) {
		return obj.get(key);
	}

	public JsonObject getObj() {
		return obj;
	}
}
