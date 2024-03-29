package mvc.controller;

import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simengineseq.SimulationListener;
import pcd.ass01.simtrafficexamples.RoadSimView;

import model.SimulationType;

public class ControllerImpl implements Controller{

    //private final StatisticalView view;
    private AbstractSimulation simulation;
    private SimulationType lastType;

    public ControllerImpl(){
        updateType(SimulationType.SINGLE_ROAD_TWO_CAR);
    }

    @Override
    public void updateType(SimulationType type) {
        this.lastType = type;
        this.simulation = SimulationType.SINGLE_ROAD_TWO_CAR.getSimulation(lastType);
    }

    /*
    @Override
    public void notifyInit(int t, List<AbstractAgent> agents, AbstractEnvironment env) {
        //this.view.updateView("[Simulation]: START simulation");
    }
     */

     /* 
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
        //this.view.updateView("[STAT]: average speed: " + avSpeed);
        //this.view.updateView("[STAT]: step: " + t);
    }
    */

    @Override
    public void stopSimulation() {
        this.simulation.pause();
        //this.isStopped = true;
        //this.toggleButton();
    }

    @Override
    public void startSimulation(boolean showView, int numberOfSteps, int numberOfThread) {
        /*
        if(this.isStopped && this.isSimulationStarted){
            System.out.println("Restart");
            this.simulation.play();
        }else{
            new Thread(() -> {
                //this.isSimulationStarted = true;
                //this.toggleButton();
                //this.view.clearTextArea();
                //Setup simulation
                //SimulationType simulationType = ((SimulationType)this.view.getBox().getSelectedItem());
                //this.simulation = ((SimulationType)this.view.getBox().getSelectedItem()).getSimulation(simulationType);
                //this.simulation.addSimulationListener(this);
                this.simulation.setup();
                //Display view
                //this.isDisplaySimulationView = this.view.getCheckBox().isSelected();
                if(this.isDisplaySimulationView){
                    RoadSimView simulationView = new RoadSimView();
		            simulationView.display();
                    this.simulation.addSimulationListener(simulationView);
                }
                //this.simulation.run(this.view.getNumberOfSteps(), this.view.getNumberOfThreads());
                //Notify finish
                //this.view.updateView("[SIMULATION]: " + simulationType.toString() + " FINISH");
               // this.view.updateView("[SIMULATION] Time: " + this.simulation.getSimulationDuration() + " ms");
                //resetGui();
            }).start(); 
                     
        }
         */  

        /* Refactor */
        new Thread(() -> {
            this.simulation.setup();
            if(showView){
                RoadSimView simulationView = new RoadSimView();
                simulationView.display();
                this.simulation.addSimulationListener(simulationView);
            }
            this.simulation.run(numberOfSteps, numberOfThread);
        }).start();
    }

    @Override
    public void resetSimulation() {
        this.updateType(lastType);
    }

    @Override
    public int getAvaiableProcessor() {
        return Runtime.getRuntime().availableProcessors();
    }

    @Override
    public void attachListener(SimulationListener listener) {
        this.simulation.addSimulationListener(listener);
    }

    @Override
    public SimulationType getSimulationType() {
        return this.lastType;
    }

    @Override
    public long getSimulationDuration() {
        return this.simulation.getSimulationDuration();
    }

}
