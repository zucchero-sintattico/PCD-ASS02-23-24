package logic.passiveComponent.simulation;

import logic.monitor.state.SimulationState;
import logic.passiveComponent.simulation.listeners.SimulationListener;

public interface Simulation {

	void setup(int numSteps, int numOfThread);

	void addSimulationListener(SimulationListener listener);

	void doStep();

	long getSimulationDuration();

	long getAverageTimePerCycle();

	void syncWithTime(int nCyclesPerSec);

	SimulationState getState();

}
