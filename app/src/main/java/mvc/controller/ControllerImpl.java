package mvc.controller;

import pcd.ass01.activeComponent.SimulationRunner;
import pcd.ass01.monitor.state.SimulationState;
import pcd.ass01.passiveComponent.simulation.AbstractSimulation;
import pcd.ass01.passiveComponent.simulation.SimulationType;
import pcd.ass01.passiveComponent.simulation.listeners.RoadSimView;
import pcd.ass01.passiveComponent.simulation.listeners.SimulationListener;

public class ControllerImpl implements Controller {

    private AbstractSimulation simulation;
    private SimulationState simulationState;
    private RoadSimView simulationView;
    private boolean showView;

    public ControllerImpl() {
        this.showView = false;
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
        if(this.simulationView != null){
            this.simulationView.dispose();
        }
        this.simulation = type.getSimulation(type);
        this.simulationState = this.simulation.getState();
        this.simulation.setup(numberOfSteps, numberOfThread);
    }

    @Override
    public int getAvaiableProcessor() {
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
