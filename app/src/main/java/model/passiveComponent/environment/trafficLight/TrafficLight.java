package model.passiveComponent.environment.trafficLight;

import model.passiveComponent.environment.road.Road;
import utils.Point2D;

public interface TrafficLight {

    boolean isGreen();

    void step(int simulationStep);


    boolean isRed();

    boolean isYellow();

    Point2D getPos();





    double getRoadPos();
}
