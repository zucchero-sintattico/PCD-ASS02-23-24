package part1.logic.passiveComponent.agent;

import part1.logic.passiveComponent.environment.Environment;
import part1.logic.passiveComponent.agent.action.Action;
import part1.logic.passiveComponent.agent.perception.Perception;
import part1.logic.passiveComponent.agent.task.ParallelTask;
import part1.logic.passiveComponent.agent.task.SerialTask;
import part1.logic.passiveComponent.environment.road.Road;
import java.util.Random;

public abstract class AbstractCarAgent implements Car, Agent {

	private final String agentID;
	private final Environment environment;
	protected final double maxSpeed;
	protected double currentSpeed;
	protected final double acceleration;
	protected final double deceleration;
	protected int stepSize;
	protected static final int CAR_NEAR_DIST = 15;
	protected static final int CAR_FAR_ENOUGH_DIST = 20;
	protected static final int MAX_WAITING_TIME = 2;
	protected int waitingTime;
	protected Perception currentPerception;
	protected Action selectedAction;
	private double position;
	private final Road road;

	public AbstractCarAgent(String agentID, Environment environment, Road road,
	                        double initialPosition,
	                        double acceleration,
	                        double deceleration,
	                        double maxSpeed
	) {
		this.agentID = agentID;
		this.acceleration = acceleration;
		this.deceleration = deceleration;
		this.maxSpeed = maxSpeed;
		this.environment = environment;
		this.road = road;
		this.position = initialPosition;
		this.environment.registerNewCarAgent(this);
	}

	public AbstractCarAgent(String agentID, Environment environment, Road road, double initialPosition, int seed) {
		Random gen = new Random(seed);
		this.agentID = agentID;
		this.acceleration = 1 + gen.nextDouble(0, 1);
		this.deceleration = 0.3 + gen.nextDouble(0, 1);
		this.maxSpeed = 4 + gen.nextDouble(1, 10);
		this.environment = environment;
		this.road = road;
		this.position = initialPosition;
		this.environment.registerNewCarAgent(this);
	}

	protected abstract void decide();

	private void senseAndDecide() {
		this.sense();
		this.selectedAction = null;
		this.decide();
	}

	private void sense() {
		this.currentPerception = this.environment.getCurrentPerception(this.getAgentID());
	}

	private void doAction() {
		if (this.selectedAction != null) {
			this.environment.doAction(this.getAgentID(), selectedAction);
		}
	}

	@Override
	public String getAgentID() {
		return this.agentID;
	}

	@Override
	public double getCurrentSpeed() {
		return this.currentSpeed;
	}

	@Override
	public double getPosition() {
		return position;
	}

	@Override
	public void updatePosition(double position) {
		this.position = position;
	}

	@Override
	public Road getRoad() {
		return this.road;
	}

	@Override
	public ParallelTask getParallelAction() {
		return this::senseAndDecide;
	}

	@Override
	public SerialTask getSerialAction() {
		return this::doAction;
	}

	@Override
	public void setup(int dt) {
		this.stepSize = dt;
	}


}
