package vision.voltsofdoom.voltsofdoom.play.adventure;

import java.util.Objects;
import com.google.gson.JsonObject;
import vision.voltsofdoom.voltsofdoom.universal.resource.json.GsonHandler;
import vision.voltsofdoom.zapbyte.resource.ResourceLocation;
import vision.voltsofdoom.zapbyte.resource.ResourceLocationInvalidException;
import vision.voltsofdoom.zapbyte.resource.ResourceLocationValidityState;

public class LevelConfiguration {
  private ResourceLocation identifier;
  private String displayName;
  private String description;

  public LevelConfiguration withIdentifier(ResourceLocation identifier) {
    this.identifier = identifier;
    return this;
  }

  public ResourceLocation getIdentifier() {
    return identifier;
  }

  public String getDescription() {
    return description;
  }

  public String getDisplayName() {
    return displayName;
  }

  public static LevelConfiguration fromJson(JsonObject json)
      throws ResourceLocationInvalidException {
    LevelConfiguration config = GsonHandler.GSON.fromJson(json, LevelConfiguration.class);

    Objects.requireNonNull(config.description,
        () -> "LevelConfiguration#fromJson found description to be null.");
    Objects.requireNonNull(config.displayName,
        () -> "LevelConfiguration#fromJson found displayName to be null.");
    Objects.requireNonNull(config.identifier,
        () -> "LevelConfiguration#fromJson found identifier to be null.");

    ResourceLocationValidityState state = config.identifier.validate();
    if (!state.isValid())
      throw new ResourceLocationInvalidException("ResourceLocation " + config.identifier.stringify()
          + " is invalid. >> " + state.getMessage());

    return config;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("LevelConfiguration{");

    builder.append("identifier=" + identifier.stringify());
    builder.append(", displayName='" + displayName + "'");
    builder.append(", description='" + description + "'");

    builder.append("}");
    return builder.toString();
  }
}
