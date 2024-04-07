package logic.passiveComponent.agent.perception;

import logic.passiveComponent.agent.AbstractCarAgent;
import logic.passiveComponent.environment.trafficLight.TrafficLight;

import java.util.Optional;

public interface Perception {

    double getRoadPosition();

    Optional<TrafficLight> getNearestSem();

    Optional<AbstractCarAgent> getNearestCarInFront();

}
