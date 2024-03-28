package pcd.ass01ridesign.monitor.environment;



import pcd.ass01ridesign.passiveComponent.utils.Point2D;

import java.util.List;
import java.util.Optional;

public interface Road {

    Point2D getFrom();

    Point2D getTo();

    void addTrafficLight(TrafficLight sem, double pos);

    List<TrafficLightInfo> getTrafficLights();

    double getLen();
}
