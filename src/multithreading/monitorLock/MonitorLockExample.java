package multithreading.monitorLock;

public class MonitorLockExample {

    /**

     */
    public synchronized void task1() {
        try {
            System.out.println("isnide task 1");
            Thread.sleep(5000);
            System.out.println("task 1 completed");
        } catch (Exception e) {
            // exception catch logic
        }
    }

    public synchronized void task2() {
        System.out.println("task 2 but before synchronized");
        synchronized (this) {
            System.out.println("task2 after synchronized");
        }
    }

    public void task3() {
        System.out.println("Task 3");
    }
}
