package logic.controller;

import logic.activeComponent.SimulationRunner;
import logic.monitor.state.SimulationState;
import logic.passiveComponent.simulation.Simulation;
import logic.passiveComponent.simulation.SimulationType;
import logic.passiveComponent.simulation.listeners.SimulationListener;
import view.RoadSimView;

public class ControllerImpl implements Controller {

    private Simulation simulation;
    private SimulationState simulationState;
    private RoadSimView simulationView;

    public ControllerImpl() {
    }

    @Override
    public void stopSimulation() {
        this.simulationState.stopSimulation();
    }

    @Override
    public void startSimulation() {
        new SimulationRunner(this.simulation).start();
    }

    @Override
    public void setupSimulation(SimulationType type, int numberOfSteps, int numberOfThread) {
        if (this.simulationView != null) {
            this.simulationView.dispose();
        }
        this.simulation = type.getSimulation(type);
        this.simulationState = this.simulation.getState();
        this.simulation.setup(numberOfSteps, numberOfThread);
    }

    @Override
    public int getAvailableProcessor() {
        return Runtime.getRuntime().availableProcessors();
    }

    @Override
    public void attachListener(SimulationListener listener) {
        this.simulation.addSimulationListener(listener);
    }

    @Override
    public long getSimulationDuration() {
        return this.simulation.getSimulationDuration();
    }

    @Override
    public void showView() {
        this.simulationView = new RoadSimView();
        this.simulationView.display();
        this.simulation.addSimulationListener(simulationView);
    }

}
