package logic.passiveComponent.environment;

import logic.passiveComponent.simulation.SimulationComponent;
import logic.passiveComponent.agent.AbstractCarAgent;
import logic.passiveComponent.agent.action.Action;
import logic.passiveComponent.agent.perception.Perception;
import logic.passiveComponent.environment.road.Road;
import logic.passiveComponent.environment.trafficLight.TrafficLight;
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
