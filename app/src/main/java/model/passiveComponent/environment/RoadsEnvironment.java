package model.passiveComponent.environment;

import model.passiveComponent.agent.AbstractCarAgent;
import model.passiveComponent.agent.action.Action;
import model.passiveComponent.agent.action.MoveForward;
import model.passiveComponent.agent.perception.CarPerception;
import model.passiveComponent.agent.perception.Perception;
import model.passiveComponent.environment.road.Road;
import model.passiveComponent.environment.road.RoadImpl;
import model.passiveComponent.environment.trafficLight.TrafficLight;
import utils.Point2D;

import java.util.*;

public class RoadsEnvironment implements Environment {

	private static final int MIN_DIST_ALLOWED = 5;
	private static final int CAR_DETECTION_RANGE = 30;
	private static final int SEM_DETECTION_RANGE = 30;
	private int simulationStep;
	private final List<Road> roads = new ArrayList<>();
	private final Map<String, AbstractCarAgent> registeredCars = new HashMap<>();

	private Optional<AbstractCarAgent> getNearestCarInFront(Road road, double carPos) {
		return
				registeredCars
						.values()
						.stream()
						.filter((carInfo) -> carInfo.getRoad() == road)
						.filter((carInfo) -> {
							double dist = carInfo.getPosition() - carPos;
							return dist > 0 && dist <= (double) RoadsEnvironment.CAR_DETECTION_RANGE;
						})
						.min((c1, c2) -> (int) Math.round(c1.getPosition() - c2.getPosition()));
	}

	private Optional<TrafficLight> getNearestSemaphoreInFront(Road road, double carPos) {
		return
				road.getTrafficLights()
						.stream()
						.filter(tl -> tl.getRoadPosition() > carPos)
						.min((c1, c2) -> (int) Math.round(c1.getRoadPosition() - c2.getRoadPosition()));
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

	@Override
	public void registerNewCarAgent(AbstractCarAgent abstractAgent) {
		registeredCars.put(abstractAgent.getAgentID(), abstractAgent);
	}

	@Override
	public List<AbstractCarAgent> getCarAgents() {
		return this.registeredCars.values().stream().toList();
	}

	@Override
	public Road createRoad(Point2D p0, Point2D p1) {
		Road r = new RoadImpl(p0, p1);
		this.roads.add(r);
		return r;
	}

	@Override
	public List<Road> getRoads() {
		return roads;
	}

	//todo maybe optimize with an init
	@Override
	public List<TrafficLight> getTrafficLights() {
		List<TrafficLight> tl = new ArrayList<>();
		for (Road r :
				roads) {
			tl.addAll(r.getTrafficLights());
		}
		return tl;
	}

	@Override
	public Perception getCurrentPerception(String agentID) {
		AbstractCarAgent carInfo = registeredCars.get(agentID);
		double pos = carInfo.getPosition();
		Road road = carInfo.getRoad();
		Optional<AbstractCarAgent> nearestCar = getNearestCarInFront(road, pos);
		Optional<TrafficLight> nearestSem = getNearestSemaphoreInFront(road, pos);
		return new CarPerception(pos, nearestCar.orElse(null), nearestSem.orElse(null));
	}

	@Override
	public void doAction(String agentID, Action selectedAction) {

		switch (selectedAction) {
			case MoveForward mv -> {
				AbstractCarAgent info = registeredCars.get(agentID);
				Road road = info.getRoad();
				Optional<AbstractCarAgent> nearestCar = getNearestCarInFront(road, info.getPosition());

				if (nearestCar.isPresent()) {
					double dist = nearestCar.get().getPosition() - info.getPosition();
					if (dist > mv.getDistance() + MIN_DIST_ALLOWED) {
						info.updatePosition(info.getPosition() + mv.getDistance());
					}
				} else {
					info.updatePosition(info.getPosition() + mv.getDistance());
				}

				if (info.getPosition() > road.getLength()) {
					info.updatePosition(0);
				}
			}
			default -> {
			}
		}

	}

	@Override
	public void setup(int dt) {
		this.simulationStep = dt;
	}

}
