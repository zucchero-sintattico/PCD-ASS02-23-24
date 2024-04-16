package part1.logic.passiveComponent.environment;

import part1.logic.simulation.SimulationComponent;
import part1.logic.passiveComponent.agent.AbstractCarAgent;
import part1.logic.passiveComponent.agent.action.Action;
import part1.logic.passiveComponent.agent.perception.Perception;
import part1.logic.passiveComponent.environment.road.Road;
import part1.logic.passiveComponent.environment.trafficLight.TrafficLight;
import part1.utils.Point2D;
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
