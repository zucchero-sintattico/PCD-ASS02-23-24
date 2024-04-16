package part1.logic.passiveComponent.environment.road;

import part1.logic.passiveComponent.environment.trafficLight.TrafficLight;
import part1.logic.passiveComponent.environment.trafficLight.TrafficLightState;
import part1.utils.Point2D;
import java.util.List;

public interface Road {

    Point2D getStartPoint();

    Point2D getEndPoint();

    void addTrafficLight(Point2D position, TrafficLightState initialState, int greenDuration, int yellowDuration, int redDuration, double roadPosition);

    List<TrafficLight> getTrafficLights();

    double getLength();

}
