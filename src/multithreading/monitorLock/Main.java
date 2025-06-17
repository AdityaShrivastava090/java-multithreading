package multithreading.monitorLock;

public class Main {
    public static void main(String[] args) {
        MonitorLockExample obj = new MonitorLockExample();

        /**
         *  Here you can notice the same object si being shared by three threads
         *  hence synchronized lock to access it.
         */
        Thread t1 = new Thread(() -> obj.task1());
        Thread t2 = new Thread(() -> obj.task2());
        Thread t3 = new Thread(() -> obj.task3());

        t1.start();
        t2.start();
        t3.start();
    }
}
