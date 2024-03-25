import org.junit.jupiter.api.Test;
import pcd.ass01.simtrafficexamples.RoadSimStatistics;
import pcd.ass01.simtrafficexamples.TrafficSimulationSingleRoadMassiveNumberOfCars;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class MassiveTests {
    // Compare this snippet from app/src/test/java/MassiveTests.java:
    // package pcd.ass01.simtrafficexamples;
    //
    // import org.junit.jupiter.api.Test;
    //
    // public class MassiveTests {
    //
    // 	@Test
    // 	public void testMassive() {
    // 		RunTrafficSimulationMassiveTest.main(null);
    // 	}
    // }
    @Test
    public void testMassive() {
        int numCars =5000;
        int nSteps = 100;

        var simulation = new TrafficSimulationSingleRoadMassiveNumberOfCars(numCars);
        simulation.setup();
        RoadSimStatistics stat = new RoadSimStatistics();
        simulation.addSimulationListener(stat);
        simulation.run(nSteps);


        // /app/log.txt must be the same of resources/log.txt
        boolean areFilesEqual = FileComparator.compareFiles("/app/log.txt", "/app/src/test/java/resources/log.txt");
        assertTrue(areFilesEqual, "The files /app/log.txt and /app/src/test/java/resources/log.txt are not the same");





    }
}
