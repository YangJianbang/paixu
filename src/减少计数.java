import java.util.concurrent.CountDownLatch;

public class 减少计数 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i < 7; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"号同学离开了教室");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"克里斯关下门");
    }
}
