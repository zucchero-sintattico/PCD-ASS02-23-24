package pcd.ass01.passiveComponent.agent;


import pcd.ass01.passiveComponent.environment.road.Road;

public interface Car {

    String getAgentID();

    double getCurrentSpeed();

    double getPosition();

    void updatePosition(double position);

    Road getRoad();

}
