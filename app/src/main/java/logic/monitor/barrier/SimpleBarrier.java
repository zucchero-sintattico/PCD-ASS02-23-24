package logic.monitor.barrier;

public class SimpleBarrier implements Barrier {

	private final int numberOfParticipants;
	private int participants = 0;
	private boolean broken;

	public SimpleBarrier(int numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
	}

	@Override
	public synchronized void hitAndWaitAll() throws InterruptedException {
		evaluateParticipants(this::breakBarrier);
		while (!broken) {
			wait();
		}
	}

	private void breakBarrier() {
		broken = true;
		this.setupAndNotify();
	}

	private void setupAndNotify() {
		participants = 0;
		notifyAll();
	}

	private void evaluateParticipants(Runnable action) {
		participants++;
		if (participants == numberOfParticipants) {
			action.run();
		}
	}

}
