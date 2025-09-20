package multithreading.completableFuture;

import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        try {
            ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                    1,
                    1,
                    10,
                    TimeUnit.HOURS,
                    new ArrayBlockingQueue<>(10),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy()
            );

            /**
             * UseCase 1 - thenApply() and thenApplyAsync()
             */
            CompletableFuture<String> asyncTask1 = CompletableFuture
                    .supplyAsync(() -> {
                        return "New Task completed by thread: " + Thread.currentThread().getName();
                    }, poolExecutor)

                    // thenApply(): synchronous, same thread as previous stage
                    .thenApply(val -> {
                        return val + " -> Added in thenApply() by: " + Thread.currentThread().getName() + " ->";
                    })

                    // thenApplyAsync(): asynchronous, uses ForkJoinPool by default
                    .thenApplyAsync(val -> {
                        return val + " Added in thenApplyAsync() by: " + Thread.currentThread().getName();
                    });

            System.out.println("Final Result (asyncTask1): " + asyncTask1.get());


            /**
             * UseCase 2 - thenCompose() and thenComposeAsync()
             */
            CompletableFuture<String> asyncTask2 = CompletableFuture
                    .supplyAsync(() -> {
                        System.out.println("Thread name in supplyAsync: " + Thread.currentThread().getName());
                        return "Hello ";
                    }, poolExecutor)

                    .thenCompose(val -> CompletableFuture.supplyAsync(() -> {
                        System.out.println("Thread name in thenCompose 1: " + Thread.currentThread().getName());
                        return val + " Aditya ";
                    }))

                    .thenCompose(val -> CompletableFuture.supplyAsync(() -> {
                        System.out.println("Thread name in thenCompose 2: " + Thread.currentThread().getName());
                        return val + " Shrivastava ";
                    }, poolExecutor))

                    .thenComposeAsync(val -> CompletableFuture.supplyAsync(() -> {
                        System.out.println("Thread name in thenComposeAsync 3: " + Thread.currentThread().getName());
                        return val + " How are you ";
                    }));

            System.out.println("Final Result (asyncTask2): " + asyncTask2.get());


            /**
             * UseCase 3 - thenCombine() & thenCombineAsync()
             */
            CompletableFuture<String> asyncTask3 = CompletableFuture.supplyAsync(() -> {
                System.out.println("Thread name in asyncTask3: " + Thread.currentThread().getName());
                return "Hello there ";
            }, poolExecutor);

            CompletableFuture<Integer> asyncTask4 = CompletableFuture.supplyAsync(() -> {
                System.out.println("Thread name in asyncTask4: " + Thread.currentThread().getName());
                return 10;
            }, poolExecutor);

            CompletableFuture<String> asyncTask5 = CompletableFuture.supplyAsync(() -> {
                System.out.println("Thread name in asyncTask5: " + Thread.currentThread().getName());
                return " good bye ";
            }, poolExecutor);

            CompletableFuture<String> combinedFutureObj = asyncTask3
                    .thenCombine(asyncTask4, (val1, val2) -> val1 + val2)
                    .thenCombineAsync(asyncTask5, (val1, val2) -> val1 + val2);

            System.out.println("Combined future object: " + combinedFutureObj.get());


            /**
             * UseCase 4 - thenAccept() & thenAcceptAsync()
             */
            CompletableFuture<Void> asyncTask6 = combinedFutureObj.thenAcceptAsync(val -> {
                System.out.println("asyncTask6 running in: " + Thread.currentThread().getName());
                System.out.println(val + " -> added via thenAccept()");
            }, poolExecutor);

            System.out.println("thenAccept future object: " + asyncTask6.get());


            /**
             * UseCase 5 - Using a custom thread pool
             */
            ThreadPoolExecutor customPool = new ThreadPoolExecutor(
                    2,
                    2,
                    2,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(5),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy()
            );

            CompletableFuture<String> newObject = CompletableFuture
                    .supplyAsync(() -> {
                        return "Hey there, executed by: " + Thread.currentThread().getName();
                    }, customPool)

                    .thenApplyAsync(val -> {
                        return val + " | Today is Saturday | Executed by: " + Thread.currentThread().getName();
                    }, customPool);

            System.out.println("Custom pool task result: " + newObject.get());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
