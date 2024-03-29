package pcd.ass01ridesign.passiveComponent.agent.perception;



import pcd.ass01ridesign.passiveComponent.agent.AbstractCarAgent;
import pcd.ass01ridesign.passiveComponent.environment.trafficLight.TrafficLightInfo;

import java.util.Optional;

public interface Perception {
    double getRoadPos();

    Optional<TrafficLightInfo> getNearestSem();

    Optional<AbstractCarAgent> getNearestCarInFront();
}
