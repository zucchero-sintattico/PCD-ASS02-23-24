package pcd.ass01;


import pcd.ass01.activeComponent.SimulationRunner;
import pcd.ass01.passiveComponent.simulation.listeners.RoadSimStatistics;
import pcd.ass01.passiveComponent.simulation.listeners.RoadSimView;
import pcd.ass01.passiveComponent.simulation.examples.TrafficSimulationWithCrossRoads;

/**
 * 
 * Main class to create and run a simulation
 * 
 */
public class RunTrafficSimulation {

	public static void main(String[] args) throws InterruptedException {
		// var simulation = new TrafficSimulationSingleRoadTwoCars();
		// var simulation = new TrafficSimulationSingleRoadSeveralCars();
		// var simulation = new TrafficSimulationSingleRoadWithTrafficLightTwoCars();
		var simulation = new TrafficSimulationWithCrossRoads();
		simulation.setup(1000, 20);
		
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
