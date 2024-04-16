package part1;

import part1.logic.activeComponent.SimulationRunner;
import part1.logic.simulation.listeners.RoadSimStatistics;
import part1.view.RoadSimView;
import part1.logic.simulation.examples.TrafficSimulationWithCrossRoads;

public class RunTrafficSimulation {

	public static void main(String[] args) throws InterruptedException {
		// var simulation = new TrafficSimulationSingleRoadTwoCars();
		// var simulation = new TrafficSimulationSingleRoadSeveralCars();
		// var simulation = new TrafficSimulationSingleRoadWithTrafficLightTwoCars();
		var simulation = new TrafficSimulationWithCrossRoads();
		simulation.setup(500, 1);
		
		RoadSimStatistics stat = new RoadSimStatistics();
		RoadSimView view = new RoadSimView();
		view.display();
		
		simulation.addSimulationListener(stat);
		simulation.addSimulationListener(view);
		Thread t = new SimulationRunner(simulation);
		t.start();
		t.join();
	}
}
