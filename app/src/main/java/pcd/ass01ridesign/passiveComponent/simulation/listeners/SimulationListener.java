package pcd.ass01ridesign.passiveComponent.simulation.listeners;


import pcd.ass01ridesign.passiveComponent.agent.AbstractCarAgent;
import pcd.ass01ridesign.passiveComponent.environment.Environment;

import java.util.List;

public interface SimulationListener {

	/**
	 * Called at the beginning of the simulation
	 * 
	 * @param t
	 * @param agents
	 * @param env
	 */
	void notifyInit(int t, List<AbstractCarAgent> agents, Environment env);
	
	/**
	 * Called at each step, updater all updates
	 * @param t
	 * @param agents
	 * @param env
	 */
	void notifyStepDone(int t, List<AbstractCarAgent> agents, Environment env);
}
