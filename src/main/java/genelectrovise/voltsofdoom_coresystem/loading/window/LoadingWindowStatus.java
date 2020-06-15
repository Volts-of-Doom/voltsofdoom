package genelectrovise.voltsofdoom_coresystem.loading.window;

public enum LoadingWindowStatus {
	UNSTARTED("Press launch to start Volts of Doom"), INIT("Initialising"), DONE("Done");
	
	public String msg;
	private LoadingWindowStatus(String msg) {
		this.msg = msg;
	}
}
