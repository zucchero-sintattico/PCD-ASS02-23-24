package model.passiveComponent.agent.agentImpl.extended;

import model.passiveComponent.agent.AbstractCarAgent;
import model.passiveComponent.agent.action.MoveForward;
import model.passiveComponent.environment.Environment;
import model.passiveComponent.environment.road.Road;
import model.passiveComponent.environment.trafficLight.TrafficLight;

import java.util.Optional;

public class ExtendedCarAgent extends AbstractCarAgent {

	protected static final int SEM_NEAR_DIST = 100;
	private ExtendedCarAgentState state = ExtendedCarAgentState.STOPPED;

	public ExtendedCarAgent(String agentID, Environment environment, Road road, double initialPosition, double acceleration, double deceleration, double maxSpeed) {
		super(agentID, environment, road, initialPosition, acceleration, deceleration, maxSpeed);
	}

	public ExtendedCarAgent(String agentID, Environment environment, Road road, double initialPosition, int seed) {
		super(agentID, environment, road, initialPosition, seed);
	}

	private boolean detectedNearCar() {
		Optional<AbstractCarAgent> car = currentPerception.getNearestCarInFront();
		if (car.isEmpty()) {
//			System.out.println("aID "+getAgentID()+" detectnearcarempty");
			return false;
		} else {
			double dist = car.get().getPosition() - currentPerception.getRoadPosition();
//			System.out.println("aID "+getAgentID()+" detectnearcarNOTempty "+car.get().getPosition());
//			System.out.println("aID "+getAgentID()+" detectnearcarNOTempty "+currentPerception.getRoadPosition());
//			System.out.println("aID "+getAgentID()+" detectnearcarNOTempty "+(dist < CAR_NEAR_DIST));
			return dist < CAR_NEAR_DIST;
		}
	}

	private boolean detectedRedOrYellowNearTrafficLights() {
		Optional<TrafficLight> sem = currentPerception.getNearestSem();
		if (sem.isEmpty() || sem.get().isGreen()) {
			return false;
		} else {
			double dist = sem.get().getRoadPosition() - currentPerception.getRoadPosition();
			return dist > 0 && dist < SEM_NEAR_DIST;
		}
	}

	private boolean detectedGreenTrafficLights() {
		Optional<TrafficLight> sem = currentPerception.getNearestSem();
		return (sem.isPresent() && sem.get().isGreen());
	}

	private boolean carFarEnough() {
		Optional<AbstractCarAgent> car = currentPerception.getNearestCarInFront();
		if (car.isEmpty()) {
			return true;
		} else {
			double dist = car.get().getPosition() - currentPerception.getRoadPosition();
			return dist > CAR_FAR_ENOUGH_DIST;
		}
	}

	@Override
	public void decide() {
		switch (state) {
			case ExtendedCarAgentState.STOPPED:
				if (!detectedNearCar()) {
					state = ExtendedCarAgentState.ACCELERATING;
				}
				break;
			case ExtendedCarAgentState.ACCELERATING:
				if (detectedNearCar()) {
					state = ExtendedCarAgentState.DECELERATING_BECAUSE_OF_A_CAR;
				} else if (detectedRedOrYellowNearTrafficLights()) {
					state = ExtendedCarAgentState.DECELERATING_BECAUSE_OF_A_NOT_GREEN_SEM;
				} else {
					this.currentSpeed += acceleration * stepSize;
					if (currentSpeed >= maxSpeed) {
						state = ExtendedCarAgentState.MOVING_CONSTANT_SPEED;
					}
				}
				break;
			case ExtendedCarAgentState.MOVING_CONSTANT_SPEED:
				if (detectedNearCar()) {
					state = ExtendedCarAgentState.DECELERATING_BECAUSE_OF_A_CAR;
				} else if (detectedRedOrYellowNearTrafficLights()) {
					state = ExtendedCarAgentState.DECELERATING_BECAUSE_OF_A_NOT_GREEN_SEM;
				}
				break;
			case ExtendedCarAgentState.DECELERATING_BECAUSE_OF_A_CAR:
				this.currentSpeed -= deceleration * stepSize;
				if (this.currentSpeed <= 0) {
					state =  ExtendedCarAgentState.STOPPED;
				} else if (this.carFarEnough()) {
					state = ExtendedCarAgentState.WAIT_A_BIT;
					waitingTime = 0;
				}
				break;
			case ExtendedCarAgentState.DECELERATING_BECAUSE_OF_A_NOT_GREEN_SEM:
				this.currentSpeed -= deceleration * stepSize;
				if (this.currentSpeed <= 0) {
					state =  ExtendedCarAgentState.WAITING_FOR_GREEN_SEM;
				} else if (!detectedRedOrYellowNearTrafficLights()) {
					state = ExtendedCarAgentState.ACCELERATING;
				}
				break;
			case ExtendedCarAgentState.WAIT_A_BIT:
				waitingTime += stepSize;
				if (waitingTime > MAX_WAITING_TIME) {
					state = ExtendedCarAgentState.ACCELERATING;
				}
				break;
			case ExtendedCarAgentState.WAITING_FOR_GREEN_SEM:
				if (detectedGreenTrafficLights()) {
					state = ExtendedCarAgentState.ACCELERATING;
				}
				break;
		}
//		System.out.println("aID "+getAgentID()+" "+detectedRedOrYellowNearTrafficLights());
//		System.out.println("aID "+getAgentID()+" "+detectedGreenTrafficLights());
//		System.out.println("aID "+getAgentID()+" "+detectedNearCar());
//		System.out.println("aID "+getAgentID()+" preactionPresent "+(this.selectedAction != null));
		if (currentSpeed > 0) {
//			System.out.println("aID "+getAgentID()+" createAction");
			selectedAction = new MoveForward(currentSpeed * stepSize);
		}
//		System.out.println("aID "+getAgentID()+" postactionPresent "+(this.selectedAction != null));
//		System.out.println("aID "+getAgentID()+" "+state);


	}


}
