package vision.voltsofdoom.coresystem.play.adventure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vision.voltsofdoom.coresystem.loading.registry.RegistryEntry;
import vision.voltsofdoom.coresystem.play.adventure.Sheet.ISheetType;
import vision.voltsofdoom.coresystem.universal.resource.ResourceLocation;

/**
 * Contains all of the data for an Adventure!
 * 
 * @author GenElectrovise
 *
 */
public class Adventure extends RegistryEntry<Adventure> {
	private ArrayList<LevelConfiguration> levelConfigurations = new ArrayList<LevelConfiguration>();
	private Map<ISheetType, ArrayList<Sheet>> sheets = new HashMap<ISheetType, ArrayList<Sheet>>();
	private AdventureConfiguration configuration;

	private Adventure() {
	}

	public ArrayList<LevelConfiguration> getLevelConfigurations() {
		return levelConfigurations;
	}

	@Override
	public ResourceLocation getIdentifier() {
		return configuration.getIdentifier();
	}

	public AdventureConfiguration getConfiguration() {
		return configuration;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Adventure{");
		
		builder.append(configuration.toString());
		
		builder.append("}");
		return builder.toString();
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
			adventure.identifier = config.getIdentifier();
			return this;
		}

		public Adventure build() {
			return adventure;
		}

		public Adventure.Builder withSheet(Sheet sheet, ISheetType type) {

			if (!adventure.sheets.containsKey(type)) {
				adventure.sheets.put(type, new ArrayList<Sheet>());
			}

			adventure.sheets.get(type).add(sheet);
			return this;
		}

		public void withLevelConfiguration(LevelConfiguration config) {
			adventure.levelConfigurations.add(config);
		}
	}
}
