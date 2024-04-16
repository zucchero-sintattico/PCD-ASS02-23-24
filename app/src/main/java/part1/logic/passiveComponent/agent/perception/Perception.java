package part1.logic.passiveComponent.agent.perception;

import part1.logic.passiveComponent.agent.AbstractCarAgent;
import part1.logic.passiveComponent.environment.trafficLight.TrafficLight;

import java.util.Optional;

public interface Perception {

    double getRoadPosition();

    Optional<TrafficLight> getNearestSem();

    Optional<AbstractCarAgent> getNearestCarInFront();

}
