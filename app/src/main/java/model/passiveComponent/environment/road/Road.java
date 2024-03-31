package model.passiveComponent.environment.road;



import model.passiveComponent.environment.trafficLight.TrafficLight;
import utils.Point2D;

import java.util.List;

public interface Road {

    Point2D getFrom();

    Point2D getTo();

    void addTrafficLight(TrafficLight sem);

    List<TrafficLight> getTrafficLights();

    double getLen();
}
