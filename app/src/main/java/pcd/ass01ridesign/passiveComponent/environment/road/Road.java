package pcd.ass01ridesign.passiveComponent.environment.road;



import pcd.ass01ridesign.passiveComponent.environment.trafficLight.TrafficLight;
import pcd.ass01ridesign.passiveComponent.environment.trafficLight.TrafficLightInfo;
import pcd.ass01ridesign.passiveComponent.utils.Point2D;

import java.util.List;

public interface Road {

    Point2D getFrom();

    Point2D getTo();

    void addTrafficLight(TrafficLight sem, double pos);

    List<TrafficLightInfo> getTrafficLights();

    double getLen();
}
