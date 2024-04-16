package part1.logic.passiveComponent.agent;

import part1.logic.simulation.SimulationComponent;
import part1.logic.passiveComponent.agent.task.ParallelTask;
import part1.logic.passiveComponent.agent.task.SerialTask;

public interface Agent extends SimulationComponent {

    ParallelTask getParallelAction();

    SerialTask getSerialAction();

}
