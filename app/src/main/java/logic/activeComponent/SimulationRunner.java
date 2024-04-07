package logic.activeComponent;

import logic.simulation.Simulation;

public class SimulationRunner extends Thread {

	private final Simulation simulation;

	public SimulationRunner(Simulation simulation) {
		this.simulation = simulation;
	}

	@Override
	public void run() {
		simulation.getState().startSimulation();
		while (simulation.getState().isSimulationRunning()) {
			simulation.doStep();
		}
	}

}

