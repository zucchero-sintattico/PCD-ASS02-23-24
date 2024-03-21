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
	public void step(Semaphore sema, Semaphore semaA1, Semaphore sema1, Semaphore semaA11) {

		/* sense */
		try {

			semaA1.acquire();
//			System.out.println("semarealse");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		AbstractEnvironment env = this.getEnv();		
		currentPercept = (CarPercept) env.getCurrentPercepts(getAgentId());			

		/* decide */
		
		selectedAction = Optional.empty();

		decide(dt);
		sema.release();

		try {
			semaA11.acquire();
//			System.out.println("semarealse,semaA11aq");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		/* act */
		
		if (selectedAction.isPresent()) {
			env.doAction(getAgentId(), selectedAction.get());
		}
		sema1.release();
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
