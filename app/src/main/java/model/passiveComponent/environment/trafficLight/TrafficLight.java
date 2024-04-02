package model.passiveComponent.environment.trafficLight;

import utils.Point2D;

public interface TrafficLight {

	void step(int dt);

	Point2D getPosition();

	double getRoadPosition();

	boolean isGreen();

	boolean isRed();

	boolean isYellow();

}
