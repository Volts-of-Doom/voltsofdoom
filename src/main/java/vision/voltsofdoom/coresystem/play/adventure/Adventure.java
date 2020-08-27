package vision.voltsofdoom.coresystem.play.adventure;

import java.util.ArrayList;

import vision.voltsofdoom.coresystem.loading.registry.RegistryEntry;
import vision.voltsofdoom.coresystem.universal.resource.ResourceLocation;

/**
 * Contains all of the data for an Adventure!
 * 
 * @author GenElectrovise
 *
 */
public class Adventure extends RegistryEntry<Adventure> {
	private ArrayList<Level> levels = new ArrayList<Level>();
	private AdventureConfiguration configuration;

	public Adventure(AdventureConfiguration configuration) {
		this.configuration = configuration;
		levels = levelNamesToContainers();
	}

	public ArrayList<Level> getLevels() {
		return levels;
	}

	public AdventureConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * Turns a list of the names of levels included in this {@link Adventure} into
	 * an {@link ArrayList} of {@link Level}s.
	 * 
	 * @return An {@link ArrayList} of {@link Level}s in this {@link Adventure}.
	 */
	public ArrayList<Level> levelNamesToContainers() {
		ArrayList<Level> out = new ArrayList<Level>();

		for (String name : configuration.getLevelNames()) {
			out.add(new Level(new LevelConfiguration()
					.withIdentifier(new ResourceLocation(configuration.getIdentifier().getPath(), name))));
		}

		return out;
	}
}
