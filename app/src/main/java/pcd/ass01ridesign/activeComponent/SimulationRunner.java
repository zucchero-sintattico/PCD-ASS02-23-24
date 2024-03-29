package pcd.ass01ridesign.activeComponent;

import pcd.ass01ridesign.monitor.state.SimulationState;
import pcd.ass01ridesign.passiveComponent.simulation.Simulation;

public class SimulationRunner extends Thread {

    private final Simulation simulation;
    private final SimulationState state;

    public SimulationRunner(Simulation simulation) {
        this.simulation = simulation;
        this.state = simulation.getState();
    }

    @Override
    public void run() {
        state.startSimulation();
        while (state.isSimulationRunning()) {
            simulation.doStep();
        }
    }



}

