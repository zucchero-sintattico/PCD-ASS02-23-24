package mvc.controller;

import pcd.ass01.simengineseq.AbstractAgent;
import pcd.ass01.simengineseq.AbstractEnvironment;
import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simengineseq.SimulationListener;
import pcd.ass01.simtrafficbase.CarAgent;
import pcd.ass01.simtrafficexamples.RunTrafficSimulation;
import pcd.ass01.simtrafficexamples.TrafficSimulationSingleRoadMassiveNumberOfCars;
import pcd.ass01.simtrafficexamples.TrafficSimulationWithCrossRoads;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import mvc.view.StatisticalView;

public class Controller implements ActionListener, SimulationListener{

    private final StatisticalView view;
    private AbstractSimulation simulation;
    private boolean isSimulationStarted;
	private double minSpeed;
	private double maxSpeed;

    public Controller(StatisticalView view){
        this.view = view;
        this.isSimulationStarted = false;
        
        //Attach the listener for the button
        this.view.getStartButton().addActionListener(this);
        this.view.getStopButton().addActionListener(this);

        //Listener
        //this.simulation.addSimulationListener(this);
    }

    @Override
    public void notifyInit(int t, List<AbstractAgent> agents, AbstractEnvironment env) {
        this.view.updateView("[Simulation]: Start simulation");
    }

    @Override
    public void notifyStepDone(int t, List<AbstractAgent> agents, AbstractEnvironment env) {
        double avSpeed = 0;
		
		maxSpeed = -1;
		minSpeed = Double.MAX_VALUE;
		for (var agent: agents) {
			CarAgent car = (CarAgent) agent;
			double currSpeed = car.getCurrentSpeed();
			avSpeed += currSpeed;			
			if (currSpeed > maxSpeed) {
				maxSpeed = currSpeed;
			} else if (currSpeed < minSpeed) {
				minSpeed = currSpeed;
			}
		}
		
		if (agents.size() > 0) {
			avSpeed /= agents.size();
		}
        this.view.updateView("[STAT]: average speed: " + avSpeed);
        this.view.updateView("[STAT]: step: " + t);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.view.getStartButton()){
            startSimulation();
        }
        if(e.getSource() == this.view.getStopButton()){
            stopSimulation();
        }
    }

    private void stopSimulation() {
        this.simulation.pause();
        this.isSimulationStarted = false;
        this.toggleButton();
    }

    private void startSimulation() {
        new Thread(() -> {
            this.isSimulationStarted = true;
            this.toggleButton();
            this.view.clearTextArea();
            //Setup simulation
            this.simulation = RunTrafficSimulation.trafficSimulation();
            this.simulation.addSimulationListener(this);
            this.simulation.setup();
            this.simulation.run(this.view.getNumberOfSteps(), this.view.getNumberOfThreads());
            this.isSimulationStarted = false;
            this.toggleButton();
        }).start();

    }

    public void displayView(){
        this.view.display();
    }


    private void toggleButton(){
        if(this.isSimulationStarted){
            this.view.getStartButton().setEnabled(false);
        }else{
            this.view.getStartButton().setEnabled(true);
        }
    }
    
}
