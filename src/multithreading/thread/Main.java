package multithreading.thread;

public class Main {

    public static void main(String[] args) {
        System.out.println("going inside main method  "+ Thread.currentThread().getName());
        MultiThreadingLearning myThread = new MultiThreadingLearning();
        myThread.start();
        System.out.println("finsih main method... "+ Thread.currentThread().getName());
    }
}


class MultiThreadingLearning extends Thread {

    @Override
    public void run() {
        System.out.println("code execued by thread..... "+ Thread.currentThread().getThreadGroup());
    }


}