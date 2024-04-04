package logic.controller;

import logic.simulation.SimulationType;
import logic.simulation.listeners.SimulationListener;

public interface Controller {

	void startSimulation();

	void stopSimulation();

	void setupSimulation(SimulationType type, int numberOfSteps, int numberOfThread);

	void showView();

	void attachListener(SimulationListener listener);

	int getAvailableProcessor();

	long getSimulationDuration();

}
