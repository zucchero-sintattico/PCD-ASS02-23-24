package model.passiveComponent.environment;

import model.passiveComponent.agent.AbstractCarAgent;
import model.passiveComponent.agent.action.Action;
import model.passiveComponent.agent.perception.Perception;
import model.passiveComponent.environment.road.Road;
import model.passiveComponent.environment.trafficLight.TrafficLight;
import model.passiveComponent.simulation.SimulationComponent;
import utils.Point2D;

import java.util.List;

public interface Environment extends SimulationComponent {

    void step();

    void registerNewCarAgent(AbstractCarAgent abstractCarAgent);

    List<AbstractCarAgent> getCarAgents();

    Road createRoad(Point2D p0, Point2D p1);

    List<Road> getRoads();

    List<TrafficLight> getTrafficLights();

    Perception getCurrentPerception(String agentID);

    void doAction(String agentID, Action selectedAction);

}
