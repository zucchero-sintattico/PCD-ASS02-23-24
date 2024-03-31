package model.passiveComponent.agent.perception;

import model.passiveComponent.agent.AbstractCarAgent;
import model.passiveComponent.environment.trafficLight.TrafficLight;

import java.util.Optional;

public class CarPerception implements Perception {
    private final double roadPos;
    private final TrafficLight nearestSem;
    private final AbstractCarAgent nearestCarInFront;

    public CarPerception(double roadPos, AbstractCarAgent nearestCarInFront, TrafficLight nearestSem){
        this.roadPos = roadPos;
        this.nearestCarInFront = nearestCarInFront;
        this.nearestSem = nearestSem;
    }

    @Override
    public double getRoadPos() {
        return roadPos;
    }

    @Override
    public Optional<TrafficLight> getNearestSem() {
        return nearestSem == null ? Optional.empty() : Optional.of(nearestSem);
    }

    @Override
    public Optional<AbstractCarAgent> getNearestCarInFront() {
        return nearestCarInFront == null ? Optional.empty() : Optional.of(nearestCarInFront);
    }
}
