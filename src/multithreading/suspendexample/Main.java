package multithreading.suspendexample;

public class Main {

    public static void main(String[] args) {

        SharedResource resource = new SharedResource();
        System.out.println("Main Thread started");

        Thread t1 = new Thread(() -> {
            System.out.println("thread 1 calling produce method");
            resource.produce();
        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                // exception handling
            }

            System.out.println("Thread2 calling produce method");
            resource.produce();
        });

        /**
         *  Priority - 1 is lowest , 10 is highest
         *  but jvm doesn't  guarantee to follow priority order
         *  its just gives a hint to follow the priority but not strictly guaranteed.
         */
        t1.setPriority(Thread.MAX_PRIORITY);
        /**
         *  Daemon - tells the thread its daemon thread now
         *  atleast one normal user thread should be alive for a daemon thread to work
         *  ese it will stop
         *
         *  example - Garbage collector, AutoSave, Logging
         */
        t1.setDaemon(true);
        t1.start();

        /**
         *  Thread 2 - concept of suspend and resume, not used though.
         */
        t2.start();
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            // handling exception
        }
        System.out.println("thread 1 is suspended");
        t1.suspend();
        System.out.println("Thread 1 is resumed");
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            //
        }
        t1.resume();

        /**
         *    JOIN - Main thread will wait for t1 thread to finish
         *
         */
//        try {
//            t1.join();
//        } catch (Exception e) {
//            // excpetion handling
//        }

        System.out.println("Main thread is finishing......");
    }
}
