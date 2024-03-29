package pcd.ass01ridesign.passiveComponent.simulation.examples;


import pcd.ass01ridesign.passiveComponent.agent.AbstractCarAgent;
import pcd.ass01ridesign.passiveComponent.agent.agentImpl.base.BaseCarAgent;
import pcd.ass01ridesign.passiveComponent.environment.Environment;
import pcd.ass01ridesign.passiveComponent.environment.road.Road;
import pcd.ass01ridesign.passiveComponent.environment.RoadsEnvironment;
import pcd.ass01ridesign.passiveComponent.simulation.AbstractSimulationSingleBarrier;
import pcd.ass01ridesign.utils.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrafficSimulationSingleRoadMassiveNumberOfCars extends AbstractSimulationSingleBarrier {

	private int numCars;
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

			AbstractCarAgent car = new BaseCarAgent(carId, environment,
					road,
					initialPos,
					carAcceleration,
					carDeceleration,
					carMaxSpeed);

			if (seed.isPresent()) {
				car = new BaseCarAgent(carId, environment, road, initialPos, seed.get());
			}

			agents.add(car);
		}
		return agents;
	}

	@Override
	protected Environment createEnvironment() {
		return new RoadsEnvironment();
	}

	@Override
	protected int setDelta() {
		return 1;
	}

	@Override
	protected int setInitialCondition() {
		return 0;
	}
}
	