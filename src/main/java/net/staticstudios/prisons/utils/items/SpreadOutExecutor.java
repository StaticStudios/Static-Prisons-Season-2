package net.staticstudios.prisons.utils.items;

import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.Bukkit;

import java.util.*;

public class SpreadOutExecutor {

    private static boolean hasBeenInitialized = false;

    // If the current iteration has taken less than the MIN_MS time to execute,
    // move on to the next iteration and complete that, and recheck the time
    // it took, and if it took less than the MIN_MS time, move on to the
    // next iteration and complete that, and recheck the time it took, and so on.
    public static final double MIN_MS = 1;


    private static void init() {
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), SpreadOutExecutor::worker, 0, 1);
        hasBeenInitialized = true;
    }

    /**
     * The list of items to update later, they will be put into the que once the current que has been emptied
     */
    private static final HashSet<SpreadOutExecution> computeLater = new HashSet<>();

    static final int DUMP_INTERVAL = 50; //The amount of ticks that this operation is spread across. It might take DUMP_INTERVAL * 2 ticks before an item's lore is updated.
    static HashSet<SpreadOutExecution>[] queue; //The array of lists of items that need to be updated. Each list in the array represents the items that should be done in that index's tick.
    static int currentIteration = 0; //Number 1 - DUMP_INTERVAL representing the current iteration through the que.

    public static void worker() { //This method should be called every tick
        if (currentIteration == 0) {
            if (computeLater.isEmpty()) return;
            buildQue();
        }
        long minFinishTime = System.nanoTime() + (long) (MIN_MS * 1000000); //The min time that the current iteration should take
        do {
            if (!completeNextIteration()) break; //If the que is empty, break out of the loop
        } while (System.nanoTime() < minFinishTime); //Keep looping if the current iteration didn't take enough time and there is still stuff to do
    }


    /**
     * Do everything that has been queued up now.
     */
    public static void flushQue() {
        if (!hasBeenInitialized) return;
        for (SpreadOutExecution currentItem : computeLater) {
            currentItem.runSpreadOutExecution();
        }
        computeLater.clear();
    }

    /**
     *
     * @return true if there is more to do, false if the que is ready to be rebuilt
     */
    private static boolean completeNextIteration() {
        HashSet<SpreadOutExecution> currentIterationItems = queue[currentIteration];
        if (currentIterationItems != null && !currentIterationItems.isEmpty()) {
            for (SpreadOutExecution currentItem : currentIterationItems) {
                if (!computeLater.contains(currentItem)) { //Ensure that the item is still in the list of things to update, it might've been updated elsewhere
                    continue;
                }
                currentItem.runSpreadOutExecution();
            }
            computeLater.removeAll(currentIterationItems);
        }


        currentIteration++;
        if (currentIteration >= DUMP_INTERVAL) {
            currentIteration = 0;
        }

        return currentIteration != 0;
    }

    /**
     * Build the queue for the next DUMP_INTERVAL ticks
     */
    private static void buildQue() {

        queue = new HashSet[DUMP_INTERVAL];
        int i = 0; //Represents the index in the computeLater list
        int iteration = 0; //Represents the amount of times the loop has run.

        Iterator<SpreadOutExecution> iter = computeLater.iterator(); //Get an iterator for the list of items to update

        while (iter.hasNext()) {
            int amountToDumpThisTick = (int) ((double) computeLater.size() - i) / (DUMP_INTERVAL - iteration);

            HashSet<SpreadOutExecution> items = new HashSet<>();
            for (int x = 0; x < amountToDumpThisTick; x++) {
                items.add(iter.next());
                i++;
            }
            queue[iteration] = items;
            iteration++;
        }
    }


    public static void add(SpreadOutExecution item) {
        if (!hasBeenInitialized) {
            init();
        }

        computeLater.add(item);
    }

    public static void remove(SpreadOutExecution item) {
        computeLater.remove(item);
    }

    public static boolean isQueued(SpreadOutExecution item) {
        return computeLater.contains(item);
    }

    public static void doNow(SpreadOutExecution item) {
        item.runSpreadOutExecution();
        computeLater.remove(item);
    }

}
