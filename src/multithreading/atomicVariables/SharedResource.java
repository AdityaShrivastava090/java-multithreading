package multithreading.atomicVariables;

import java.util.concurrent.atomic.AtomicInteger;

public class SharedResource {

    int counter = 0;

    public synchronized void incrementCounter(){
        counter++;
        /**
         *  not an atomic operation  as it has 3 operations inside it
         *  load counter
         *  increment it
         *  assign it back to counter
         */
    }



    /***
     * to make above counter thread safe we have two solutions for it
     *   using lock synchronized
     *   using lock free operation like AtomicInteger.
     *
     */

    AtomicInteger counterr = new AtomicInteger(0);

    public void incrementAtomicInteger(){
        counterr.incrementAndGet();
    }
    public int get(){
        return counterr.get();
    }

    public int getCounter() {
        return counter;
    }
}
