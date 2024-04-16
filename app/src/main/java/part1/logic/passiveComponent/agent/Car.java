package part1.logic.passiveComponent.agent;

import part1.logic.passiveComponent.environment.road.Road;

public interface Car {

	String getAgentID();

	double getCurrentSpeed();

	double getPosition();

	void updatePosition(double position);

	Road getRoad();

}
