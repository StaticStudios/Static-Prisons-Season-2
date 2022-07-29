package net.staticstudios.prisons.utils.items;

public interface SpreadOutExecution {

    void runSpreadOutExecution();

    default void queueExecution() {
        SpreadOutExecutor.add(this);
    }

}
