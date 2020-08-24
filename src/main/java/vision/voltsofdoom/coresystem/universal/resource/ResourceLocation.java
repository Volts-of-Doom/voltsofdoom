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

	/**
	 * A component {@link String} in a {@link ResourceLocation} is considered valid
	 * if it contains only letters, and is between 4 and 32 characters incl.
	 * 
	 * @param string
	 * @return
	 */
	public boolean validateString(String string) {

		if (string.length() > 32 || string.length() < 4) {
			return false;
		}

		// For each letter
		OfInt it = string.chars().iterator();
		while (it.hasNext()) {
			Integer i = (Integer) it.next();
			Character c = new Character((char) i.intValue());

			if (!Character.isLetter(c)) {
				return false;
			}

			/*
			 * // If not letter if (!Character.isLetter(c)) { // If is colon if
			 * (!c.equals(new String(":").charAt(0))) { System.out.println("Colon : " + new
			 * String(":").charAt(0));
			 * 
			 * // If too many colons (only one allowed) if (colonCount++ > 1) { return
			 * false; }
			 * 
			 * } else Not letter is illegal { return false; } }
			 */
		}

		return true;
	}

	public boolean validate() {

		int colonCount = 0;
		OfInt iterator = stringify().chars().iterator();
		while (iterator.hasNext()) {
			Integer integer = (Integer) iterator.next();
			char c = (char) integer.intValue();

			if (!Character.isLetter(c)) {
				if (new Character(c).equals(new Character(new String(":").charAt(0)))) {
					if (colonCount++ > 1) {
						return false;
					}
				} else {
					return false;
				}
			}
		}

		if (colonCount < 1) {
			return false;
		}

		return validateString(getDomain()) && validateString(getPath());
	}

	public static ResourceLocation fromString(String str) {
		Objects.requireNonNull(str, () -> "Constructing ResourceLocation fromString() >> Input string cannot be null!");

		int colonCount = 0;
		OfInt iteratorOne = str.chars().iterator();
		while (iteratorOne.hasNext()) {
			Integer integer = (Integer) iteratorOne.next();
			Character character = new Character((char) integer.intValue());

			if (!Character.isLetter(character.charValue())) {
				if (character.equals(new Character(new String(":").charAt(0)))) {
					if (colonCount++ > 1) {
						throw new IllegalStateException(
								"Invalid ResourceLocation string input >> Contains more than one colon!");
					}
				} else {
					throw new IllegalStateException(
							"Invalid ResourceLocation string input >> Can only contain alphabetic letters, and 1 colon");
				}
			}
		}

		if (colonCount < 1) {
			throw new IllegalStateException("Invalid ResourceLocation string input >> Contains fewer than one colon!");
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
		ResourceLocation r = new ResourceLocation("modid", "path");
		System.out.println(r.validate());

		ResourceLocation r2 = ResourceLocation.fromString("domain::entry");
		System.out.println(r2.validate());
	}
}
