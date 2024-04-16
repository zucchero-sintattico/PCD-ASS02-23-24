package part1.logic.monitor.barrier;

public class CyclicBarrier implements Barrier {

	private final int numberOfParticipants;
	private int participants = 0;
	private boolean broken;
	private final Runnable postprocessing;

	public CyclicBarrier(int numberOfParticipants) {
		this(numberOfParticipants, () -> {});
	}

	public CyclicBarrier(int numberOfParticipants, Runnable postprocessing) {
		this.numberOfParticipants = numberOfParticipants;
		this.postprocessing = postprocessing;
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
		postprocessing.run();
		this.setupAndNotify();
	}

	private void reset() {
		broken = false;
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
