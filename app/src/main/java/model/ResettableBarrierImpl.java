package model;

public class ResettableBarrierImpl implements ResettableBarrier {

    private final int nParticipants;
    private int arrived = 0;
    private int exited = 0;
    private boolean broken, resettable, reset = true;

    public ResettableBarrierImpl(int nParticipants){
        this.nParticipants = nParticipants;
    }

    @Override
    public synchronized void hitAndWaitAll() throws InterruptedException {

        while(!reset){
            wait();
        }
        arrived++;
//        System.out.println("a"+arrived);
        if (arrived == nParticipants){
            broken = true;
            reset = false;
            arrived = 0;
            notifyAll();
        }
        while (!broken) {
            wait();
        }
        exited++;
//            System.out.println("e"+exited);
        if (exited == nParticipants) {
            resettable = true;
//                System.out.println("resettable");
            exited = 0;
            reset();
        }



    }

    @Override
    public synchronized void reset() {
//        System.out.println("reset");
        reset = true;
        broken = false;
        resettable = false;
        notifyAll();
    }

    @Override
    public synchronized boolean isResettable(){
        return resettable;
    }
}