package vision.voltsofdoom.zapbyte.resource;

public interface IID {

  /**
   * The logic of this is that if the two aren't null they should be valid, as the constructor (the
   * only entry point) validates the incoming strings.
   * 
   * @return Whether this {@link ID} is valid.
   */
  ResourceLocationValidityState validate();

  String getEntry();

  String getDomain();

  String stringify();

  String toString();

  boolean equals(Object obj);

}
