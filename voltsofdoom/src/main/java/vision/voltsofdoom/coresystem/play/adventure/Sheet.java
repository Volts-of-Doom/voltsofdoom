package vision.voltsofdoom.coresystem.play.adventure;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import vision.voltsofdoom.zapbyte.resource.ResourceLocation;

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

	public static interface ISheetType {

		public static final List<ISheetType> types = new ArrayList<Sheet.ISheetType>();

		public static final ISheetType EMPTY = create("");

		public static final ISheetType ENTITIES = create("entities");

		public static final ISheetType TILES = create("tiles");

		public static ISheetType create(String folderName) {

			for (ISheetType type : types) {
				if (folderName.contentEquals(type.folderName())) {
					throw new IllegalStateException("Duplicate ISheetType folderName " + folderName);
				}
			}

			ISheetType ist = new ISheetType() {
				@Override
				public String folderName() {
					return folderName;
				}
			};

			types.add(ist);

			return ist;
		}

		public String folderName();
	}
}
