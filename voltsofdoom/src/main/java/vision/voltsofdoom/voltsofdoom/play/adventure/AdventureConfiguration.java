package vision.voltsofdoom.voltsofdoom.play.adventure;

import java.util.List;
import java.util.Objects;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import vision.voltsofdoom.voltsofdoom.universal.resource.json.GsonHandler;
import vision.voltsofdoom.zapbyte.resource.ResourceLocation;

/**
 * The configuration for an {@link Adventure}, in order to abstract away the basic setting-of-things
 * and unclog the {@link Adventure} class.
 * 
 * @author GenElectrovise
 *
 */
public class AdventureConfiguration {
  private ResourceLocation identifier;
  private String displayName;
  private String description;
  private List<String> levelNames;
  private String version;

  public static AdventureConfiguration fromJson(JsonObject json) {

    AdventureConfiguration config = new AdventureConfiguration();
    Gson gson = GsonHandler.GSON;

    config = gson.fromJson(json, AdventureConfiguration.class);
    config.levelNames = gson.fromJson(json.get("levelNames"), new TypeToken<List<String>>() {
      private static final long serialVersionUID = -1179905207477723840L;
    }.getType());

    Objects.requireNonNull(config.description,
        () -> "AdventureConfiguration#fromJson found description to be null.");
    Objects.requireNonNull(config.displayName,
        () -> "AdventureConfiguration#fromJson found displayName to be null.");
    Objects.requireNonNull(config.identifier,
        () -> "AdventureConfiguration#fromJson found identifier to be null.");
    Objects.requireNonNull(config.levelNames,
        () -> "AdventureConfiguration#fromJson found levelNames to be null.");
    Objects.requireNonNull(config.version,
        () -> "AdventureConfiguration#fromJson found version to be null.");

    return config;
  }

  // With
  public AdventureConfiguration withIdentifier(ResourceLocation identifier) {
    this.identifier = identifier;
    return this;
  }

  public AdventureConfiguration withDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  public AdventureConfiguration withDescription(String description) {
    this.description = description;
    return this;
  }

  public AdventureConfiguration withLevelNames(String... levelNames) {
    for (int i = 0; i < levelNames.length; i++) {
      this.levelNames.add(levelNames[i]);
    }
    return this;
  }

  public AdventureConfiguration withVersion(String version) {
    this.version = version;
    return this;
  }

  // Get
  public ResourceLocation getIdentifier() {
    return identifier;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getDescription() {
    return description;
  }

  public List<String> getLevelNames() {
    return levelNames;
  }

  public String getVersion() {
    return version;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("AdventureConfiguration{");

    builder.append("identifier=" + identifier.stringify());
    builder.append(", displayName='" + displayName + "'");
    builder.append(", levelNames=" + levelNames);
    builder.append(", description='" + description + "'");

    builder.append("}");
    return builder.toString();
  }
}
