package multithreading.suspendExample;

public class SharedResource {

    boolean isPresent = false;

    public synchronized void produce() {
        System.out.println("lock acquired " + Thread.currentThread().getName());
        isPresent = true;
        try {
            Thread.sleep(8000);
        } catch (Exception e) {
            // exception handling
        }
        System.out.println("lock release by " + Thread.currentThread().getName());
    }
}
