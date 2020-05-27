package genelectrovise.voltsofdoom_coresystem.play.tile;

import genelectrovise.voltsofdoom_coresystem.loading.registry.RegistryObject;
import genelectrovise.voltsofdoom_coresystem.loading.resource.image.VODImage;

/**
 * A very important thing.
 * 
 * @author adam_
 *
 */
public class Tile {
	private Tile.Properties properties;

	public Tile(String modid, String registryName) {
		// TODO Auto-generated constructor stub
	}

	public Tile(RegistryObject<Tile> objFromModRegistry) {
		// TODO Should take RegistryObject and turn self into duplicate
	}

	public Tile() {
		// TODO Should dooo idk? Always called withProperties(), so that can set
		// variables.
	}

	public Tile(Tile.Properties properties) {
		this.properties = properties;
	}

	public Tile(Tile.Archetype archetype) {
		this.properties = archetype.toProperties();
	}

	public Tile.Properties getProperties() {
		return properties;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		try {
			builder.append("Tile : [");

			builder.append("Properties {");
			builder.append("isAnimated=" + properties.getAnimated());
			builder.append(" image=" + properties.getImage().toString());
			builder.append("}");

			builder.append("]");
		} catch (Exception e) {
			// e.printStackTrace();
		}

		return builder.toString();
	}

	// Classes
	// =================================================================================

	/**
	 * Defines the properties of a Tile using chainable methods.
	 * 
	 * @author adam_
	 *
	 */
	public static class Properties {
		private boolean isAnimated;
		private VODImage image;

		public VODImage getImage() {
			return image;
		}

		/**
		 * @param vodImage The ImageIcon to set the image attribute of this
		 *                 Tile.Properties object to
		 * @return This Tile.Properties object
		 */
		public Tile.Properties setImage(VODImage vodImage) {
			this.image = vodImage;
			return this;
		}

		public boolean getAnimated() {
			return isAnimated;
		}

		/**
		 * @param isAnimated A boolean to set whether this Tile.Properties should give
		 *                   its Tiles an animation.
		 * @return This Tile.Properties object
		 */
		public Tile.Properties setAnimated(boolean isAnimated) {
			this.isAnimated = isAnimated;
			return this;
		}
	}

	/**
	 * Defines an archetypal Tile -- All of the default properties a Tile of a given
	 * type should have.
	 * 
	 * @author adam_
	 *
	 */
	public static enum Archetype {
		COBBLE(new Tile.Properties().setImage(new VODImage("src/main/resources/image/tile/cobble.png"))
				.setAnimated(false)),
		WOODLOG(new Tile.Properties().setImage(new VODImage("src/main/resources/image/tile/woodlog.png"))
				.setAnimated(false));

		private Tile.Properties properties;

		/**
		 * @param properties An instance of the properties for a new Tile.Archetype.
		 */
		private Archetype(Tile.Properties properties) {
			this.properties = properties;
		}

		/**
		 * An almost convenience method to use to get the value of an enum of the given
		 * name. Does the exact same thing as the enum's own method
		 * <code>valueOf(String)</code>
		 * 
		 * @param key The name of the enum you want to retrieve
		 * @return The Tile.Archetype Enum object.
		 */
		public static Tile.Archetype fromString(String key) {
			key = key.replace("tile", "").toUpperCase();
			return Tile.Archetype.valueOf(key);
		}

		/**
		 * @return The inbuilt Tile.Properties object of this Tile.Archetype
		 */
		public Tile.Properties toProperties() {
			return this.properties;
		}

	}

}
