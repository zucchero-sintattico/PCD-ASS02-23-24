package pcd.ass01.simengineseq;

import java.util.concurrent.Semaphore;

/**
 * 
 * Base  class for defining types of agents taking part to the simulation
 * 
 */
public abstract class AbstractAgent extends Thread{
	
	private String myId;
	private AbstractEnvironment env;

	private Semaphore sema;
	private Semaphore semaA1;
	private Semaphore sema1;
	private Semaphore semaA11;

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
	abstract public void step(Semaphore sema, Semaphore semaA1, Semaphore sema1, Semaphore semaA11);
	
	
	public String getAgentId() {
		return myId;
	}
	
	protected AbstractEnvironment getEnv() {
		return this.env;
	}

	@Override
	public void run() {
		while (true) {
//            try {
//                semaA1.acquire();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            step(sema, semaA1, sema1, semaA11);
//			sema.release();
		}
	}




	public void setSema(Semaphore s, Semaphore sA1, Semaphore s1, Semaphore sA11) {
		this.sema = s;
		this.semaA1 = sA1;
		this.sema1 = s1;
		this.semaA11 = sA11;
	}
}
