import com.sun.xml.internal.ws.api.message.MessageWritable;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SaleTicket {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 40; i++) {
                    ticket.sale();
                }
            }
        }, "AA").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 40; i++) {
                    ticket.sale();
                }
            }
        }, "BB").start();
        new Thread(()->{
            for (int i = 0; i <= 40; i++) {
                ticket.sale();
            }
        },"CC").start();
    }
}

class Ticket {
    private int number = 30;

    //    lock实现卖票
    public void sale() {
//        引入可重入锁ReentrantLock
        Lock lock = new ReentrantLock();
        //上锁
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "售票，余票：" + number--);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //解锁
            lock.unlock();
        }
    }


//    synchronized 卖票方法
//    public synchronized void sale() {
//        if (number > 0) {
//            System.out.println(Thread.currentThread().getName() + "售票，余票：" + number--);
//        }
//    }
}