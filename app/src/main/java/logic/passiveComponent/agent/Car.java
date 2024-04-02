package logic.passiveComponent.agent;

import logic.passiveComponent.environment.road.Road;

public interface Car {

	String getAgentID();

	double getCurrentSpeed();

	double getPosition();

	void updatePosition(double position);

	Road getRoad();

}
