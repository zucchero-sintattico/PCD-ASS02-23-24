package pcd.ass01ridesign.monitor.environment;

import pcd.ass01ridesign.monitor.agent.AbstractCarAgent;
import pcd.ass01ridesign.monitor.agent.action.Action;
import pcd.ass01ridesign.monitor.agent.perception.Percept;
import pcd.ass01ridesign.passiveComponent.utils.Point2D;

public interface Environment {

    //todo refactor
    //todo missing id
    void registerNewCar(AbstractCarAgent abstractAgent, Road road, double initialPos);

    Road createRoad(Point2D p0, Point2D p1);

    TrafficLight createTrafficLight(Point2D pos, TrafficLightState initialState, int greenDuration, int yellowDuration, int redDuration);

    Percept getCurrentPercept(String agentID);

    void doAction(String agentID, Action selectedAction);

    void step();
}
