package part1.logic.controller;

import part1.logic.simulation.SimulationType;
import part1.logic.simulation.listeners.SimulationListener;

public interface Controller {

	void startSimulation();

	void stopSimulation();

	void setupSimulation(SimulationType type, int numberOfSteps);

	void showView();

	void attachListener(SimulationListener listener);

	int getAvailableProcessor();

	long getSimulationDuration();

}
