package net.staticstudios.prisons.utils.items;

/**
 * Interface for interacting with the SpreadOutExecutor, which will spread out tasks over time to prevent the main thread from getting bogged down.
 */
public interface SpreadOutExecution {

    /**
     * Override this method to specify what should be done when the executor has decided to execute this task.
     * There is no guarantee that this method will be called at a specific time, or at a known interval.
     */
    void execute();

    /**
     * Add this class to the queue to be executed at a later time.
     * When the executor is ready, it will call {@link #execute()}.
     */
    default void queueExecution() {
        SpreadOutExecutor.add(this);
    }

}
