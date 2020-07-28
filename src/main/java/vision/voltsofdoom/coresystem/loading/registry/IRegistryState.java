package vision.voltsofdoom.coresystem.loading.registry;

/**
 * The state of an {@link IRegistry} or {@link IFinalisedRegistry}, or some
 * other kind of registry... <br>
 * This is a functional interface.
 * 
 * @author GenElectrovise
 *
 */
@FunctionalInterface
public interface IRegistryState {

	public static final IRegistryState ACTIVE = new IRegistryState() {
		@Override
		public boolean isMutable() {
			return true;
		}
	};

	public static final IRegistryState FROZEN = new IRegistryState() {

		@Override
		public boolean isMutable() {
			return false;
		}
	};

	/**
	 * In the case of {@link IRegistry}:<br>
	 * Can items be added to it?<br>
	 * 
	 * <br>
	 * In the case of {@link IFinalisedRegistry}:<br>
	 * Is it being constructed?
	 * 
	 * @return Whether the registry can be changed.
	 */
	public boolean isMutable();
}