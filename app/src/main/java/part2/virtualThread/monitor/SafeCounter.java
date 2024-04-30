package part2.virtualThread.monitor;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SafeCounter {

	private final Lock lock = new ReentrantLock();

	private int count;

	public void inc() {
		try {
			lock.lock();
			this.count++;
		} finally {
			lock.unlock();
		}
	}

	public void dec(String log) {
		try {
			lock.lock();
			this.count--;
			System.out.println(log + " " + this.count);
		} finally {
			lock.unlock();
		}
	}

	public int getValue() {
		try {
			lock.lock();
			return this.count;
		} finally {
			lock.unlock();
		}
	}

	public void update(int increment) {
		try {
			lock.lock();
			this.count += increment;
		} finally {
			lock.unlock();
		}
	}

    public void reset() {
		try {
			lock.lock();
			this.count = 0;
		} finally {
			lock.unlock();
		}
    }
}