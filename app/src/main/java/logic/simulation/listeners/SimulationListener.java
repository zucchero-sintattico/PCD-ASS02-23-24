package logic.simulation.listeners;

import logic.passiveComponent.agent.AbstractCarAgent;
import logic.passiveComponent.environment.Environment;
import java.util.List;

public interface SimulationListener {

	void notifyInit(int t, List<AbstractCarAgent> agents, Environment env);

	void notifyStepDone(int t, List<AbstractCarAgent> agents, Environment env);

	void notifySimulationEnded();

	void notifyStat(double averageSpeed);

}
