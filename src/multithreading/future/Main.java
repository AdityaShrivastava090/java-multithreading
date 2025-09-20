package multithreading.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                3, // corePoolSize
                7, // maximumPoolSize
                10, TimeUnit.MINUTES, // keepAliveTime
                new ArrayBlockingQueue<>(10), // workQueue
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        /**
         * 1st Use Case - submit(Runnable runnable)
         */
        Future<?> futureObj = poolExecutor.submit(() -> {
            try {
                System.out.println("Task is started..... " + System.currentTimeMillis());
                Thread.sleep(7000);
                System.out.println("Task is completed..... " + System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        System.out.println("Is task completed? " + futureObj.isDone());

        try {
            System.out.println("Checking if execution is done or not completely..... " + System.currentTimeMillis());
            futureObj.get(2, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.out.println("Timeout exception....");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            futureObj.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Is done: " + futureObj.isDone());
        System.out.println("Is cancelled: " + futureObj.isCancelled());

        /**
         * 2nd Use Case - submit(Runnable runnable, T object)
         */
        List<Integer> output = new ArrayList<>();
        Future<List<Integer>> futureObject = poolExecutor.submit(new MyRunnable(output), output);

        try {
            futureObject.get();

            // 1st way
            System.out.println("Fetching future object from the Runnable task ---> " + output.get(0));

            // 2nd way
            List<Integer> result = futureObject.get();
            System.out.println(result.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 3rd Use Case - Callable where submit returns result
         */
        Future<List<Integer>> futureObject3 = poolExecutor.submit(() -> {
            List<Integer> result = new ArrayList<>();
            result.add(1200);
            return result;
        });

        try {
            futureObject3.get();

            Future<?> finalResult = poolExecutor.submit(
                    new MyRunnable(futureObject3.get()),
                    futureObject3.get()
            );

            System.out.println("Future object 3: " + futureObject3.get().get(0));
            System.out.println("Final object 3: " + finalResult.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
