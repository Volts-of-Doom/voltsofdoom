package vision.voltsofdoom.coresystem.universal.resource;

import java.util.ArrayList;
import java.util.Objects;
import java.util.PrimitiveIterator.OfInt;

/**
 * Identifies the location of *something*. This could be a resource or an object
 * in a registry, for example.
 * 
 * @author GenElectrovise
 *
 */
public class ResourceLocation {

	private String domain;
	private String path;

	public ResourceLocation(String modid, String entry) {
		this.domain = modid;
		this.path = entry;
	}

	public static ResourceLocationValidityState validate(String string) {

		// Get colon count
		int colonCount = 0;
		int index = 0;
		int colonIndex = 0;

		int preColonLetterCount = 0;
		int postColonLetterCount = 0;
		boolean foundColon = false;

		OfInt iterator = string.chars().iterator();
		while (iterator.hasNext()) {
			Integer integer = (Integer) iterator.next();
			char c = (char) integer.intValue();

			if (!Character.isLetter(c)) {
				if (isColon(c)) {
					if (colonCount++ > 1) {
						return ResourceLocationValidityState.INVALID_COLON_COUNT;
					} else {
						colonIndex = index;
					}

					foundColon = true;
				} else {
					return ResourceLocationValidityState.ILLEGAL_CHARACTER;
				}
			}

			if (isColon(c)) {
				;
			} else if (!foundColon) {
				preColonLetterCount++;
			} else {
				postColonLetterCount++;
			}

			index++;
		}

		// If doesn't contain exactly one colon
		if (colonCount < 1 || colonCount > 1) {
			return ResourceLocationValidityState.INVALID_COLON_COUNT;
		}

		// If colon does not obstruct lengths, i.e. is too short
		if (colonIndex < 4 || colonIndex > string.length() - 5) {
			return ResourceLocationValidityState.TOO_SHORT;
		}

		// If either side is too long
		if (preColonLetterCount > 32 || postColonLetterCount > 32) {
			return ResourceLocationValidityState.TOO_LONG;
		}

		return ResourceLocationValidityState.VALID;
	}

	public static ResourceLocation fromString(String str) {
		Objects.requireNonNull(str, () -> "Constructing ResourceLocation fromString() >> Input string cannot be null!");

		ResourceLocationValidityState validityState = validate(str);
		if (!validityState.isValid()) {
			throw new IllegalStateException("Invalid string '" + str + "' given to construct a ResourceLocation! >> "
					+ validityState.getMessage());
		}

		// Validated!

		ArrayList<Character> domainChars = new ArrayList<Character>();
		ArrayList<Character> pathChars = new ArrayList<Character>();
		boolean foundColon = false;
		OfInt iteratorTwo = str.chars().iterator();
		while (iteratorTwo.hasNext()) {
			Integer integer = (Integer) iteratorTwo.next();
			Character character = new Character((char) integer.intValue());

			if (isColon(character)) {
				foundColon = true;
				continue;
			}

			if (foundColon) {
				pathChars.add(character);
			} else {
				domainChars.add(character);
			}
		}

		StringBuilder domainBuilder = new StringBuilder();
		for (Character character : domainChars) {
			domainBuilder.append(character);
		}

		StringBuilder pathBuilder = new StringBuilder();
		for (Character character : pathChars) {
			pathBuilder.append(character);
		}

		return new ResourceLocation(domainBuilder.toString(), pathBuilder.toString());
	}

	public String getPath() {
		return path;
	}

	public String getDomain() {
		return domain;
	}

	public String stringify() {
		return domain + ":" + path;
	}

	@Override
	public String toString() {
		return "ResourceLocation{" + "}";
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof ResourceLocation)) {
			return false;
		}

		obj = (ResourceLocation) obj;

		if (!((ResourceLocation) obj).getDomain().equals(domain)) {
			return false;
		}

		if (!((ResourceLocation) obj).getPath().equals(path)) {
			return false;
		}

		return true;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ResourceLocation r2 = ResourceLocation.fromString("domain:entry");
		System.out.println(validate("domain::entry"));
	}

	public static boolean isColon(Character c) {
		return c.equals(new Character(new String(":").charAt(0)));
	}

	public static boolean isColon(char c) {
		return isColon(new Character(c));
	}

	/**
	 * Various reasons why a ResourceLocation might, or might not be valid. Useful
	 * for error messages!
	 * 
	 * @author GenElectrovise
	 *
	 */
	public static enum ResourceLocationValidityState {
		VALID(true, "ResourceLocation valid!"),
		TOO_LONG(false, "ResourceLocation spec is too long! (One half of the given spec exceeds 32 characters)"),
		TOO_SHORT(false, "ResourceLocation spec is too short! (One half of the given spec is less than 4 characters)"),
		INVALID_COLON_COUNT(false, "ResourceLocation does not contain 1 colon!"), ILLEGAL_CHARACTER(false,
				"ResourceLocation contains an illegal character! Only alphabetic characters, and colons are allowed!");

		private boolean valid;
		private String message;

		private ResourceLocationValidityState(boolean valid, String message) {
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
}
