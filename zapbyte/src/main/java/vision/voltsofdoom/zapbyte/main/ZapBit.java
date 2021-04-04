package vision.voltsofdoom.zapbyte.main;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A {@link Runnable} task with associated {@link #priority} used in the {@link ZapByte} class, and
 * run at loading time.
 * 
 * @see DefaultZapBits
 * @author GenElectrovise
 *
 */
public class ZapBit implements Runnable {

  private final int priority;
  private final Runnable task;
  private final String name;

  public ZapBit(@Nullable String name, @Nonnull int priority, @Nonnull Runnable task) {
    Objects.requireNonNull(priority, "ZapBit priority cannot be null");
    Objects.requireNonNull(task, "ZapBit Runnable task cannot be null");
    this.priority = priority;
    this.task = task;
    this.name = name != null ? name : "zapbit_" + priority + "_" + task.hashCode();
  }

  public ZapBit(@Nonnull int priority, @Nonnull Runnable task) {
    this(null, priority, task);
  }

  public int getPriority() {
    return priority;
  }

  public Runnable getTask() {
    return task;
  }

  public String getName() {
    return name;
  }

  @Override
  public void run() {
    getTask().run();
  }
}
