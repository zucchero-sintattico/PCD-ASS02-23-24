package pcd.ass01.passiveComponent.agent;

import pcd.ass01.passiveComponent.agent.task.ParallelTask;
import pcd.ass01.passiveComponent.agent.task.SerialTask;
import pcd.ass01.passiveComponent.simulation.SimulationComponent;

public interface Agent extends SimulationComponent {

    ParallelTask getParallelAction();

    SerialTask getSerialAction();

}
