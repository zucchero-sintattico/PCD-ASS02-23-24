package part1.logic.simulation.listeners;

import part1.logic.passiveComponent.agent.AbstractCarAgent;
import part1.logic.passiveComponent.environment.Environment;
import java.io.FileWriter;
import java.util.List;

public class RoadSimStatistics implements SimulationListener {

	private double averageSpeed;
	private double minSpeed;
	private double maxSpeed;
	
	public RoadSimStatistics() {}
	
	@Override
	public void notifyInit(int t, List<AbstractCarAgent> agents, Environment env) {
		averageSpeed = 0;
	}

	@Override
	public void notifyStepDone(int t, List<AbstractCarAgent> agents, Environment env) {}
	
	public double getAverageSpeed() {
		return this.averageSpeed;
	}

	public double getMinSpeed() {
		return this.minSpeed;
	}
	
	public double getMaxSpeed() {
		return this.maxSpeed;
	}
	
	
	private void log(String msg) {
		System.out.println("[STAT] " + msg);

		//save to file
		//delete file if exists
		try {
			FileWriter fw = new FileWriter("log.txt", true);
			fw.write(msg + "\n");
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void notifySimulationEnded() {
		
	}

	@Override
	public void notifyStat(double averageSpeed) {
		log("average speed: " + averageSpeed);
	}

}
