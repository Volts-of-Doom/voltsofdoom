package vision.voltsofdoom.coresystem.universal.resource;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.function.IntPredicate;
import java.util.stream.Stream;

import com.google.common.io.Files;

import vision.voltsofdoom.coresystem.loading.reflectory.Reflectories;
import vision.voltsofdoom.coresystem.universal.log.VODLog4J;
import vision.voltsofdoom.coresystem.universal.util.Reference;

/**
 * Identifies the location of *something*. This could be a resource or an object
 * in a registry, for example.
 * 
 * @author GenElectrovise
 *
 */
public class ResourceLocation {

	private String modid;
	private String entry;
	private final EnumResourceType type;

	public ResourceLocation(String modid, String entry) {
		this(modid, entry, EnumResourceType.OBJECT);
	}

	public ResourceLocation(String modid, String entry, EnumResourceType type) {
		this.modid = modid;
		this.entry = entry;
		this.type = type;
	}

	public String getEntry() {
		return entry;
	}

	public String getModid() {
		return modid;
	}

	public EnumResourceType getType() {
		return type;
	}

	public ResourceLocation castType(EnumResourceType toType) {
		return new ResourceLocation(modid, entry, toType);
	}

	public String stringify() {
		return modid + ":" + entry;
	}

	@Override
	public String toString() {
		return "ResourceLocation{" + "}";
	}

	public InputStream asStream() {
		InputStream stream = null;

		try {
			switch (type) {
			case INTERNAL:
				System.err.println("INTERNAL getAsStream");
				return this.getClass().getResourceAsStream("");
			case JAR:
				System.err.println("JAR getAsStream");
				return Reflectories.get(modid).getClassLoader().getResourceAsStream("");
			case OBJECT:
				VODLog4J.LOGGER.warn("Oops! Cannot retrieve a resource of " + EnumResourceType.OBJECT
						+ " type as an InputStream! See the documentation of " + EnumResourceType.class.getSimpleName()
						+ " for details!");
				return null;
			case SYSTEM:
				System.err.println("SYSTEM getAsStream");
				File file = new File("");
				return file.AS_A_STREAM;
			}
		} catch (Exception e) {
			throw new NullPointerException("Could not find resource! >> " + toString());
		}
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof ResourceLocation)) {
			return false;
		}

		if (!((ResourceLocation) obj).getModid().equals(modid)) {
			return false;
		}

		if (!((ResourceLocation) obj).getEntry().equals(entry)) {
			return false;
		}

		return true;
	}
}
