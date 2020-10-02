package vision.voltsofdoom.zapbyte.resource;

public interface IResourceLocation {

  /**
   * The logic of this is that if the two aren't null they should be valid, as the constructor (the
   * only entry point) validates the incoming strings.
   * 
   * @return Whether this {@link ResourceLocation} is valid.
   */
  ResourceLocationValidityState validate();

  String getEntry();

  String getDomain();

  String stringify();

  String toString();

  boolean equals(Object obj);

}
