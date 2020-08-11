package vision.voltsofdoom.coresystem.universal.resource;

/**
 * Denotes the type of resource a {@link ResourceLocation} denotes.
 * 
 * @author GenElectrovise
 *
 */
public enum EnumResourceType {
	/**
	 * Inside the resources of the Volts of Doom application.
	 */
	INTERNAL,
	/**
	 * Inside of the resources of a modded (and loaded as such!) jar file.
	 */
	JAR,
	/**
	 * A resource in the Volts of Doom roaming directory (save directory)
	 */
	SYSTEM,
	/**
	 * A coded resource in, for example, a registry. Used as a unique identifier to
	 * access such stored data.
	 */
	OBJECT;
}
