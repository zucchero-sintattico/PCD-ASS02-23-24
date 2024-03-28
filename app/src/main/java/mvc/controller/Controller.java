package mvc.controller;

import pcd.ass01.simengineseq.AbstractAgent;
import pcd.ass01.simengineseq.AbstractEnvironment;
import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simengineseq.SimulationListener;
import pcd.ass01.simtrafficbase.CarAgent;
import pcd.ass01.simtrafficexamples.RunTrafficSimulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import model.SimulationType;
import mvc.view.StatisticalView;

public class Controller implements ActionListener, SimulationListener{

    private final StatisticalView view;
    private AbstractSimulation simulation;
    private boolean isSimulationStarted;
    private boolean isStopped;
	private double minSpeed;
	private double maxSpeed;

    public Controller(StatisticalView view){
        this.view = view;
        this.isSimulationStarted = false;
        this.isStopped = false;
        
        //Attach the listener for the button
        this.view.getStartButton().addActionListener(this);
        this.view.getStopButton().addActionListener(this);

        this.populateComboBox();
    }

    @Override
    public void notifyInit(int t, List<AbstractAgent> agents, AbstractEnvironment env) {
        this.view.updateView("[Simulation]: START simulation");
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
        this.isStopped = true;
        this.toggleButton();
    }

    private void startSimulation() {
        if(this.isStopped && this.isSimulationStarted){
            System.out.println("Restart");
            this.simulation.play();
        }else{
            new Thread(() -> {
                this.isSimulationStarted = true;
                this.toggleButton();
                this.view.clearTextArea();
                //Setup simulation
                SimulationType simulationType = ((SimulationType)this.view.getBox().getSelectedItem());
                this.simulation = ((SimulationType)this.view.getBox().getSelectedItem()).getSimulation(simulationType);
                this.simulation.addSimulationListener(this);
                this.simulation.setup();
                this.simulation.run(this.view.getNumberOfSteps(), this.view.getNumberOfThreads());
                //Notify finish
                this.view.updateView("[SIMULATION]: " + simulationType.toString() + " FINISH");
                resetGui();
            }).start();            
        }


    }

    private void resetGui() {
        this.isSimulationStarted = false;
        this.isStopped = false;
        this.toggleButton();
    }

    public void displayView(){
        this.view.display();
    }


    private void toggleButton(){
        if(this.isSimulationStarted){
            this.view.getStartButton().setText("Restart simulation");
        }else{
            this.view.getStartButton().setText("Start simulation");
        }
    }
    
    private void populateComboBox(){
        var box = this.view.getBox();
        box.addItem(SimulationType.SINGLE_ROAD_TWO_CAR);
        box.addItem(SimulationType.SINGLE_ROAD_SEVERAL_CARS);
        box.addItem(SimulationType.SINGLE_ROAD_WITH_TRAFFIC_TWO_CAR);
        box.addItem(SimulationType.CROSS_ROADS);
        box.addItem(SimulationType.MASSIVE_SIMULATION);
    }

}
