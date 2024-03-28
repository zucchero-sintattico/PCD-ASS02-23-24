package model;

import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simtrafficexamples.TrafficSimulationSingleRoadMassiveNumberOfCars;
import pcd.ass01.simtrafficexamples.TrafficSimulationSingleRoadSeveralCars;
import pcd.ass01.simtrafficexamples.TrafficSimulationSingleRoadTwoCars;
import pcd.ass01.simtrafficexamples.TrafficSimulationSingleRoadWithTrafficLightTwoCars;
import pcd.ass01.simtrafficexamples.TrafficSimulationWithCrossRoads;

public enum SimulationType {
    SINGLE_ROAD_TWO_CAR("SingleRoadTwoCars", new TrafficSimulationSingleRoadTwoCars()), 
    SINGLE_ROAD_SEVERAL_CARS("SingleRoadSeveralCars", new TrafficSimulationSingleRoadSeveralCars()), 
    SINGLE_ROAD_WITH_TRAFFIC_TWO_CAR("SingleRoadWithTrafficLightTwoCars" ,new TrafficSimulationSingleRoadWithTrafficLightTwoCars()), 
    CROSS_ROADS("WithCrossRoads", new TrafficSimulationWithCrossRoads()), 
    MASSIVE_SIMULATION("SingleRoadMassiveNumberOfCars", new TrafficSimulationSingleRoadMassiveNumberOfCars(500));

    private String simulationName;
    private AbstractSimulation simulation;

    private SimulationType(String name, AbstractSimulation simulation){
        this.simulationName = name;
        this.simulation = simulation;
    }

    public String getSimulationName(){
        return this.simulationName;
    }
    
    public AbstractSimulation geSimulation(){
        return this.simulation;
    }
}
