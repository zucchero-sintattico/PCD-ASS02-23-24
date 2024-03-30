package pcd.ass01.passiveComponent.simulation.examples;



import pcd.ass01.passiveComponent.agent.AbstractCarAgent;
import pcd.ass01.passiveComponent.agent.agentImpl.base.BaseCarAgent;
import pcd.ass01.passiveComponent.environment.Environment;
import pcd.ass01.passiveComponent.environment.RoadsEnvironment;
import pcd.ass01.passiveComponent.environment.road.Road;
import pcd.ass01.utils.Point2D;
import pcd.ass01.passiveComponent.simulation.AbstractSimulation;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Traffic Simulation about a number of cars 
 * moving on a single road, no traffic lights
 * 
 */
public class TrafficSimulationSingleRoadSeveralCars extends AbstractSimulation {

//	public TrafficSimulationSingleRoadSeveralCars() {
//		super();
//	}

	@Override
	protected List<AbstractCarAgent> createAgents() {
		Road road = environment.createRoad(new Point2D(0,300), new Point2D(1500,300));
		List<AbstractCarAgent> agents = new ArrayList<>();
		int nCars = 30;

		for (int i = 0; i < nCars; i++) {

			String carId = "car-" + i;
			// double initialPos = i*30;
			double initialPos = i*10;

			double carAcceleration = 1; //  + gen.nextDouble()/2;
			double carDeceleration = 0.3; //  + gen.nextDouble()/2;
			double carMaxSpeed = 7; // 4 + gen.nextDouble();

			AbstractCarAgent car = new BaseCarAgent(carId, environment,
					road,
					initialPos,
					carAcceleration,
					carDeceleration,
					carMaxSpeed);
			agents.add(car);
		}

		this.syncWithTime(25);
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
	