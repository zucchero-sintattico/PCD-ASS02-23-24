package logic.simulation.examples;

import logic.passiveComponent.agent.AbstractCarAgent;
import logic.passiveComponent.agent.agentImpl.base.BaseCarAgent;
import logic.passiveComponent.environment.road.Road;
import utils.Point2D;
import java.util.ArrayList;
import java.util.List;

public class TrafficSimulationSingleRoadTwoCars extends AbstractTrafficSimulationExample {

	@Override
	protected List<AbstractCarAgent> createAgents() {
		Road r = environment.createRoad(new Point2D(0, 300), new Point2D(1500, 300));
		List<AbstractCarAgent> agents = new ArrayList<>();
		AbstractCarAgent car1 = new BaseCarAgent("car-1", environment, r, 0, 0.1, 0.2, 8);
		agents.add(car1);
		AbstractCarAgent car2 = new BaseCarAgent("car-2", environment, r, 100, 0.1, 0.1, 7);
		agents.add(car2);
		/* sync with wall-time: 25 steps per sec */
		this.syncWithTime(25);
		return agents;
	}

}
