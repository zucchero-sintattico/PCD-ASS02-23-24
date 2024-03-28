package pcd.ass01ridesign.monitor.agent.perception;



import pcd.ass01ridesign.monitor.agent.CarAgentInfo;
import pcd.ass01ridesign.monitor.environment.TrafficLightInfo;

import java.util.Optional;

public interface Percept {
    double getRoadPos();

    Optional<TrafficLightInfo> getNearestSem();

    Optional<CarAgentInfo> getNearestCarInFront();
}
