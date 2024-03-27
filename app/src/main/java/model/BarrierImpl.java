package model;

public class BarrierImpl implements Barrier{
    private final int nParticipants;
    private int arrived = 0;
    private boolean canPass = false;

    public BarrierImpl(int nParticipants){
        this.nParticipants = nParticipants;
    }

    @Override
    public synchronized void hitAndWaitAll() throws InterruptedException {
        arrived++;
        if (arrived == nParticipants){
            canPass = true;
            notifyAll();
        }
        else{
            while (!canPass){
                wait();
            }
        }
    }

    @Override
    public void reset() {
        canPass = false;
        arrived = 0;
    }
}