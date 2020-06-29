package vision.voltsofdoom.coresystem.play.entity;

import javax.swing.ImageIcon;

import vision.voltsofdoom.coresystem.play.tile.Tile;
import vision.voltsofdoom.coresystem.loading.registry.IRegistryEntry;
import vision.voltsofdoom.coresystem.loading.registry.RegistryObjectRetriever;
import vision.voltsofdoom.coresystem.loading.resource.ResourceLocation;

/**
 * A thing in a level which isn't a Tile is probably an Entity.
 * 
 * @author GenElectrovise
 *
 * @see Tile
 */
public class Entity implements IRegistryEntry<Entity> {
	private Entity.Properties properties;

	public Entity(RegistryObjectRetriever<Entity> objFromModRegistry) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Should use this constructor in combination with <code>withProperties</code>
	 */
	public Entity() {

	}

	public Entity(Entity.Properties properties) {
		this.properties = properties;
	}

	public Entity(Entity.Archetype archetype) {
		this.properties = archetype.toProperties();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		try {
			builder.append("Entity : [");

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

	/**
	 * Defines the properties of a Tile using chainable methods.
	 * 
	 * @author GenElectrovise
	 *
	 */
	public static class Properties {
		private boolean isAnimated;
		private ImageIcon image;

		public ImageIcon getImage() {
			return image;
		}

		/**
		 * @param image The ImageIcon to set the image attribute of this Tile.Properties
		 *              object to
		 * @return This Tile.Properties object
		 */
		public Entity.Properties setImage(ImageIcon image) {
			this.image = image;
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
		public Entity.Properties setAnimated(boolean isAnimated) {
			this.isAnimated = isAnimated;
			return this;
		}
	}

	/**
	 * Defines an archetypal Tile -- All of the default properties a Tile of a given
	 * type should have.
	 * 
	 * @author GenElectrovise
	 *
	 */
	public static enum Archetype {
		ENTITYBLOB(new Entity.Properties().setImage(new ImageIcon("src/main/resources/image/tile/cobble.png"))
				.setAnimated(false));

		private Entity.Properties properties;

		/**
		 * @param properties An instance of the properties for a new Tile.Archetype.
		 */
		private Archetype(Entity.Properties properties) {
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
		public static Entity.Archetype fromString(String key) {
			key = key.replace("tile", "").toUpperCase();
			return Entity.Archetype.valueOf(key);
		}

		/**
		 * @return The inbuilt Tile.Properties object of this Tile.Archetype
		 */
		public Entity.Properties toProperties() {
			return this.properties;
		}

	}

	@Override
	public ResourceLocation getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIdentifier(ResourceLocation identifier) {
		
	}
}
