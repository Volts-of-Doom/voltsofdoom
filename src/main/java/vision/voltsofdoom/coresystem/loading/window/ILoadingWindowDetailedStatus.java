package vision.voltsofdoom.coresystem.loading.window;

public interface ILoadingWindowDetailedStatus {
	public static final ILoadingWindowDetailedStatus NO_ADDITIONAL_INFO = new ILoadingWindowDetailedStatus() {
		
		@Override
		public String getDetailedMessage() {
			return "No additional information...";
		}
	};

	public String getDetailedMessage();
}
