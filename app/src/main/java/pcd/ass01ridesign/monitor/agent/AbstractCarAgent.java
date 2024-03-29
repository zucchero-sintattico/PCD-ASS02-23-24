package pcd.ass01ridesign.monitor.agent;


import pcd.ass01ridesign.monitor.agent.action.Action;
import pcd.ass01ridesign.monitor.agent.perception.Percept;
import pcd.ass01ridesign.monitor.agent.task.ParallelTask;
import pcd.ass01ridesign.monitor.agent.task.SerialTask;
import pcd.ass01ridesign.monitor.environment.Environment;
import pcd.ass01ridesign.monitor.environment.Road;

import java.util.Random;

public abstract class AbstractCarAgent implements CarAgent {

    private final String agentID;
    private final Environment environment;
    protected final double maxSpeed;
    protected double currentSpeed;
    protected double acceleration;
    protected double deceleration;
    protected final double stepSize;
    protected static final int CAR_NEAR_DIST = 15;
    protected static final int CAR_FAR_ENOUGH_DIST = 20;
    protected static final int MAX_WAITING_TIME = 2;
    protected int waitingTime;
    protected Percept currentPerception;
    protected Action selectedAction;

    public AbstractCarAgent(String agentID, Environment environment, Road road,
                            double initialPosition,
                            double acceleration,
                            double deceleration,
                            double maxSpeed,
                            double stepSize) {
        this.agentID = agentID;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.maxSpeed = maxSpeed;
        this.environment = environment;
        this.stepSize = stepSize;
        this.environment.registerNewCar(this, road, initialPosition);
    }

    public AbstractCarAgent(String agentID, Environment environment, Road road, double initialPosition, int seed) {
        this.agentID = agentID;
        this.environment = environment;
        Random gen = new Random(seed);
        this.environment.registerNewCar(this, road, initialPosition);
        this.acceleration = 1 + gen.nextDouble(0,1);
        this.deceleration = 0.3 + gen.nextDouble(0,1);
        this.maxSpeed = 4 + gen.nextDouble(1,10);
        this.stepSize = 1;
    }

    protected abstract void decide();

    private void senseAndDecide() {
        this.sense();
        this.decide();
    }

    private void sense() {
        this.currentPerception = this.environment.getCurrentPercept(this.getAgentID());
    }

    private void doAction() {
        if (this.selectedAction != null) {
            this.environment.doAction(this.getAgentID(), selectedAction);
        }
    }

    //TODO parallel action needs a dt for decide, now is passed in constructor
    @Override
    public synchronized ParallelTask getParallelAction() {
        return this::senseAndDecide;
    }

    @Override
    public synchronized SerialTask getSerialAction() {
        return this::doAction;
    }

    @Override
    public synchronized String getAgentID() {
        return this.agentID;
    }

    @Override
    public synchronized Environment getAgentEnvironment() {
        return this.environment;
    }

    @Override
    public synchronized double getCurrentSpeed() {
        return this.currentSpeed;
    }
}
