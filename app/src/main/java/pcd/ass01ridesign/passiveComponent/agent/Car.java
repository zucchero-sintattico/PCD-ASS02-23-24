package pcd.ass01ridesign.passiveComponent.agent;


import pcd.ass01ridesign.passiveComponent.environment.road.Road;

public interface Car {

    String getAgentID();

    double getCurrentSpeed();

    double getPosition();

    void updatePosition(double position);

    Road getRoad();

}
