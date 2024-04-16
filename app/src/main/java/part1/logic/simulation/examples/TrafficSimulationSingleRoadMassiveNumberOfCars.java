package part1.logic.simulation.examples;

import part1.logic.passiveComponent.agent.AbstractCarAgent;
import part1.logic.passiveComponent.agent.agentImpl.base.BaseCarAgent;
import part1.logic.passiveComponent.environment.road.Road;
import part1.utils.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrafficSimulationSingleRoadMassiveNumberOfCars extends AbstractTrafficSimulationExample {

	private final int numCars;
	private Optional<Integer> seed = Optional.empty();

	public TrafficSimulationSingleRoadMassiveNumberOfCars(int numCars) {
		super();
		this.numCars = numCars;
	}

	public TrafficSimulationSingleRoadMassiveNumberOfCars(int numCars, int seed) {
		super();
		this.numCars = numCars;
		this.seed = Optional.of(seed);
	}

	@Override
	protected List<AbstractCarAgent> createAgents() {
		Road road = environment.createRoad(new Point2D(0, 300), new Point2D(15000, 300));
		List<AbstractCarAgent> agents = new ArrayList<>();
		for (int i = 0; i < numCars; i++) {
			String carId = "car-" + i;
			double initialPos = i * 10;
			double carAcceleration = 1;
			double carDeceleration = 0.3;
			double carMaxSpeed = 7;
			AbstractCarAgent car;
			if (seed.isPresent()) {
				car = new BaseCarAgent(carId, environment, road, initialPos, seed.get()+i);
			}else{
				car = new BaseCarAgent(carId, environment,
					road,
					initialPos,
					carAcceleration,
					carDeceleration,
					carMaxSpeed);
			}
			agents.add(car);
		}
		return agents;
	}

}
	