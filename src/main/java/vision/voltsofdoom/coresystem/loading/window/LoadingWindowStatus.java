package vision.voltsofdoom.coresystem.loading.window;

public enum LoadingWindowStatus {
	OPENING_WINDOW("Opening loading window"), GENERATING_REFLECTORIES("Generating reflectories"),
	LOCATING_MODS("Locating mods"), LOCATING_BAND_WAGON_SUBSCRIBERS("Locating band wagon subscribers"),
	CREATING_BAND_WAGON("Loading band wagon"), CREATING_REGISTRY("Creating registry"), DONE("Done");

	public String msg;

	private LoadingWindowStatus(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
}
