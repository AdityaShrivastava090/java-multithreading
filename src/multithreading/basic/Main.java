package multithreading.basic;

public class Main {
    public static void main(String[] args) {


        System.out.println("Going in Main thread  "+ Thread.currentThread().getName());
        MultiThreadLearning obj = new MultiThreadLearning();
        Thread th = new Thread(obj);
        th.start();
        System.out.println("finish the Main method "+ Thread.currentThread().getName());
    }
}
