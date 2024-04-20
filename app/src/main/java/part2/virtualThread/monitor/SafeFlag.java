package part2.virtualThread.monitor;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SafeFlag {

	private final Lock lock = new ReentrantLock();

	private boolean flag;

	public SafeFlag(){};

	public SafeFlag(boolean flag){
		this.flag = flag;
	};

	public void stopSimulation() {
		try {
			lock.lock();
			flag = false;
		} finally {
			lock.unlock();
		}
	}

	public void startSimulation() {
		try {
			lock.lock();
			flag = true;
		} finally {
			lock.unlock();
		}
	}

	public boolean isSimulationRunning() {
		try {
			lock.lock();
			return flag;
		} finally {
			lock.unlock();
		}
	}

}
