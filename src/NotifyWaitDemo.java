import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NotifyWaitDemo {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                shareResource.AA();
            }, "AA").start();
            new Thread(() -> {
                shareResource.BB();
            }, "BB").start();
            new Thread(() -> {
                shareResource.CC();
            }, "CC").start();
        }
    }
}

class ShareResource {
    private int num = 1;
    private Lock lock = new ReentrantLock();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();

    public void AA() {
        lock.lock();
        try {
            while (num != 1) {
                c1.await();
            }
            for (int i = 1; i < 6; i++) {
                System.out.println("i = " + i);
            }
            num = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void BB() {
        lock.lock();
        try {
            while (num != 2) {
                c2.await();
            }
            for (int i = 1; i < 11; i++) {
                System.out.println("i = " + i);
            }
            num = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void CC() {
        lock.lock();
        try {
            while (num != 3) {
                c3.await();
            }
            for (int i = 1; i < 16; i++) {
                System.out.println("i = " + i);
            }
            num = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
