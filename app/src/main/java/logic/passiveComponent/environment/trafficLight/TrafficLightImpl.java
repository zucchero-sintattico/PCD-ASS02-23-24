package logic.passiveComponent.environment.trafficLight;

import utils.Point2D;

public class TrafficLightImpl implements TrafficLight {

	private TrafficLightState state;
	private int currentTimeInState;
	private final int redDuration;
	private final int greenDuration;
	private final int yellowDuration;
	private final Point2D position;
	private final double roadPosition;

	public TrafficLightImpl(Point2D position, TrafficLightState initialState, int greenDuration, int yellowDuration, int redDuration, double roadPosition) {
		this.redDuration = redDuration;
		this.greenDuration = greenDuration;
		this.yellowDuration = yellowDuration;
		this.position = position;
		this.state = initialState;
		this.currentTimeInState = 0;
		this.roadPosition = roadPosition;
	}

	@Override
	public void step(int simulationStep) {
		switch (state) {
			case GREEN:
				currentTimeInState += simulationStep;
				if (currentTimeInState >= greenDuration) {
					state = TrafficLightState.YELLOW;
					currentTimeInState = 0;
				}
				break;
			case RED:
				currentTimeInState += simulationStep;
				if (currentTimeInState >= redDuration) {
					state = TrafficLightState.GREEN;
					currentTimeInState = 0;
				}
				break;
			case YELLOW:
				currentTimeInState += simulationStep;
				if (currentTimeInState >= yellowDuration) {
					state = TrafficLightState.RED;
					currentTimeInState = 0;
				}
				break;
			default:
				break;


		}
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public double getRoadPosition() {
		return roadPosition;
	}

	@Override
	public boolean isGreen() {
		return state.equals(TrafficLightState.GREEN);
	}

	@Override
	public boolean isRed() {
		return state.equals(TrafficLightState.RED);
	}

	@Override
	public boolean isYellow() {
		return state.equals(TrafficLightState.YELLOW);
	}


}
