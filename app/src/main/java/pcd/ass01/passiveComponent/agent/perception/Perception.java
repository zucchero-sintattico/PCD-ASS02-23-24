package pcd.ass01.passiveComponent.agent.perception;



import pcd.ass01.passiveComponent.agent.AbstractCarAgent;
import pcd.ass01.passiveComponent.environment.trafficLight.TrafficLightInfo;

import java.util.Optional;

public interface Perception {
    double getRoadPos();

    Optional<TrafficLightInfo> getNearestSem();

    Optional<AbstractCarAgent> getNearestCarInFront();
}
