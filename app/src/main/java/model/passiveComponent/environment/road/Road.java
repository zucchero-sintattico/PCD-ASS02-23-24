package model.passiveComponent.environment.road;



import model.passiveComponent.environment.trafficLight.TrafficLight;
import model.passiveComponent.environment.trafficLight.TrafficLightState;
import utils.Point2D;

import java.util.List;

public interface Road {

    Point2D getStartPoint();

    Point2D getEndPoint();

    void addTrafficLight(Point2D position, TrafficLightState initialState, int greenDuration, int yellowDuration, int redDuration, double roadPosition);

    List<TrafficLight> getTrafficLights();

    double getLength();

}
