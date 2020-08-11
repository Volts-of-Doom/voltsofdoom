package vision.voltsofdoom.coresystem.universal.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import vision.voltsofdoom.coresystem.loading.reflectory.Reflectories;
import vision.voltsofdoom.coresystem.universal.log.VODLog4J;
import vision.voltsofdoom.coresystem.universal.main.VoltsOfDoomCoreSystem;
import vision.voltsofdoom.coresystem.universal.util.Reference;

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
	private final EnumResourceType type;

	public ResourceLocation(String modid, String entry) {
		this(modid, entry, EnumResourceType.OBJECT);
	}

	public ResourceLocation(String modid, String entry, EnumResourceType type) {
		this.domain = modid;
		this.path = entry;
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public String getDomain() {
		return domain;
	}

	public EnumResourceType getType() {
		return type;
	}

	public ResourceLocation castType(EnumResourceType toType) {
		return new ResourceLocation(domain, path, toType);
	}

	public String stringify() {
		return domain + ":" + path;
	}

	@Override
	public String toString() {
		return "ResourceLocation{" + "}";
	}

	/**
	 * Should I even use this?? Resources will need to be accessed in different
	 * ways!
	 * 
	 * @return
	 */
	public InputStream asStream() {
		InputStream stream = null;

		try {
			switch (type) {
			case INTERNAL:
				VODLog4J.LOGGER.debug("INTERNAL getAsStream - Path >> " + getDomain() + getPath());
				return this.getClass().getResourceAsStream("");
			case JAR:
				VODLog4J.LOGGER.debug("JAR getAsStream - ClassLoader >> " + Reflectories.get(domain).getClassLoader()
						+ " : With path >>" + getDomain() + getPath());
				return Reflectories.get(domain).getClassLoader().getResourceAsStream(getDomain() + getPath());
			case OBJECT:
				VODLog4J.LOGGER.warn("Oops! Cannot retrieve a resource of " + EnumResourceType.OBJECT
						+ " type as an InputStream! See the documentation of " + EnumResourceType.class.getSimpleName()
						+ " for details!");
				return null;
			case SYSTEM:
				String path = Reference.ROAMING_RESOURCES + "mod" + getDomain() + Reference.SEP + getPath();
				System.err.println("SYSTEM getAsStream >> " + path);
				File file = new File(path);
				return new FileInputStream(file);
			}
		} catch (Exception e) {
			throw new NullPointerException("Could not find resource! >> " + toString());
		}
		VODLog4J.LOGGER.error("Failed to retrieve stream for ResourceLocation " + toString());
		return stream;
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof ResourceLocation)) {
			return false;
		}

		if (!((ResourceLocation) obj).getDomain().equals(domain)) {
			return false;
		}

		if (!((ResourceLocation) obj).getPath().equals(path)) {
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		ResourceLocation l = new ResourceLocation(VoltsOfDoomCoreSystem.ID, "path", EnumResourceType.SYSTEM);
		l.asStream();
	}
}
