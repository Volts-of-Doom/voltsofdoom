package vision.voltsofdoom.voltsofdoom.play.adventure;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipFile;
import com.google.common.collect.ImmutableSet;
import vision.voltsofdoom.voltsofdoom.play.adventure.Sheet.ISheetType;
import vision.voltsofdoom.voltsofdoom.universal.util.Reference;
import vision.voltsofdoom.zapbyte.registry.RegistryEntry;
import vision.voltsofdoom.zapbyte.resource.IResourceLocation;
import vision.voltsofdoom.zapbyte.resource.ZBSystemResourceHandler;

/**
 * Contains all of the data for an Adventure!
 * 
 * @author GenElectrovise
 *
 */
public class Adventure extends RegistryEntry<Adventure> {
  private AdventureConfiguration configuration;
  private ArrayList<LevelConfiguration> levelConfigurations = new ArrayList<LevelConfiguration>();
  private Map<ISheetType, ArrayList<Sheet>> sheets = new HashMap<ISheetType, ArrayList<Sheet>>();
  private ImmutableSet<Level> levels = ImmutableSet.of();

  private Adventure() {}

  public ArrayList<LevelConfiguration> getLevelConfigurations() {
    return levelConfigurations;
  }

  public Map<ISheetType, ArrayList<Sheet>> getSheets() {
    return sheets;
  }

  public AdventureConfiguration getConfiguration() {
    return configuration;
  }

  public ImmutableSet<Level> getLevels() {
    return levels.isEmpty() ? generateLevels(levelConfigurations) : levels;
  }

  @Override
  public IResourceLocation getIdentifier() {
    return configuration.getIdentifier();
  }

  private ImmutableSet<Level> generateLevels(ArrayList<LevelConfiguration> configs) {
    ArrayList<Level> levelArr = new ArrayList<Level>();
    try {

      for (LevelConfiguration config : configs) {

        File file = ZBSystemResourceHandler.instance.getFile(() -> (Reference.getAdventuresDir() + this.configuration.getIdentifier().getEntry()
            + "_" + this.configuration.getVersion() + ".zip"));
        levelArr.add(Level.fromZip(this, new ZipFile(file), config));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return ImmutableSet.copyOf(levelArr);
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
   * Builds an {@link Adventure} using chained methods. Call <code>build()</code> to access the
   * built {@link Adventure}.
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
