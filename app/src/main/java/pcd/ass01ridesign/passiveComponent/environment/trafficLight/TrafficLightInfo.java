package pcd.ass01ridesign.passiveComponent.environment.trafficLight;


import pcd.ass01ridesign.passiveComponent.environment.road.Road;

public class TrafficLightInfo {
    private final TrafficLight sem;
    private final Road road;
    private final double roadPos;

    public TrafficLightInfo(TrafficLight sem, Road road, double roadPos) {
        this.sem = sem;
        this.road = road;
        this.roadPos = roadPos;
    }

    public TrafficLight getSem() {
        return sem;
    }

    public Road getRoad() {
        return road;
    }

    public double getRoadPos() {
        return roadPos;
    }


}
