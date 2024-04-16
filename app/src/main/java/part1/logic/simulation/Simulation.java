package part1.logic.simulation;

import part1.logic.monitor.state.SimulationState;
import part1.logic.simulation.listeners.SimulationListener;

public interface Simulation {

	void setup(int numSteps, int numOfThread);

	void addSimulationListener(SimulationListener listener);

	void doStep();

	long getSimulationDuration();

	long getAverageTimePerCycle();

	void syncWithTime(int nCyclesPerSec);

	SimulationState getState();

}
