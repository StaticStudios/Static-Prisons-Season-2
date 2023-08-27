package net.staticstudios.mines;

public class StaticMineSettings {
    private int secondsBetweenRefill = 600;
    private double refillAtPercentFull = 50d;
    private boolean async = false;
    private boolean saveToFile = false;
    private boolean refillOnTimer = false;

    public StaticMineSettings() {
    }

    /**
     * @return The amount of time in seconds between each refill.
     */
    public int secondsBetweenRefill() {
        return secondsBetweenRefill;
    }

    /**
     * Set the amount of time in seconds between each refill.
     *
     * @param secondsBetweenRefill The amount of time in seconds between each refill.
     */
    public void secondsBetweenRefill(int secondsBetweenRefill) {
        this.secondsBetweenRefill = secondsBetweenRefill;
    }

    /**
     * @return The percentage of the mine that must be left before the mine is refilled.
     */
    public double refillAtPercentFull() {
        return refillAtPercentFull;
    }

    /**
     * Set the percentage of the mine that must be left before the mine is refilled.
     *
     * @param refillAtPercentFull The percentage of the mine that must be left before the mine is refilled.
     */
    public void refillAtPercentFull(double refillAtPercentFull) {
        this.refillAtPercentFull = refillAtPercentFull;
    }

    /**
     * @return Whether the mine should refill asynchronous or not.
     */
    public boolean async() {
        return async;
    }

    /**
     * Set whether the refill is asynchronous or not.
     *
     * @param async Whether the refill is asynchronous or not.
     */
    public void async(boolean async) {
        this.async = async;
    }

    /**
     * @return Whether the mine is saved to file or not.
     */
    public boolean saveToFile() {
        return saveToFile;
    }

    /**
     * Set whether the mine is saved to file or not.
     *
     * @param saveToFile Whether the mine is saved to file or not.
     */
    public void saveToFile(boolean saveToFile) {
        this.saveToFile = saveToFile;
    }

    /**
     * @return Whether the mine is refilled on a timer or not.
     */
    public boolean refillOnTimer() {
        return refillOnTimer;
    }

    /**
     * Set whether the mine is refilled on a timer or not.
     *
     * @param refillOnTimer Whether the mine is refilled on a timer or not.
     */
    public void refillOnTimer(boolean refillOnTimer) {
        this.refillOnTimer = refillOnTimer;
    }
}
