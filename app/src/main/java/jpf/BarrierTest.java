package jpf;

import logic.monitor.barrier.Barrier;
import logic.monitor.barrier.CyclicBarrier;

public class BarrierTest {

    public static void main(String[] args) {
        int participants = 2; //works with 2, breaks with 3 (not enough threads to hit the barrier)
        Barrier barrier = new CyclicBarrier(participants);
        Counter counter = new Counter();

        new SimpleSimulationWorker(barrier,participants,counter).start();
        new SimpleSimulationWorker(barrier,participants,counter).start();
    }

    private static class SimpleSimulationWorker extends Thread{

        private final Barrier barrier;
        private final Counter counter;
        private final int participants;

        public SimpleSimulationWorker(Barrier barrier, int participants, Counter counter){
            this.barrier = barrier;
            this.participants = participants;
            this.counter = counter;
        }

        @Override
        public void run() {
            try {
                assert this.counter.getValue() == 0;
                this.barrier.hitAndWaitAll();
                this.counter.inc();
                this.barrier.hitAndWaitAll();
                assert this.counter.getValue() == participants;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
