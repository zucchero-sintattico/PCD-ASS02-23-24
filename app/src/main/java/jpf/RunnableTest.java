package jpf;

import logic.monitor.barrier.Barrier;
import logic.monitor.barrier.CyclicBarrier;

public class RunnableTest {

    private static int i = 0;

    private static void inc(){i++;}

    public static void main(String[] args) throws InterruptedException {
        int iterations = 500000;
        Barrier barrier = new CyclicBarrier(1, RunnableTest::inc);
        Thread thread = new Thread(()->{
                for (int j = 0; j < iterations; j++) {
                    try {
                        barrier.hitAndWaitAll();
                    } catch (InterruptedException ignored){}
                }
            }
        );
        thread.start();
        for (int j = 0; j < iterations; j++) {
            inc();
        }
        thread.join();
        System.out.println(i); // Expected: 1000000, Example Actual: 989701
    }

}
