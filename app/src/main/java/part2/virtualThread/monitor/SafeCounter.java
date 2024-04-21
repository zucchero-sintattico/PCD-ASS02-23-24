package part2.virtualThread.monitor;

public class SafeCounter {

	private int count;

	public SafeCounter() {
		count = 0;
	}

	public synchronized void inc() {
		count++;
	}

	public synchronized int getValue() {
		return count;
	}

}