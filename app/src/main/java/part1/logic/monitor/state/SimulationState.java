package part1.logic.monitor.state;

public class SimulationState {

	private boolean isRunning;

	public synchronized void stopSimulation() {
		isRunning = false;
	}

	public synchronized void startSimulation() {
		isRunning = true;
	}

	public synchronized boolean isSimulationRunning() {
		return isRunning;
	}

}
