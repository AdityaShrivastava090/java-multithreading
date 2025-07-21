package multithreading.scheduledThreadPool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class ScheduledTaskExample {
    public static void main(String[] args) {
        // Create a pool with 2 threads
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        Runnable task = () -> {
            System.out.println("Running task at " + System.currentTimeMillis());
        };

        // Schedule the task to run every 3 seconds after an initial delay of 2 seconds
        scheduler.scheduleAtFixedRate(task, 2, 3, TimeUnit.SECONDS);

        // Optionally stop the scheduler after 15 seconds (example)
        scheduler.schedule(() -> {
            System.out.println("Shutting down scheduler...");
            scheduler.shutdown();
        }, 15, TimeUnit.SECONDS);
    }

    Map<String, String> map = new HashMap<>();
}
