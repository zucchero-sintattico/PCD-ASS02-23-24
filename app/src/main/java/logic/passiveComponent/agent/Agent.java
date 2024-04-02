package logic.passiveComponent.agent;

import logic.passiveComponent.simulation.SimulationComponent;
import logic.passiveComponent.agent.task.ParallelTask;
import logic.passiveComponent.agent.task.SerialTask;

public interface Agent extends SimulationComponent {

    ParallelTask getParallelAction();

    SerialTask getSerialAction();

}
