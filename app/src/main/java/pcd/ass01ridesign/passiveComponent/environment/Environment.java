package pcd.ass01ridesign.passiveComponent.environment;

import pcd.ass01ridesign.passiveComponent.agent.AbstractCarAgent;
import pcd.ass01ridesign.passiveComponent.agent.action.Action;
import pcd.ass01ridesign.passiveComponent.agent.perception.Perception;
import pcd.ass01ridesign.passiveComponent.environment.road.Road;
import pcd.ass01ridesign.passiveComponent.environment.trafficLight.TrafficLight;
import pcd.ass01ridesign.passiveComponent.environment.trafficLight.TrafficLightState;
import pcd.ass01ridesign.utils.Point2D;
import pcd.ass01ridesign.simulation.SimulationComponent;

public interface Environment extends SimulationComponent {

    //todo refactor
    //todo missing id
    void registerNewCar(AbstractCarAgent abstractCarAgent);

    Road createRoad(Point2D p0, Point2D p1);

    TrafficLight createTrafficLight(Point2D pos, TrafficLightState initialState, int greenDuration, int yellowDuration, int redDuration);

    Perception getCurrentPercept(String agentID);

    void doAction(String agentID, Action selectedAction);

    void step();
}
