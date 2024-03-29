package pcd.ass01ridesign.simulation;

import pcd.ass01ridesign.monitor.state.SimulationState;
import pcd.ass01ridesign.simulation.listeners.SimulationListener;

public interface Simulation {

    void setup(int numSteps, int numOfThread);

    void addSimulationListener(SimulationListener listener);

    void doStep();

    long getSimulationDuration();

    long getAverageTimePerCycle();

    void syncWithTime(int nCyclesPerSec);

    SimulationState getState();
}
