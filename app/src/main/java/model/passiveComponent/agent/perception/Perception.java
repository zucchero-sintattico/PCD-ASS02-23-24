package model.passiveComponent.agent.perception;



import model.passiveComponent.agent.AbstractCarAgent;
import model.passiveComponent.environment.trafficLight.TrafficLight;

import java.util.Optional;

public interface Perception {

    double getRoadPosition();

    Optional<TrafficLight> getNearestSem();

    Optional<AbstractCarAgent> getNearestCarInFront();

}
