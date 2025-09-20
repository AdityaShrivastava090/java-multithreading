package multithreading.lock.semaphore;

import java.util.concurrent.Semaphore;

class Printer {
    private final Semaphore semaphore = new Semaphore(2); // allow 2 threads at a time

    public void print(String doc) {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " printing: " + doc);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }
}

public class SemaphoreExample {
    public static void main(String[] args) {
        Printer printer = new Printer();

        for (int i = 1; i <= 5; i++) {
            String doc = "Doc-" + i;
            new Thread(() -> printer.print(doc), "Thread-" + i).start();
        }
    }
}
