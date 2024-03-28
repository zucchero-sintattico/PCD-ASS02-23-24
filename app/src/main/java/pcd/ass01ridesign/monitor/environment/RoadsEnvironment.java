package pcd.ass01ridesign.monitor.environment;


import pcd.ass01ridesign.monitor.agent.AbstractCarAgent;
import pcd.ass01ridesign.monitor.agent.CarAgentInfo;
import pcd.ass01ridesign.monitor.agent.action.Action;
import pcd.ass01ridesign.monitor.agent.action.MoveForward;
import pcd.ass01ridesign.monitor.agent.perception.CarPerception;
import pcd.ass01ridesign.monitor.agent.perception.Percept;
import pcd.ass01ridesign.passiveComponent.utils.Point2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class RoadsEnvironment implements Environment {
    private static final int MIN_DIST_ALLOWED = 5;
    private static final int CAR_DETECTION_RANGE = 30;
    private static final int SEM_DETECTION_RANGE = 30;
    private final int simulationStep;

    /* list of roads */
    private List<Road> roads;

    /* traffic lights */
    private List<TrafficLight> trafficLights;

    /* cars situated in the environment */
    private HashMap<String, CarAgentInfo> registeredCars;

    public RoadsEnvironment(int simulationStep) {
        this.simulationStep = simulationStep;
        this.registeredCars = new HashMap<>();
        this.trafficLights = new ArrayList<>();
        this.roads = new ArrayList<>();
    }

    @Override
    public void registerNewCar(AbstractCarAgent abstractAgent, Road road, double initialPos) {
        registeredCars.put(abstractAgent.getAgentID(), new CarAgentInfo(abstractAgent, road, initialPos));
    }


    @Override
    public Road createRoad(Point2D p0, Point2D p1) {
        Road r = new RoadImpl(p0, p1);
        this.roads.add(r);
        return r;
    }

    @Override
    public TrafficLight createTrafficLight(Point2D pos, TrafficLightState initialState, int greenDuration, int yellowDuration, int redDuration) {
        TrafficLight tl = new TrafficLightImpl(pos, initialState, greenDuration, yellowDuration, redDuration);
        this.trafficLights.add(tl);
        tl.init();
        return tl;
    }

    @Override
    public Percept getCurrentPercept(String agentID) {
        CarAgentInfo carInfo = registeredCars.get(agentID);
        double pos = carInfo.getPos();
        Road road = carInfo.getRoad();
        Optional<CarAgentInfo> nearestCar = getNearestCarInFront(road, pos, CAR_DETECTION_RANGE);
        Optional<TrafficLightInfo> nearestSem = getNearestSemaphoreInFront(road, pos, SEM_DETECTION_RANGE);
        return new CarPerception(pos, nearestCar.orElse(null), nearestSem.orElse(null));
    }

    private Optional<CarAgentInfo> getNearestCarInFront(Road road, double carPos, double range) {
        return
                registeredCars
                        .entrySet()
                        .stream()
                        .map(el -> el.getValue())
                        .filter((carInfo) -> carInfo.getRoad() == road)
                        .filter((carInfo) -> {
                            double dist = carInfo.getPos() - carPos;
                            return dist > 0 && dist <= range;
                        })
                        .min((c1, c2) -> (int) Math.round(c1.getPos() - c2.getPos()));
    }

    private Optional<TrafficLightInfo> getNearestSemaphoreInFront(Road road, double carPos, double range) {
        return
                road.getTrafficLights()
                        .stream()
                        .filter(tl -> tl.getRoadPos() > carPos)
                        .min((c1, c2) -> (int) Math.round(c1.getRoadPos() - c2.getRoadPos()));
    }

    @Override
    public void doAction(String agentID, Action selectedAction) {

        switch (selectedAction) {
            case MoveForward mv: {
                CarAgentInfo info = registeredCars.get(agentID);
                Road road = info.getRoad();
                Optional<CarAgentInfo> nearestCar = getNearestCarInFront(road, info.getPos(), CAR_DETECTION_RANGE);

                if (!nearestCar.isEmpty()) {
                    double dist = nearestCar.get().getPos() - info.getPos();
                    if (dist > mv.getDistance() + MIN_DIST_ALLOWED) {
                        info.updatePos(info.getPos() + mv.getDistance());
                    }
                } else {
                    info.updatePos(info.getPos() + mv.getDistance());
                }

                if (info.getPos() > road.getLen()) {
                    info.updatePos(0);
                }
                break;
            }
            default:
                break;
        }

    }

    @Override
    public void step() {
        for (TrafficLight tl : trafficLights) {
            tl.step(simulationStep);
        }
    }

    public List<CarAgentInfo> getAgentInfo(){
        return this.registeredCars.entrySet().stream().map(el -> el.getValue()).toList();
    }

    public List<Road> getRoads(){
        return roads;
    }

    public List<TrafficLight> getTrafficLights(){
        return trafficLights;
    }
}
