package vision.voltsofdoom.coresystem.play.adventure;

import vision.voltsofdoom.coresystem.universal.band_wagon.Stowaway;
import vision.voltsofdoom.coresystem.universal.event.RegistryEvent;

/**
 * Generates a list of {@link Adventure}s to register.
 * @author GenElectrovise
 *
 */
public class AdventureLoader {

	/**
	 * Constructs the Adventure objects for a list of JSON files during the
	 * {@link RegistryEvent.GenerateAdventuresEvent}. Actually delegates all of the
	 * hard work to the Adventure class, which further delegates the work to its
	 * contained classes (eg {@link LevelMeta}, {@link Level} and
	 * {@link LevelMap})
	 * 
	 * @param adventureFiles The list of JSON files.
	 * @return An ArrayList of Adventures.
	 * @see Adventure
	 * @see Level
	 * @see LevelMeta
	 * @see LevelMeta
	 */
	@Stowaway
	private static void generateAdventures(RegistryEvent.GenerateAdventuresEvent event) {

	}
}
