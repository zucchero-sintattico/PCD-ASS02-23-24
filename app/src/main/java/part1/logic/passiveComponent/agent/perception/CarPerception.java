package part1.logic.passiveComponent.agent.perception;

import part1.logic.passiveComponent.agent.AbstractCarAgent;
import part1.logic.passiveComponent.environment.trafficLight.TrafficLight;
import java.util.Optional;

public class CarPerception implements Perception {

	private final double roadPosition;
	private final TrafficLight nearestTrafficLight;
	private final AbstractCarAgent nearestCarInFront;

	public CarPerception(double roadPosition, AbstractCarAgent nearestCarInFront, TrafficLight nearestTrafficLight) {
		this.roadPosition = roadPosition;
		this.nearestCarInFront = nearestCarInFront;
		this.nearestTrafficLight = nearestTrafficLight;
	}

	@Override
	public double getRoadPosition() {
		return roadPosition;
	}

	@Override
	public Optional<TrafficLight> getNearestSem() {
		return nearestTrafficLight == null ? Optional.empty() : Optional.of(nearestTrafficLight);
	}

	@Override
	public Optional<AbstractCarAgent> getNearestCarInFront() {
		return nearestCarInFront == null ? Optional.empty() : Optional.of(nearestCarInFront);
	}

}
