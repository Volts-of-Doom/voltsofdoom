package vision.voltsofdoom.coresystem.loading.window;

/**
 * The detailed status to be shown on the {@link LoadingWindow} during startup.
 * Updated using
 * {@link BandWagon#playEvent(vision.voltsofdoom.coresystem.universal.band_wagon.Event)},
 * playing a {@link LoadingManager.UpdateDetailedStatusEvent}.
 * 
 * @author GenElectrovise
 *
 */
public interface ILoadingWindowDetailedStatus {
	public static final ILoadingWindowDetailedStatus NO_ADDITIONAL_INFO = new ILoadingWindowDetailedStatus() {

		@Override
		public String getDetailedMessage() {
			return "No additional information...";
		}
	};

	public String getDetailedMessage();
}
