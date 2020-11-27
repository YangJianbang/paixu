import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class 循环栅栏 {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7);
        for (int i = 1; i < 8; i++) {
            new Thread(() -> {
                System.out.println("收集到了"+Thread.currentThread().getName()+"星龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
