package multithreading.threadPoolExecutor;

import java.util.concurrent.*;

public class ExecutorService {

    public static void main(String[] args) {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,4,5,TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(2),new customRejectHandler()
        );

        for(int i = 0; i<9; i++){
            threadPoolExecutor.submit(() ->{
                try{
                    Thread.sleep(5000);
                }catch (Exception e){
                    //
                }
                System.out.println("Task processed by :  "+ Thread.currentThread().getName());
            });
//            threadPoolExecutor.shutdown();
        }
    }
}

class customRejectHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("Task Rejected :  " + r.toString());
    }
}

class customthreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread th = new Thread(r);
        th.setDaemon(false);
        th.setPriority(Thread.NORM_PRIORITY);
        return th;
    }
}