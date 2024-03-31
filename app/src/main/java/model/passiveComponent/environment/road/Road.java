package model.passiveComponent.environment.road;



import model.passiveComponent.environment.trafficLight.TrafficLight;
import model.passiveComponent.environment.trafficLight.TrafficLightState;
import utils.Point2D;

import java.util.List;

public interface Road {

    Point2D getFrom();

    Point2D getTo();

    void addTrafficLight(Point2D pos, TrafficLightState initialState, int greenDuration, int yellowDuration, int redDuration, double roadPos);

    List<TrafficLight> getTrafficLights();

    double getLen();
}
