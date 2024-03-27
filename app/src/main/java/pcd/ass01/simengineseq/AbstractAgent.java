package pcd.ass01.simengineseq;

import java.util.concurrent.Semaphore;

/**
 * 
 * Base  class for defining types of agents taking part to the simulation
 * 
 */
public abstract class AbstractAgent implements Runnable {
	
	private String myId;
	private AbstractEnvironment env;
	private Semaphore sema;
	private Semaphore sema1;


	/**
	 * Each agent has an identifier
	 * 
	 * @param id
	 */
	protected AbstractAgent(String id) {
		this.myId = id;
	}
	
	/**
	 * This method is called at the beginning of the simulation
	 * 
	 * @param env
	 */
	public void init(AbstractEnvironment env) {
		this.env = env;
	}
	
	/**
	 * This method is called at each step of the simulation
	 * 
	 */

	public abstract void sensAndDecide(Semaphore sema, Semaphore sema1);

	/**
	 *
	 * @return the identifier of the agent
	 */

	public abstract void doAction();

	
	
	public String getAgentId() {
		return myId;
	}
	
	protected AbstractEnvironment getEnv() {
		return this.env;
	}

	@Override
	public void run() {
		sensAndDecide(sema, sema1);
	}


	public void setSema(Semaphore s, Semaphore sA1) {
		this.sema = s;
		this.sema1 = sA1;
	}
}
