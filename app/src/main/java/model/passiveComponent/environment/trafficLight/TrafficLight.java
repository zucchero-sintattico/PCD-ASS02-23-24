package model.passiveComponent.environment.trafficLight;

import model.passiveComponent.environment.road.Road;
import utils.Point2D;

public interface TrafficLight {

    boolean isGreen();

    void step(int simulationStep);

    void init();

    boolean isRed();

    boolean isYellow();

    Point2D getPos();

    void setRoad(Road r);

    Road getRoad();

    void setPos(double roadPos);

    double getRoadPos();
}
