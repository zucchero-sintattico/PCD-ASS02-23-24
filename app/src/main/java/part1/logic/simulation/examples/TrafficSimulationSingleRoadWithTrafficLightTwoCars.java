package part1.logic.simulation.examples;

import part1.logic.passiveComponent.agent.AbstractCarAgent;
import part1.logic.passiveComponent.agent.agentImpl.extended.ExtendedCarAgent;
import part1.logic.passiveComponent.environment.road.Road;
import part1.logic.passiveComponent.environment.trafficLight.TrafficLightState;
import part1.utils.Point2D;
import java.util.ArrayList;
import java.util.List;

public class TrafficSimulationSingleRoadWithTrafficLightTwoCars extends AbstractTrafficSimulationExample {

	@Override
	protected List<AbstractCarAgent> createAgents() {
		Road r = environment.createRoad(new Point2D(0, 300), new Point2D(1500, 300));
		List<AbstractCarAgent> agents = new ArrayList<>();
		r.addTrafficLight(new Point2D(740, 300), TrafficLightState.GREEN, 75, 25, 100, 740);
		AbstractCarAgent car1 = new ExtendedCarAgent("car-1", environment, r, 0, 0.1, 0.3, 6);
		agents.add(car1);
		AbstractCarAgent car2 = new ExtendedCarAgent("car-2", environment, r, 100, 0.1, 0.3, 5);
		agents.add(car2);
		/* sync with wall-time: 25 steps per sec */
		this.syncWithTime(25);
		return agents;
	}

}
