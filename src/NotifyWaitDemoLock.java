import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NotifyWaitDemoLock {
    public static void main(String[] args) {
        ShareDataOne shareDataOne = new ShareDataOne();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareDataOne.increment();
            }
        }, "aa").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareDataOne.decrement();
            }
        }, "bb").start();
    }
}

class ShareDataOne {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void increment() {
        lock.lock();

        try {
            while (number != 0) {
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + ":" + number);
            condition.signalAll();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }

    }

    public void decrement()  {
        lock.lock();
        try {
            while (number != 1) {
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + ":" + number);
            condition.signalAll();
        }catch (Exception e){

        }finally {
            lock.unlock();
        }


    }
}
