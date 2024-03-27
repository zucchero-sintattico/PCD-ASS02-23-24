package mvc.controller;

import pcd.ass01.simengineseq.AbstractAgent;
import pcd.ass01.simengineseq.AbstractEnvironment;
import pcd.ass01.simengineseq.AbstractSimulation;
import pcd.ass01.simengineseq.SimulationListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.SwingUtilities;

import mvc.view.StatisticalView;

public class Controller implements ActionListener, SimulationListener{

    private final StatisticalView view;
    private final AbstractSimulation simulation;
    private boolean isSimulationStarted;

    public Controller(StatisticalView view, AbstractSimulation simulation){
        this.view = view;
        this.simulation = simulation;
        this.isSimulationStarted = false;
        
        //Attach the listener for the button
        this.view.getStartButton().addActionListener(this);
        this.view.getStopButton().addActionListener(this);

        //Setup simulation
        this.simulation.addSimulationListener(this);
    }

    @Override
    public void notifyInit(int t, List<AbstractAgent> agents, AbstractEnvironment env) {
        this.view.updateView("[Simulation]: Start simulation");
    }

    @Override
    public void notifyStepDone(int t, List<AbstractAgent> agents, AbstractEnvironment env) {
        this.view.updateView("[Simulation]: Prova");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.view.getStartButton()){
            SwingUtilities.invokeLater(() -> {
                this.simulation.run(this.view.getNumberOfSteps(), this.view.getNumberOfThreads());
                this.isSimulationStarted = true;
                this.toggleButton();
            });
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
