package pcd.ass01.passiveComponent.environment.road;



import pcd.ass01.passiveComponent.environment.trafficLight.TrafficLight;
import pcd.ass01.passiveComponent.environment.trafficLight.TrafficLightInfo;
import pcd.ass01.utils.Point2D;

import java.util.List;

public interface Road {

    Point2D getFrom();

    Point2D getTo();

    void addTrafficLight(TrafficLight sem, double pos);

    List<TrafficLightInfo> getTrafficLights();

    double getLen();
}
