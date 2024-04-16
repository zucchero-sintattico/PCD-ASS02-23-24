package part1.logic.simulation.examples;

import part1.logic.passiveComponent.agent.AbstractCarAgent;
import part1.logic.passiveComponent.agent.agentImpl.extended.ExtendedCarAgent;
import part1.logic.passiveComponent.environment.road.Road;
import part1.logic.passiveComponent.environment.trafficLight.TrafficLightState;
import part1.utils.Point2D;
import java.util.ArrayList;
import java.util.List;

public class TrafficSimulationWithCrossRoads extends AbstractTrafficSimulationExample {

	@Override
	protected List<AbstractCarAgent> createAgents() {
		Road r1 = environment.createRoad(new Point2D(0, 300), new Point2D(1500, 300));
		r1.addTrafficLight(new Point2D(740, 300), TrafficLightState.GREEN, 75, 25, 100, 740);
		List<AbstractCarAgent> agents = new ArrayList<>();
		AbstractCarAgent car1 = new ExtendedCarAgent("car-1", environment, r1, 0, 0.1, 0.3, 6);
		agents.add(car1);
		AbstractCarAgent car2 = new ExtendedCarAgent("car-2", environment, r1, 100, 0.1, 0.3, 5);
		agents.add(car2);
		Road r2 = environment.createRoad(new Point2D(750, 0), new Point2D(750, 600));
		r2.addTrafficLight(new Point2D(750, 290), TrafficLightState.RED, 75, 25, 100, 290);
		AbstractCarAgent car3 = new ExtendedCarAgent("car-3", environment, r2, 0, 0.1, 0.2, 5);
		agents.add(car3);
		AbstractCarAgent car4 = new ExtendedCarAgent("car-4", environment, r2, 100, 0.1, 0.1, 4);
		agents.add(car4);
		/* sync with wall-time: 25 steps per sec */
		this.syncWithTime(25);
		return agents;
	}

}
