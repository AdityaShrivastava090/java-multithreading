package multithreading.basicProdcerConsumer;

public class Main {

    public static void main(String[] args) {

        SharedResource sharedResource = new SharedResource();
        Thread producerThread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                // exception handling
            }
            sharedResource.addItem();

        });
        Thread consumerThread = new Thread(() -> {
            sharedResource.consumeItem();
        });
        producerThread.start();
        consumerThread.start();
    }
}
