package part1.logic.controller;

import part1.logic.activeComponent.SimulationRunner;
import part1.logic.monitor.state.SimulationState;
import part1.logic.simulation.Simulation;
import part1.logic.simulation.SimulationType;
import part1.logic.simulation.listeners.SimulationListener;
import part1.view.RoadSimView;

public class ControllerImpl implements Controller {

    private Simulation simulation;
    private SimulationState simulationState;
    private RoadSimView simulationView;

    public ControllerImpl() {}

    @Override
    public void stopSimulation() {
        this.simulationState.stopSimulation();
    }

    @Override
    public void startSimulation() {
        new SimulationRunner(this.simulation).start();
    }

    @Override
    public void setupSimulation(SimulationType type, int numberOfSteps) {
        if (this.simulationView != null) {
            this.simulationView.dispose();
        }
        this.simulation = type.getSimulation(type);
        this.simulationState = this.simulation.getState();
        this.simulation.setup(numberOfSteps);
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
