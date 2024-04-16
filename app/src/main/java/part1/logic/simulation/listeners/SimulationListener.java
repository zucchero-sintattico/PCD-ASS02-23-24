package part1.logic.simulation.listeners;

import part1.logic.passiveComponent.agent.AbstractCarAgent;
import part1.logic.passiveComponent.environment.Environment;
import java.util.List;

public interface SimulationListener {

	void notifyInit(int t, List<AbstractCarAgent> agents, Environment env);

	void notifyStepDone(int t, List<AbstractCarAgent> agents, Environment env);

	void notifySimulationEnded();

	void notifyStat(double averageSpeed);

}
