package vision.voltsofdoom.zapbyte.resource;

import java.util.ArrayList;
import java.util.Objects;
import java.util.PrimitiveIterator.OfInt;

import com.google.common.collect.ImmutableList;

/**
 * Identifies the location of *something*. This could be a resource or an object
 * in a registry, for example.
 * 
 * @author GenElectrovise
 *
 */
public class ResourceLocation implements IResourceLocation {

	private String domain;
	private String entry;

	public ResourceLocation(String domain, String entry) {
		this.domain = domain;
		this.entry = entry;

		ImmutableList.of(domain, entry).forEach((str) -> {
			try {

				if (str.length() > 32) {
					throw new ResourceLocationInvalidException(
							"ResourceLocation section " + str + " too long: " + str.length());
				}

				if (str.length() < 4) {
					throw new ResourceLocationInvalidException(
							"ResourceLocation section " + str + " too short: " + str.length());
				}

				if (str.contains(":")) {
					throw new ResourceLocationInvalidException(
							"ResourceLocation section " + str + " contains an invalid colon!");
				}

			} catch (ResourceLocationInvalidException r) {
				r.printStackTrace();
			}
		});
	}

	/**
	 * The logic of this is that if the two aren't null they should be valid, as the
	 * constructor (the only entry point) validates the incoming strings.
	 * 
	 * @return Whether this {@link ResourceLocation} is valid.
	 */
	@Override
	public ResourceLocationValidityState validate() {
		return (domain != null && entry != null) ? ResourceLocationValidityState.VALID
				: ResourceLocationValidityState.GENERIC_INVALID;
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

	public static IResourceLocation fromString(String str) {
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

	@Override
	public String getEntry() {
		return entry;
	}

	@Override
	public String getDomain() {
		return domain;
	}

	@Override
	public String stringify() {
		return domain + ":" + entry;
	}

	@Override
	public String toString() {
		return "ResourceLocation{" + domain + ":" + entry + "}";
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof ResourceLocation)) {
			return false;
		}

		obj = (IResourceLocation) obj;

		if (!((IResourceLocation) obj).getDomain().equals(domain)) {
			return false;
		}

		if (!((IResourceLocation) obj).getEntry().equals(entry)) {
			return false;
		}

		return true;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		IResourceLocation r2 = ResourceLocation.fromString("domain:entry");
		System.out.println(validate("domain::entry"));
	}

	public static boolean isColon(Character c) {
		return c.equals(new Character(new String(":").charAt(0)));
	}

	public static boolean isColon(char c) {
		return isColon(new Character(c));
	}
}
