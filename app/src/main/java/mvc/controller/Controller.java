package mvc.controller;

import pcd.ass01.simengineseq.AbstractAgent;
import pcd.ass01.simengineseq.AbstractEnvironment;
import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simengineseq.SimulationListener;
import pcd.ass01.simtrafficbase.CarAgent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.SwingUtilities;

import mvc.view.StatisticalView;

public class Controller implements ActionListener, SimulationListener{

    private final StatisticalView view;
    private final AbstractSimulation simulation;
    private boolean isSimulationStarted;
	private double averageSpeed;
	private double minSpeed;
	private double maxSpeed;
    private int step;

    public Controller(StatisticalView view, AbstractSimulation simulation){
        this.view = view;
        this.simulation = simulation;
        this.isSimulationStarted = false;
        
        //Attach the listener for the button
        this.view.getStartButton().addActionListener(this);
        this.view.getStopButton().addActionListener(this);

        //Setup simulation
        this.simulation.addSimulationListener(this);

        this.averageSpeed = 0;
        this.step = 0;
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
        this.step++;
        this.view.updateView("[STAT]: average speed: " + avSpeed);
        this.view.updateView("[STAT]: step: " + step);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.view.getStartButton()){
            new Thread(() -> {
                this.isSimulationStarted = true;
                this.toggleButton();
                this.view.clearTextArea();
                this.simulation.run(this.view.getNumberOfSteps(), this.view.getNumberOfThreads());
            }).start();
        }
        if(e.getSource() == this.view.getStopButton()){
            this.isSimulationStarted = false;
            this.toggleButton();
        }
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
