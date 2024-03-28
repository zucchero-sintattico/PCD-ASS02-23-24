package pcd.ass01ridesign.activeComponent;



import pcd.ass01ridesign.SimulationListener;
import pcd.ass01ridesign.monitor.agent.AbstractCarAgent;
import pcd.ass01ridesign.monitor.agent.task.ParallelTask;
import pcd.ass01ridesign.monitor.agent.task.SerialTask;
import pcd.ass01ridesign.monitor.barrier.CyclicBarrier;
import pcd.ass01ridesign.monitor.environment.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Base class for defining concrete simulations
 *  
 */
public abstract class AbstractSimulation {

	/* environment of the simulation */
	private Environment env;
	
	/* list of the agents */
	private List<AbstractCarAgent> agents;
	private List<ParallelTask> parallelTasks;
	private List<SerialTask> serialTasks;
	
	/* simulation listeners */
	private List<SimulationListener> listeners;

	/* logical time step */
	private int dt;
	
	/* initial logical time */
	private int t0;

	/* in the case of sync with wall-time */
	private boolean toBeInSyncWithWallTime;
	private int nStepsPerSec;
	
	/* for time statistics*/
	private long currentWallTime;
	private long startWallTime;
	private long endWallTime;
	private long averageTimePerStep;
	private Random r = new Random();

	private CyclicBarrier barrier1, barrier2;

	protected AbstractSimulation() {
		agents = new ArrayList<AbstractCarAgent>();
		listeners = new ArrayList<SimulationListener>();
		parallelTasks = new ArrayList<ParallelTask>();
		serialTasks = new ArrayList<SerialTask>();
		toBeInSyncWithWallTime = false;
	}
	
	/**
	 * 
	 * Method used to configure the simulation, specifying env and agents
	 * 
	 */
	protected abstract void setup();
	
	/**
	 * Method running the simulation for a number of steps,
	 * using a sequential approach
	 * 
	 * @param numSteps
	 */
	public void run(int numSteps, int numOfThread) {
		barrier1 = new CyclicBarrier(numOfThread+1);
		barrier2 = new CyclicBarrier(numOfThread+1);
		startWallTime = System.currentTimeMillis();

		List<Thread> carsList = new ArrayList<Thread>();


        /* initialize the env and the agents inside */
		int t = t0;

//		env.init();
		for (var a: agents) {
//			a.init(env);
			parallelTasks.add(a.getParallelAction());
			serialTasks.add(a.getSerialAction());
		}


		new MasterWorkerHandler(numOfThread, parallelTasks, numSteps, barrier1, barrier2);

		this.notifyReset(t, agents, env);
		
		long timePerStep = 0;
		int nSteps = 0;
		
		while (nSteps < numSteps) {

			currentWallTime = System.currentTimeMillis();
		
			/* make a step */
			
			env.step();

			System.out.println("Step: " + nSteps);

			try {
				barrier1.hitAndWaitAll();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
//			while(!barrier.isResettable()){}
			for (var a: serialTasks) {
				a.run();
			}

			
			t += dt;
			
			notifyNewStep(t, agents, env);

			nSteps++;			
			timePerStep += System.currentTimeMillis() - currentWallTime;
			
			if (toBeInSyncWithWallTime) {
				syncWithWallTime();
			}
			try {
				barrier2.hitAndWaitAll();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
//			barrier.reset();

		}	
		
		endWallTime = System.currentTimeMillis();
		this.averageTimePerStep = timePerStep / numSteps;
		
	}
	
	public long getSimulationDuration() {
		return (endWallTime - startWallTime);
	}
	
	public long getAverageTimePerCycle() {
		return (averageTimePerStep);
	}
	
	/* methods for configuring the simulation */
	
	protected void setupTimings(int t0, int dt) {
		this.dt = dt;
		this.t0 = t0;
	}
	
	protected void syncWithTime(int nCyclesPerSec) {
		this.toBeInSyncWithWallTime = true;
		this.nStepsPerSec = nCyclesPerSec;
	}
		
	protected void setupEnvironment(Environment env) {
		this.env = env;
	}

	protected void addAgent(AbstractCarAgent agent) {
		agents.add(agent);
	}
	
	/* methods for listeners */
	
	public void addSimulationListener(SimulationListener l) {
		this.listeners.add(l);
	}
	
	private void notifyReset(int t0, List<AbstractCarAgent> agents, Environment env) {
		for (var l: listeners) {
			l.notifyInit(t0, agents, env);
		}
	}

	private void notifyNewStep(int t, List<AbstractCarAgent> agents, Environment env) {
		for (var l: listeners) {
			l.notifyStepDone(t, agents, env);
		}
	}

	/* method to sync with wall time at a specified step rate */
	
	private void syncWithWallTime() {
		try {
			long newWallTime = System.currentTimeMillis();
			long delay = 1000 / this.nStepsPerSec;
			long wallTimeDT = newWallTime - currentWallTime;
			if (wallTimeDT < delay) {
				Thread.sleep(delay - wallTimeDT);
			}
		} catch (Exception ex) {}		
	}
}
