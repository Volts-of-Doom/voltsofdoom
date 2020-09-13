package vision.voltofdoom.zapbyte.main;

/**
 * A {@link Runnable} task with associated {@link #priority} used in the
 * {@link ZapByte} class, and run at loading time.
 * 
 * @author GenElectrovise
 *
 */
public class ZapBit {

	private final int priority;
	private final Runnable task;

	public ZapBit(int priority, Runnable task) {
		this.priority = priority;
		this.task = task;
	}

	public int getPriority() {
		return priority;
	}

	public Runnable getTask() {
		return task;
	}
}
