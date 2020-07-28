package vision.voltsofdoom.coresystem.loading.window;

public interface ILoadingWindowStatus {
	public static final ILoadingWindowStatus OPENING_WINDOW = new ILoadingWindowStatus() {

		@Override
		public String getMessage() {
			return "Opening loading window";
		}
	};

	public static final ILoadingWindowStatus GENERATING_REFLECTORIES = new ILoadingWindowStatus() {

		@Override
		public String getMessage() {
			return "Generating reflectories";
		}
	};

	public static final ILoadingWindowStatus LOCATING_MODS = new ILoadingWindowStatus() {

		@Override
		public String getMessage() {
			return "Locating mods";
		}
	};

	public static final ILoadingWindowStatus LOCATING_BAND_WAGON_SUBSCRIBERS = new ILoadingWindowStatus() {

		@Override
		public String getMessage() {
			return "Locating band wagon subscribers";
		}
	};

	public static final ILoadingWindowStatus CREATING_BAND_WAGON = new ILoadingWindowStatus() {

		@Override
		public String getMessage() {
			return "Loading band wagon";
		}
	};

	public static final ILoadingWindowStatus CREATING_REGISTRY = new ILoadingWindowStatus() {

		@Override
		public String getMessage() {
			return "Creating registry";
		}
	};
	public static final ILoadingWindowStatus DONE = new ILoadingWindowStatus() {

		@Override
		public String getMessage() {
			return "Done";
		}
	};

	public String getMessage();
}
