package pcd.ass01ridesign.monitor.agent.perception;

import pcd.ass01ridesign.monitor.agent.CarAgentInfo;
import pcd.ass01ridesign.monitor.environment.TrafficLightInfo;

import java.util.Optional;

public class CarPerception implements Percept{
    private final double roadPos;
    private final TrafficLightInfo nearestSem;
    private final CarAgentInfo nearestCarInFront;

    public CarPerception(double roadPos, CarAgentInfo nearestCarInFront, TrafficLightInfo nearestSem){
        this.roadPos = roadPos;
        this.nearestCarInFront = nearestCarInFront;
        this.nearestSem = nearestSem;
    }

    @Override
    public double getRoadPos() {
        return roadPos;
    }

    @Override
    public Optional<TrafficLightInfo> getNearestSem() {
        return nearestSem == null ? Optional.empty() : Optional.of(nearestSem);
    }

    @Override
    public Optional<CarAgentInfo> getNearestCarInFront() {
        return nearestCarInFront == null ? Optional.empty() : Optional.of(nearestCarInFront);
    }
}
