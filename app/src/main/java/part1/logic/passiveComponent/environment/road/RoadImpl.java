package part1.logic.passiveComponent.environment.road;

import part1.logic.passiveComponent.environment.trafficLight.TrafficLight;
import part1.logic.passiveComponent.environment.trafficLight.TrafficLightImpl;
import part1.logic.passiveComponent.environment.trafficLight.TrafficLightState;
import part1.utils.Point2D;
import java.util.ArrayList;
import java.util.List;

public class RoadImpl implements Road {

    private final double length;
    private final Point2D startPoint;
    private final Point2D endPoint;
    private final List<TrafficLight> trafficLights = new ArrayList<>();

    public RoadImpl(Point2D p0, Point2D p1) {
        this.startPoint = p0;
        this.endPoint = p1;
        this.length = Point2D.len(startPoint, endPoint);
    }

    @Override
    public Point2D getStartPoint() {
        return startPoint;
    }

    @Override
    public Point2D getEndPoint() {
        return endPoint;
    }

    @Override
    public double getLength() {
        return length;
    }

    @Override
    public void addTrafficLight(Point2D position, TrafficLightState initialState, int greenDuration, int yellowDuration, int redDuration, double roadPosition) {
        this.trafficLights.add(new TrafficLightImpl(position, initialState, greenDuration, yellowDuration, redDuration, roadPosition));
    }

    @Override
    public List<TrafficLight> getTrafficLights() {
        return trafficLights;
    }

}
