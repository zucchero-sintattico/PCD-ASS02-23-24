package logic.passiveComponent.simulation;

import logic.passiveComponent.simulation.examples.TrafficSimulationSingleRoadMassiveNumberOfCars;
import logic.passiveComponent.simulation.examples.TrafficSimulationSingleRoadSeveralCars;
import logic.passiveComponent.simulation.examples.TrafficSimulationSingleRoadTwoCars;
import logic.passiveComponent.simulation.examples.TrafficSimulationSingleRoadWithTrafficLightTwoCars;
import logic.passiveComponent.simulation.examples.TrafficSimulationWithCrossRoads;

public enum SimulationType {
	SINGLE_ROAD_TWO_CAR,
	SINGLE_ROAD_SEVERAL_CARS,
	SINGLE_ROAD_WITH_TRAFFIC_TWO_CAR,
	CROSS_ROADS,
	MASSIVE_SIMULATION;

	public Simulation getSimulation(SimulationType type) {
		if (type.equals(SINGLE_ROAD_TWO_CAR)) {
			return new TrafficSimulationSingleRoadTwoCars();
		} else if (type.equals(SINGLE_ROAD_SEVERAL_CARS)) {
			return new TrafficSimulationSingleRoadSeveralCars();
		} else if (type.equals(SINGLE_ROAD_WITH_TRAFFIC_TWO_CAR)) {
			return new TrafficSimulationSingleRoadWithTrafficLightTwoCars();
		} else if (type.equals(CROSS_ROADS)) {
			return new TrafficSimulationWithCrossRoads();
		} else {
			return new TrafficSimulationSingleRoadMassiveNumberOfCars(500);
		}
	}
}
