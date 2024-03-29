package pcd.ass01ridesign.passiveComponent.environment.trafficLight;

import pcd.ass01ridesign.utils.Point2D;

public interface TrafficLight {

    boolean isGreen();

    void step(int simulationStep);

    void init();

    boolean isRed();

    boolean isYellow();

    Point2D getPos();
}
