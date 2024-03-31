package model.passiveComponent.environment;


import model.passiveComponent.agent.AbstractCarAgent;
import model.passiveComponent.agent.action.Action;
import model.passiveComponent.agent.action.MoveForward;
import model.passiveComponent.agent.perception.CarPerception;
import model.passiveComponent.agent.perception.Perception;
import model.passiveComponent.environment.road.Road;
import model.passiveComponent.environment.road.RoadImpl;
import model.passiveComponent.environment.trafficLight.TrafficLight;
import model.passiveComponent.environment.trafficLight.TrafficLightImpl;
import model.passiveComponent.environment.trafficLight.TrafficLightState;
import utils.Point2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class RoadsEnvironment implements Environment {
    private static final int MIN_DIST_ALLOWED = 5;
    private static final int CAR_DETECTION_RANGE = 30;
    private static final int SEM_DETECTION_RANGE = 30;
    private int simulationStep;

    /* list of roads */
    private List<Road> roads;

    /* traffic lights */
//    private List<TrafficLight> trafficLights;

    /* cars situated in the environment */
    private HashMap<String, AbstractCarAgent> registeredCars;

    public RoadsEnvironment() {
        this.registeredCars = new HashMap<>();
//        this.trafficLights = new ArrayList<>();
        this.roads = new ArrayList<>();
    }

    @Override
    public void registerNewCar(AbstractCarAgent abstractAgent) {
        registeredCars.put(abstractAgent.getAgentID(), abstractAgent);
    }


//    @Override
//    public AbstractCarAgent createBaseAgent(Point2D p0, Point2D p1) {
//        Road r = new RoadImpl(p0, p1);
//        this.roads.add(r);
//        return r;
//    }
//
//    @Override
//    public AbstractCarAgent createExtendedAgent(Point2D p0, Point2D p1) {
//        Road r = new RoadImpl(p0, p1);
//        this.roads.add(r);
//        return r;
//    }


    @Override
    public Road createRoad(Point2D p0, Point2D p1) {
        Road r = new RoadImpl(p0, p1);
        this.roads.add(r);
        return r;
    }

    @Override
    public Perception getCurrentPercept(String agentID) {
        AbstractCarAgent carInfo = registeredCars.get(agentID);
        double pos = carInfo.getPosition();
        Road road = carInfo.getRoad();
        Optional<AbstractCarAgent> nearestCar = getNearestCarInFront(road, pos, CAR_DETECTION_RANGE);
        Optional<TrafficLight> nearestSem = getNearestSemaphoreInFront(road, pos, SEM_DETECTION_RANGE);
        return new CarPerception(pos, nearestCar.orElse(null), nearestSem.orElse(null));
    }

    private Optional<AbstractCarAgent> getNearestCarInFront(Road road, double carPos, double range) {
        return
                registeredCars
                        .entrySet()
                        .stream()
                        .map(el -> el.getValue())
                        .filter((carInfo) -> carInfo.getRoad() == road)
                        .filter((carInfo) -> {
                            double dist = carInfo.getPosition() - carPos;
                            return dist > 0 && dist <= range;
                        })
                        .min((c1, c2) -> (int) Math.round(c1.getPosition() - c2.getPosition()));
    }

    private Optional<TrafficLight> getNearestSemaphoreInFront(Road road, double carPos, double range) {
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
                AbstractCarAgent info = registeredCars.get(agentID);
                Road road = info.getRoad();
                Optional<AbstractCarAgent> nearestCar = getNearestCarInFront(road, info.getPosition(), CAR_DETECTION_RANGE);

                if (!nearestCar.isEmpty()) {
                    double dist = nearestCar.get().getPosition() - info.getPosition();
                    if (dist > mv.getDistance() + MIN_DIST_ALLOWED) {
                        info.updatePosition(info.getPosition() + mv.getDistance());
                    }
                } else {
                    info.updatePosition(info.getPosition() + mv.getDistance());
                }

                if (info.getPosition() > road.getLen()) {
                    info.updatePosition(0);
                }
                break;
            }
            default:
                break;
        }

    }

    @Override
    public void step() {
        for (Road r :
                roads) {
            for (TrafficLight tl : r.getTrafficLights()) {
                tl.step(simulationStep);
            }
        }

    }

    public List<AbstractCarAgent> getAgentInfo() {
        return this.registeredCars.entrySet().stream().map(el -> el.getValue()).toList();
    }

    public List<Road> getRoads() {
        return roads;
    }

    //todo maybe optimize with an init
    public List<TrafficLight> getTrafficLights() {
        List<TrafficLight> tl = new ArrayList<>();
        for (Road r :
                roads) {
            tl.addAll(r.getTrafficLights());
        }
        return tl;
    }

    @Override
    public void setup(int dt) {
        this.simulationStep = dt;
    }
}
