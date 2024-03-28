package pcd.ass01ridesign.monitor.agent;


import pcd.ass01ridesign.monitor.environment.Road;

public class CarAgentInfo {
	//todo refactor
	//todo maybe want a basecaragent
	private AbstractCarAgent car;
	private double pos;
	private Road road;
	
	public CarAgentInfo(AbstractCarAgent car, Road road, double pos) {
		this.car = car;
		this.road = road;
		this.pos = pos;
	}
	
	public double getPos() {
		return pos;
	}
	
	public void updatePos(double pos) {
		this.pos = pos;
	}
	
	public AbstractCarAgent getCar() {
		return car;
	}	
	
	public Road getRoad() {
		return road;
	}
}
