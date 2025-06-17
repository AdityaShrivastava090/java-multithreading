package multithreading.basic;

public class MultiThreadLearning implements Runnable{

    @Override
    public void run() {
        System.out.println("code executed by thread "+Thread.currentThread().getName());
    }
}
