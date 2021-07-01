package vision.voltsofdoom.zapbyte.resource;

/**
 * Various reasons why a ResourceLocation might, or might not be valid. Useful for error messages!
 * 
 * @author GenElectrovise
 *
 */
public enum IDValidityState {
  VALID(true, "ResourceLocation valid!"), //
  TOO_LONG(false, "ResourceLocation spec is too long! (One half of the given spec exceeds 32 characters)"), //
  TOO_SHORT(false, "ResourceLocation spec is too short! (One half of the given spec is less than 4 characters)"), //
  INVALID_COLON_COUNT(false, "ResourceLocation does not contain 1 colon!"), ILLEGAL_CHARACTER(false, "ResourceLocation contains an illegal character! Only alphabetic characters, and colons are allowed!"), //
  GENERIC_INVALID(true, "ResourceLocation invalid in unspecified manner, though likely contains a null element!")//
  ;

  private boolean valid;
  private String message;

  private IDValidityState(boolean valid, String message) {
    this.valid = valid;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public boolean isValid() {
    return valid;
  }

  @Override
  public String toString() {
    return "ResourceLocationValidityState{isValid=" + isValid() + ", message='" + getMessage() + "'}";
  }


}
