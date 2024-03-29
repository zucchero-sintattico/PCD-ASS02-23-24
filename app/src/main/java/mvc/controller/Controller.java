package mvc.controller;

import model.SimulationType;
import pcd.ass01.simengineseq.SimulationListener;

public interface Controller {
    
    void startSimulation(boolean showView, int numberOfSteps, int numberOfThread);

    void stopSimulation();

    void resetSimulation();

    void updateType(SimulationType type);

    void attachListener(SimulationListener listener);

    SimulationType getSimulationType();

    int getAvaiableProcessor();

    long getSimulationDuration();
}
