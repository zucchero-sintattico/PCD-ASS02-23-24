package part2.virtualThread.monitor;

public class SafeFlag {

	private boolean flag;

	public SafeFlag(){};
	public SafeFlag(boolean flag){
		this.flag = flag;
	};


	public synchronized void stopSimulation() {
		flag = false;
	}

	public synchronized void startSimulation() {
		flag = true;
	}

	public synchronized boolean isSimulationRunning() {
		return flag;
	}

}
