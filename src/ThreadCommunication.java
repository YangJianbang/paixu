import com.sun.org.apache.bcel.internal.generic.NEW;
import jdk.nashorn.internal.ir.IfNode;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//两个线程，一个线程打印1-52，另一个打印字母A-Z打印顺序为12A34B...5152Z
public class ThreadCommunication {
    public static void main(String[] args) {

        Map map = new HashMap();
        DaYin daYin = new DaYin();
        for (int i = 0; i < 26; i++) {
            new Thread(() -> {
                daYin.num();
            }, "AA").start();
            new Thread(() -> {
                daYin.abc();
            }, "BB").start();
        }

    }
}

class DaYin {
    private Lock lock = new ReentrantLock();
    int num = 0;
    int count = 0;
    char letter = 65;
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();

    public void num() {

        lock.lock();
        try {
            while (count != 0) {
                c1.await();
            }
            for (int i = 0; i < 2; i++) {
                num++;
                count++;
                System.out.print(num);
            }
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void abc() {
        lock.lock();
        try {
            while (count != 2) {
                c2.await();
            }
            System.out.println(letter);
            letter++;
            count = 0;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}