/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.concurrent.*;

/**
 *
 * @author Tien
 */
public class TaskManager {

    private static final int THREAD_POOL_SIZE = 4;
    private final ExecutorService executorService;
    private static TaskManager instance;

    public TaskManager() {
        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public static synchronized TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    public void submitTask(Runnable task) {
        executorService.submit(task);
    }

    public void shutDown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
