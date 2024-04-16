package part1.logic.simulation.examples;

import part1.logic.passiveComponent.agent.AbstractCarAgent;
import part1.logic.passiveComponent.agent.agentImpl.base.BaseCarAgent;
import part1.logic.passiveComponent.environment.road.Road;
import part1.utils.Point2D;
import java.util.ArrayList;
import java.util.List;

public class TrafficSimulationSingleRoadSeveralCars extends AbstractTrafficSimulationExample {

	@Override
	protected List<AbstractCarAgent> createAgents() {
		Road road = environment.createRoad(new Point2D(0, 300), new Point2D(1500, 300));
		List<AbstractCarAgent> agents = new ArrayList<>();
		int nCars = 30;
		for (int i = 0; i < nCars; i++) {
			String carId = "car-" + i;
			double initialPos = i * 10;
			double carAcceleration = 1;
			double carDeceleration = 0.3;
			double carMaxSpeed = 7;
			AbstractCarAgent car = new BaseCarAgent(carId, environment,
					road,
					initialPos,
					carAcceleration,
					carDeceleration,
					carMaxSpeed);
			agents.add(car);
		}
		/* sync with wall-time: 25 steps per sec */
		this.syncWithTime(25);
		return agents;
	}

}
	