package pcd.ass01ridesign.monitor.environment;

import pcd.ass01ridesign.passiveComponent.utils.Point2D;

public interface TrafficLight {
    boolean isGreen();

    void step(int simulationStep);

    void init();

    boolean isRed();

    boolean isYellow();

    Point2D getPos();
}
