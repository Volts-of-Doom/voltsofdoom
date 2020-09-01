package vision.voltsofdoom.coresystem.play.adventure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vision.voltsofdoom.coresystem.loading.registry.RegistryEntry;
import vision.voltsofdoom.coresystem.play.adventure.Sheet.ISheetType;

/**
 * Contains all of the data for an Adventure!
 * 
 * @author GenElectrovise
 *
 */
public class Adventure extends RegistryEntry<Adventure> {
	private ArrayList<Level> levels = new ArrayList<Level>();
	private Map<ISheetType, ArrayList<Sheet>> sheets = new HashMap<ISheetType, ArrayList<Sheet>>();
	private AdventureConfiguration configuration;

	private Adventure() {
	}

	public ArrayList<Level> getLevels() {
		return levels;
	}

	public AdventureConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * Builds an {@link Adventure} using chained methods. Call <code>build()</code>
	 * to access the built {@link Adventure}.
	 * 
	 * @author GenElectrovise
	 *
	 */
	public static class Builder {
		private Adventure adventure = new Adventure();

		public Adventure.Builder withConfiguration(AdventureConfiguration config) {
			adventure.configuration = config;
			return this;
		}

		public Adventure build() {
			return adventure;
		}

		public Adventure.Builder withSheet(Sheet sheet, ISheetType type) {
			
			if(!adventure.sheets.containsKey(type)) {
				adventure.sheets.put(type, new ArrayList<Sheet>());
			}
			
			adventure.sheets.get(type).add(sheet);
			return this;
		}
	}
}
