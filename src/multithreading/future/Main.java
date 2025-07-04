package multithreading.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3, 7, 10, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        /**
         *  1st UseCase - submit(Runnable runnable)
         */
        Future<?> futureObj = poolExecutor.submit(() -> {
            try {
                System.out.println("task is started....." + System.currentTimeMillis());
                Thread.sleep(7000);
                System.out.println("task is completed....." + System.currentTimeMillis());
            } catch (Exception e) {
                //
            }
        });
        System.out.println("Is task completed " + futureObj.isDone());

        try {
            System.out.println("checking if execution is done or not completely..... "+ System.currentTimeMillis());
            futureObj.get(2, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.out.println("Timeout exception....");
        } catch (Exception e) {
            //
        }

        try {
            futureObj.get();
        } catch (Exception e) {

        }

        System.out.println("is done : " + futureObj.isDone());
        System.out.println("is cancelled : " + futureObj.isCancelled());




        /**
         *  2nd UseCase - submit(Runnable runnable, T object)
         */

        List<Integer> output = new ArrayList<>();
        Future<List<Integer>> futureObject = poolExecutor.submit(new MyRunnable(output), output);
        try {
            futureObject.get();
            // 1st way
            System.out.println(output.get(0));

            //2nd way
            List<Integer> result = futureObject.get();
            System.out.println(result.get(0));
        } catch (Exception e) {
        }

        /**
         *  3rd Use case - callable where the submit is returning result.
         */
        Future<List<Integer>> futureObject3 = poolExecutor.submit(() -> {
            List<Integer> result = new ArrayList<>();
            result.add(1200);
            return result;
        });

        try {
            futureObject3.get();
            System.out.println("futrure object 3 " + futureObject3.get().get(0));
        } catch (Exception e) {
            //
        }
    }
}
