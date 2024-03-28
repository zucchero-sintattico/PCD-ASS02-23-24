package pcd.ass01ridesign.monitor.barrier;

import java.util.function.Consumer;

public class CyclicBarrier implements Barrier {

    private final int numberOfParticipants;
    private int participants = 0;
    private boolean broken;

    public CyclicBarrier(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    @Override
    public synchronized void hitAndWaitAll() throws InterruptedException {
        this.enterBarrier();
        while (!broken) {
            wait();
        }
        this.passBarrier();
    }

    private void enterBarrier() throws InterruptedException {
        while (broken) {
            wait();
        }
       evaluateParticipants(this::breakBarrier);
    }

    private void passBarrier() {
        evaluateParticipants(this::reset);
    }

    private void breakBarrier() {
        broken = true;
        this.setupAndNotify();
    }

    private synchronized void reset() {
        broken = false;
        this.setupAndNotify();
    }

    private void setupAndNotify() {
        participants = 0;
        notifyAll();
    }

    private void evaluateParticipants(Runnable action){
        participants++;
        if (participants == numberOfParticipants) {
            action.run();
        }
    }

}
