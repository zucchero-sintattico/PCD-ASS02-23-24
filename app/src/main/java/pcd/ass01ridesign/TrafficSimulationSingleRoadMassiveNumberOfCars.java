package pcd.ass01ridesign;


import pcd.ass01ridesign.activeComponent.AbstractSimulation;
import pcd.ass01ridesign.monitor.agent.AbstractCarAgent;
import pcd.ass01ridesign.monitor.agent.BaseCarAgent;
import pcd.ass01ridesign.monitor.environment.Environment;
import pcd.ass01ridesign.monitor.environment.Road;
import pcd.ass01ridesign.monitor.environment.RoadsEnvironment;
import pcd.ass01ridesign.passiveComponent.utils.Point2D;

public class TrafficSimulationSingleRoadMassiveNumberOfCars extends AbstractSimulation {

	private int numCars;
	
	public TrafficSimulationSingleRoadMassiveNumberOfCars(int numCars) {
		super();
		this.numCars = numCars;
	}
	
	public void setup() {
		int dt= 1;
		this.setupTimings(0, dt);
		//todo dove si passava il dt???
		Environment env = new RoadsEnvironment(dt);
		this.setupEnvironment(env);
		
		Road road = env.createRoad(new Point2D(0,300), new Point2D(15000,300));

		for (int i = 0; i < numCars; i++) {
			
			String carId = "car-" + i;
			double initialPos = i*10;			
			double carAcceleration = 1; //  + gen.nextDouble()/2;
			double carDeceleration = 0.3; //  + gen.nextDouble()/2;
			double carMaxSpeed = 7; // 4 + gen.nextDouble();
						
			AbstractCarAgent car = new BaseCarAgent(carId, env,
									road,
									initialPos, 
									carAcceleration, 
									carDeceleration,
									carMaxSpeed,1);
			this.addAgent(car);
			
			
			/* no sync with wall-time */
		}
		
	}	
}
	