package vision.voltsofdoom.zapbyte.main;

import java.util.Objects;
import javax.annotation.Nonnull;

/**
 * A {@link Runnable} task with associated {@link #priority} used in the
 * {@link ZapByte} class, and run at loading time.
 * 
 * @author GenElectrovise
 *
 */
public class ZapBit implements Runnable {

	private final int priority;
	private final Runnable task;

	public ZapBit(@Nonnull int priority, @Nonnull Runnable task) {
		Objects.requireNonNull(priority, "ZapBit priority cannot be null");
		Objects.requireNonNull(task, "ZapBit Runnable task cannot be null");
		this.priority = priority;
		this.task = task;
	}

	public int getPriority() {
		return priority;
	}

	public Runnable getTask() {
		return task;
	}

	@Override
	public void run() {
		getTask().run();
	}
}
