package multithreading.completableFuture;

import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        try {
            ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1, 10,
                    TimeUnit.HOURS, new ArrayBlockingQueue<>(10),
                    Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());


            CompletableFuture<String> asyncTask1 = CompletableFuture.supplyAsync(
                            () -> {
                                return "new Task completed by thread: " + Thread.currentThread().getName();
                            }, poolExecutor
                    )
                    /**
                     *  using thenApply() to the result of supplyAsync()
                     *  Note : thenApply() is a sync method hence
                     *         it uses the same thread of the previous async task to complete its task
                     */
                    .thenApply((String val) -> {
                        return val + " -> this part is added in thenApply() : done by " + Thread.currentThread().getName() + " ->";
                    })

                    /**
                     *  using: thenApplyAsync() to the result of thenApply()
                     *  Note:  thenApplyAsync() is a async method
                     *          hence it will use another thread pool basically default thread pool fork join pool
                     *          to complete its task
                     */
                    .thenApplyAsync((String val) -> {
                        return val + " this part is added by thenApplyAsync() done by :" + Thread.currentThread().getName();
                    });

            System.out.println("Final Result: " + asyncTask1.get());


            /**
             *    Using - thenCompose() and thenComposeAsync()
             */
            CompletableFuture<String> asyncTask2 = CompletableFuture.supplyAsync(() -> {
                        System.out.println("Thread name in supplyAsync: " + Thread.currentThread().getName());
                        return "Hello ";
                    })
                    .thenCompose(val -> CompletableFuture.supplyAsync(() -> {
                        System.out.println("Thread name in thenCompose 1: " + Thread.currentThread().getName());
                        return val + " Aditya ";
                    }))
                    .thenCompose(val -> CompletableFuture.supplyAsync(() -> {
                        System.out.println("Thread name in thenCompose 2: " + Thread.currentThread().getName());
                        return val + " Shrivastava ";
                    }))
                    .thenComposeAsync(val -> CompletableFuture.supplyAsync(() -> {
                        System.out.println("Thread name in thenComposeAsync 3: " + Thread.currentThread().getName());
                        return val + " How are you ";
                    }));

            // Get and print final result
            System.out.println("Final Result: " + asyncTask2.get());


            /**
             *  Using  - thenCombine() & thenCombineAsync()
             *
             */
            CompletableFuture<String> asyncTask3 = CompletableFuture.supplyAsync(() -> {
                System.out.println("Thread name in supplyAsync for asynctask3 : " + Thread.currentThread().getName());
                return "Hello their ";
            }, poolExecutor);

            CompletableFuture<Integer> asyncTask4 = CompletableFuture.supplyAsync(() -> {
                System.out.println("Thread name in supplyAsync for asynctask4 : " + Thread.currentThread().getName());
                return 10;
            }, poolExecutor);

            CompletableFuture<String> asyncTask5 = CompletableFuture.supplyAsync(() -> {
                System.out.println("Thread name in supplyAsync for async task 5: " + Thread.currentThread().getName());
                return " good bye ";
            }, poolExecutor);

            CompletableFuture<String> combinedFutureObj = asyncTask3.thenCombine(asyncTask4, (String val1, Integer val2) -> val1 + val2)
                    .thenCombineAsync(asyncTask5, (String val1, String val2) -> val1 + val2);

            System.out.println("Combined future object: " + combinedFutureObj.get());


            /**
             *  using thenAccept()
             */

            CompletableFuture<Void> asyncTask6 = combinedFutureObj.thenAcceptAsync(val -> {
                System.out.println("async task 6 : " + Thread.currentThread().getName());
                System.out.println(val + " -> this part of string is added via thenAccept()");
            }, poolExecutor);
            System.out.println("thenCompose future object: " + asyncTask6.get());


        } catch (Exception e) {
            //
        }
    }
}
