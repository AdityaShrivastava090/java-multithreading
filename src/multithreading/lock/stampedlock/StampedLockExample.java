package multithreading.lock.stampedlock;

import java.util.concurrent.locks.StampedLock;

class DataContainer {
    private int data = 0;
    private final StampedLock lock = new StampedLock();

    public void write(int value) {
        long stamp = lock.writeLock();
        try {
            System.out.println(Thread.currentThread().getName() + " writing: " + value);
            data = value;
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public int read() {
        long stamp = lock.tryOptimisticRead();
        int result = data;
        if (!lock.validate(stamp)) {  // check if write happened during read
            stamp = lock.readLock();
            try {
                result = data;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        System.out.println(Thread.currentThread().getName() + " reading: " + result);
        return result;
    }
}

public class StampedLockExample {
    public static void main(String[] args) {
        DataContainer container = new DataContainer();

        new Thread(() -> container.write(200), "Writer").start();
        new Thread(() -> container.read(), "Reader").start();
    }
}
