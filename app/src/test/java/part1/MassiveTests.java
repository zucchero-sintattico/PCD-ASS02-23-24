package part1;

import part1.logic.activeComponent.SimulationRunner;
import part1.logic.simulation.examples.TrafficSimulationSingleRoadMassiveNumberOfCars;
import part1.logic.simulation.examples.TrafficSimulationWithCrossRoads;
import part1.logic.simulation.listeners.RoadSimStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MassiveTests {

	@BeforeEach
	public void setup() {
		File file = new File("log.txt");
		if (file.exists()) {
			file.delete();
		}
	}

	@Test
	public void testMassive() throws InterruptedException {
		int numCars = 5000;
		int nSteps = 100;

		var simulation = new TrafficSimulationSingleRoadMassiveNumberOfCars(numCars);
		simulation.setup(nSteps, 32);
		RoadSimStatistics stat = new RoadSimStatistics();
		simulation.addSimulationListener(stat);
		Thread t = new SimulationRunner(simulation);
		t.start();
		t.join();

		// /app/log.txt must be the same of part1.resources/log.txt
		boolean areFilesEqual = FileComparator.compareFiles("log.txt", "src/test/java/part1.resources/log_massive_improved.txt");
		assertTrue(areFilesEqual, "The files /app/log.txt and /app/src/test/java/part1.resources/log_massive_improved.txt are not the same");

	}

	@Test
	public void testTrafficLights() throws InterruptedException {

		int nSteps = 500;

		var simulation = new TrafficSimulationWithCrossRoads();
		simulation.setup(nSteps, 500);
		RoadSimStatistics stat = new RoadSimStatistics();
		simulation.addSimulationListener(stat);
		Thread t = new SimulationRunner(simulation);
		t.start();
		t.join();

		// /app/log.txt must be the same of part1.resources/log.txt
		boolean areFilesEqual = FileComparator.compareFiles("log.txt", "src/test/java/part1.resources/log_with_trafficLights_improved.txt");
		assertTrue(areFilesEqual, "The files /app/log.txt and /app/src/test/java/part1.resources/log_with_trafficLights_improved.txt are not the same");

	}

	@Test
	public void testRandMassive() throws InterruptedException {
		int numCars = 5000;
		int nSteps = 100;

		var simulation = new TrafficSimulationSingleRoadMassiveNumberOfCars(numCars, 1234);
		simulation.setup(nSteps, 32);
		RoadSimStatistics stat = new RoadSimStatistics();
		simulation.addSimulationListener(stat);
		Thread t = new SimulationRunner(simulation);
		t.start();
		t.join();

		// /app/log.txt must be the same of part1.resources/log.txt
		boolean areFilesEqual = FileComparator.compareFiles("log.txt", "src/test/java/part1.resources/rand_log.txt");
		assertTrue(areFilesEqual, "The files /app/log.txt and /app/src/test/java/part1.resources/rand_log.txt are not the same");

	}

}
