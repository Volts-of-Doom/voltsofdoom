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

	public static boolean validate(String string) {

		// Get colon count
		int colonCount = 0;
		int index = 0;
		int colonIndex = 0;

		OfInt iterator = string.chars().iterator();
		while (iterator.hasNext()) {
			Integer integer = (Integer) iterator.next();
			char c = (char) integer.intValue();

			if (!Character.isLetter(c)) {
				if (new Character(c).equals(new Character(new String(":").charAt(0)))) {
					if (colonCount++ > 1) {
						return false;
					} else {
						colonIndex = index;
					}
				} else {
					return false;
				}
			}

			index++;
		}

		// If doesn't contain exactly one colon
		if (colonCount < 1 || colonCount > 1) {
			return false;
		}

		// If colon does not obstruct lengths
		if (colonIndex < 4 || colonIndex > string.length() - 5) {
			return false;
		}

		return true;
	}

	public static ResourceLocation fromString(String str) {
		Objects.requireNonNull(str, () -> "Constructing ResourceLocation fromString() >> Input string cannot be null!");

		if (!validate(str)) {
			throw new IllegalStateException("Invalid string given to construct a ResourceLocation!");
		}

		// Validated!

		ArrayList<Character> domainChars = new ArrayList<Character>();
		ArrayList<Character> pathChars = new ArrayList<Character>();
		boolean foundColon = false;
		OfInt iteratorTwo = str.chars().iterator();
		while (iteratorTwo.hasNext()) {
			Integer integer = (Integer) iteratorTwo.next();
			Character character = new Character((char) integer.intValue());

			if (character.equals(new Character(new String(":").charAt(0)))) {
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

	public static void main(String[] args) {
		ResourceLocation r2 = ResourceLocation.fromString("domain:entry");
		System.out.println(validate("domain:entry_"));
	}
}
