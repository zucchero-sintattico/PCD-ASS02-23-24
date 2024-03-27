package pcd.ass01.simtrafficbase;

import java.util.Optional;
import java.util.concurrent.Semaphore;

import pcd.ass01.simengineseq.*;

/**
 * 
 * Base class modeling the skeleton of an agent modeling a car in the traffic environment
 * 
 */
public abstract class CarAgent extends AbstractAgent {
	
	/* car model */
	protected double maxSpeed;		
	protected double currentSpeed;  
	protected double acceleration;
	protected double deceleration;

	/* percept and action retrieved and submitted at each step */
	protected CarPercept currentPercept;
	protected Optional<Action> selectedAction;

	int dt = 1;
	
	
	public CarAgent(String id, RoadsEnv env, Road road, 
			double initialPos, 
			double acc, 
			double dec,
			double vmax) {
		super(id);
		this.acceleration = acc;
		this.deceleration = dec;
		this.maxSpeed = vmax;
		env.registerNewCar(this, road, initialPos);
	}

	/**
	 * 
	 * Basic behaviour of a car agent structured into a sense/decide/act structure 
	 * 
	 */
	public void sensAndDecide(Semaphore sema, Semaphore sema1) {

		/* sense */
		try {
			sema1.acquire();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		AbstractEnvironment env = this.getEnv();		
		currentPercept = (CarPercept) env.getCurrentPercepts(getAgentId());			

		/* decide */
		
		selectedAction = Optional.empty();

		decide(dt);

		sema.release();

	}

	public void doAction() {
		AbstractEnvironment env = this.getEnv();
		env.doAction(getAgentId(), selectedAction);
	}
	
	/**
	 * 
	 * Base method to define the behaviour strategy of the car
	 * 
	 * @param dt
	 */
	protected abstract void decide(int dt);
	
	public double getCurrentSpeed() {
		return currentSpeed;
	}
	
	protected void log(String msg) {
		System.out.println("[CAR " + this.getAgentId() + "] " + msg);
	}

	
}
