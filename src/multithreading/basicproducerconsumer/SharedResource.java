package multithreading.basicproducerconsumer;

public class SharedResource {

    boolean itemPresent = false;

    public synchronized void addItem() {
        System.out.println("Producer thread calling add item method...");
        itemPresent = true;
        System.out.println("produced.....");
        notifyAll();
    }

    public synchronized void consumeItem() {
        System.out.println("consumer thread  inside consume item method");
        while (!itemPresent) {
            try {
                System.out.println("consumer thread waiting....");
                wait();
            } catch (Exception e) {
                // exception handling
            }
        }
        System.out.println("consumer thread wait over....");
        itemPresent = false;
        System.out.println("consumed......");
    }
}
