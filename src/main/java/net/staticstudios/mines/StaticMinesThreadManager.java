package net.staticstudios.mines;

import org.bukkit.Bukkit;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class StaticMinesThreadManager {

    static ThreadPoolExecutor threadPool;

    /**
     * Initialize the thread pool.
     */
    public static void init() {
        threadPool = new ThreadPoolExecutor(
                StaticMinesConfig.getConfig().getInt("thread_pool.core_size"),
                StaticMinesConfig.getConfig().getInt("thread_pool.max_size"),
                StaticMinesConfig.getConfig().getInt("thread_pool.keep_alive_time"),
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>()
        );
    }

    /**
     * Queue a task to be run on the thread pool.
     *
     * @param runnable The task to run.
     */
    public static Future<?> submit(Runnable runnable) {
        if (threadPool == null) {
            throw new IllegalStateException("Thread pool has not been initialized!");
        }
        return threadPool.submit(runnable);
    }

    /**
     * Queue a task to be run on the thread pool.
     *
     * @param runnable The task to run.
     */
    public static void execute(Runnable runnable) {
        if (threadPool == null) {
            throw new IllegalStateException("Thread pool has not been initialized!");
        }
        threadPool.execute(runnable);
    }

    /**
     * Shutdown the thread pool.
     */
    public static void shutdown() {
        if (threadPool == null) return;
        StaticMines.log("Shutting down thread pool...");
        threadPool.shutdown();
        threadPool = null;
    }


    /**
     * Runs a task on the main thread. It will be run on the next tick if the current thread is not the main thread.
     */
    public static void runOnMainThread(Runnable runnable) {
        if (Bukkit.isPrimaryThread()) {
            runnable.run();
            return;
        }
        Bukkit.getScheduler().runTask(StaticMines.getParent(), runnable);
    }
}
