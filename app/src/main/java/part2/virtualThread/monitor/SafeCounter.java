package part2.virtualThread.monitor;

public class SafeCounter {

	private int count;

	public synchronized void inc() {
		this.count++;
	}
	public synchronized int getValue() {
		return this.count;
	}
	public synchronized void update(int increment) {
		this.count += increment;
	}
}