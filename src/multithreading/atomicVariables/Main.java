package multithreading.atomicVariables;

public class Main {

    public static void main(String[] args) {

        SharedResource sh = new SharedResource();

//        for(int i = 0 ; i<400 ; i++){
//            sh.incrementCounter();
//        }
//        System.out.println(sh.getCounter());

        Thread t1 = new Thread(() ->{
            for(int i = 0 ; i<400 ; i++){
                //sh.incrementCounter();  //normal method;
                sh.incrementAtomicInteger();
            }

        });

        Thread t2 = new Thread(() ->{
            for(int i = 0 ; i<400 ; i++){
//                sh.incrementCounter(); //normal method;
                sh.incrementAtomicInteger();
            }

        });

        t1.start();
        t2.start();

        try{
            t1.join();
            t2.join();
        }catch (Exception e){
            //
        }
        System.out.println(sh.getCounter()); // doesnt give output as 800 it means its not thread safe as there's some data loss while reading and updating
        System.out.println(sh.get()); // this will give correct output as 800 due to usage of atomic integer

    }
}
