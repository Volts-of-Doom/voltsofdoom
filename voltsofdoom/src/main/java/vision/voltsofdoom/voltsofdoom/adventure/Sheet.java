package vision.voltsofdoom.voltsofdoom.adventure;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonObject;
import vision.voltsofdoom.voltsofdoom.resource.json.GsonHandler;
import vision.voltsofdoom.zapbyte.resource.ID;

/**
 * Represents a <code>sheet</code> file from an {@link Adventure}.
 * 
 * @author GenElectrovise
 *
 */
public class Sheet {
  private ID identifier;
  private DataTagMap data;

  public ID getIdentifier() {
    return identifier;
  }

  public DataTagMap getData() {
    return data;
  }

  public static Sheet fromJson(JsonObject json) {
    return GsonHandler.GSON.fromJson(json, Sheet.class);
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
