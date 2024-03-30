package pcd.ass01.passiveComponent.environment.trafficLight;

import pcd.ass01.utils.Point2D;

public interface TrafficLight {

    boolean isGreen();

    void step(int simulationStep);

    void init();

    boolean isRed();

    boolean isYellow();

    Point2D getPos();
}
