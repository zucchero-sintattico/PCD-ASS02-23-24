package pcd.ass01ridesign.passiveComponent.agent;

import pcd.ass01ridesign.passiveComponent.agent.task.ParallelTask;
import pcd.ass01ridesign.passiveComponent.agent.task.SerialTask;
import pcd.ass01ridesign.simulation.SimulationComponent;

public interface Agent extends SimulationComponent {

    ParallelTask getParallelAction();

    SerialTask getSerialAction();

}
