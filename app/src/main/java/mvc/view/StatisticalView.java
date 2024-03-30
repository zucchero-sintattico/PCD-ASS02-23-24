package mvc.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import mvc.controller.Controller;
import mvc.controller.ControllerImpl;
import pcd.ass01.passiveComponent.agent.AbstractCarAgent;
import pcd.ass01.passiveComponent.environment.Environment;
import pcd.ass01.passiveComponent.simulation.SimulationType;
import pcd.ass01.passiveComponent.simulation.listeners.SimulationListener;

public class StatisticalView extends JFrame implements ActionListener, SimulationListener {
    private final static int DEFAULT_SIZE = 1000;
    private JLabel labelNumberOfSteps;
    private JTextField fieldNumberOfSteps;
    private JLabel labelNumberOfThreads;
    private JTextField fieldNumberOfThreads;
    private JLabel labelConsoleLog;
    private JTextArea areaConsoleLog;
    private JButton buttonStart;
    private JButton buttonReset;
    private JButton buttonStop;
    private JPanel panel;
    private JLabel labelBox;
    private JComboBox<SimulationType> comboBox;
    private JCheckBox checkBox;
    private JScrollPane scroll;
    private Controller controller;
    private boolean isStartedSimulation;

    public StatisticalView() {

        // Create frame
        super();
        setFrameProperties();

        // Set controller
        this.controller = new ControllerImpl();

        // Create components
        setViewComponents();

        // Add components on panel
        addAllComponentsIntoFrame();

        // Add properties
        editAllComponentsProperties();

    }

    private void setFrameProperties() {
        this.setLayout(new GridLayout(11, 0, 16, 8));
        this.setTitle("StatisticalView");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(DEFAULT_SIZE, DEFAULT_SIZE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    private void editAllComponentsProperties() {
        this.areaConsoleLog.setMargin(new Insets(10, 10, 10, 10));
        this.areaConsoleLog.setEditable(false);
        this.fieldNumberOfSteps.setMargin(new Insets(10, 10, 10, 10));
        this.populateComboBox();
        this.buttonStart.addActionListener(this);
        this.buttonStop.addActionListener(this);
        this.buttonReset.addActionListener(this);
    }

    private void addAllComponentsIntoFrame() {
        this.add(this.labelNumberOfSteps);
        this.add(this.fieldNumberOfSteps);
        this.add(this.labelNumberOfThreads);
        this.add(this.fieldNumberOfThreads);
        this.add(this.labelBox);
        this.add(this.comboBox);
        this.add(this.checkBox);
        this.add(this.labelConsoleLog);
        this.add(this.scroll);
        this.panel.add(this.buttonStart);
        this.panel.add(this.buttonStop);
        this.panel.add(this.buttonReset);
        this.add(this.panel);
    }

    private void setViewComponents() {
        this.labelNumberOfSteps = new JLabel("Number of steps");
        this.fieldNumberOfSteps = new JTextField("200", 1);
        this.labelNumberOfThreads = new JLabel("Number of threads");
        this.fieldNumberOfThreads = new JTextField(String.valueOf(this.controller.getAvaiableProcessor()), 1);
        this.labelConsoleLog = new JLabel("Console log");
        this.areaConsoleLog = new JTextArea("Console log");
        this.buttonStart = new JButton("Start simulation");
        this.buttonReset = new JButton("Reset simulation");
        this.buttonStop = new JButton("Stop simulation");
        this.scroll = new JScrollPane(this.areaConsoleLog);
        this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.panel = new JPanel(new GridLayout(1, 1, 30, 30));
        this.labelBox = new JLabel("Choise simulation");
        this.comboBox = new JComboBox<SimulationType>();
        this.checkBox = new JCheckBox("Display simulation view");
    }

    public void display() {
        SwingUtilities.invokeLater(() -> this.setVisible(true));
    }

    public void updateView(String message) {
        this.areaConsoleLog.append(message + "\n");
        this.areaConsoleLog.setCaretPosition(this.areaConsoleLog.getDocument().getLength());
    }

    public int getNumberOfSteps() {
        return Integer.valueOf(this.fieldNumberOfSteps.getText());
    }

    public int getNumberOfThreads() {
        return Integer.valueOf(this.fieldNumberOfThreads.getText());
    }

    public void clearTextArea() {
        this.areaConsoleLog.setText("");
    }

    public void populateComboBox() {
        this.comboBox.addItem(SimulationType.SINGLE_ROAD_TWO_CAR);
        this.comboBox.addItem(SimulationType.SINGLE_ROAD_SEVERAL_CARS);
        this.comboBox.addItem(SimulationType.SINGLE_ROAD_WITH_TRAFFIC_TWO_CAR);
        this.comboBox.addItem(SimulationType.CROSS_ROADS);
        this.comboBox.addItem(SimulationType.MASSIVE_SIMULATION);
    }

    public SimulationType getSimulationType() {
        return (SimulationType) this.comboBox.getSelectedItem();
    }

    public boolean getShowViewFlag() {
        return this.checkBox.isSelected();
    }

    @Override
    public void notifyInit(int t, List<AbstractCarAgent> agents, Environment env) {
        this.updateView("[Simulation]: START simulation");
    }

    @Override
    public void notifyStepDone(int t, List<AbstractCarAgent> agents, Environment env) {
        this.updateView("[STAT] Steps: " + t);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.buttonStart) {
            if (!this.isStartedSimulation) {
                SwingUtilities.invokeLater(() -> {
                    updateViewWhenSimulationStart();
                    this.controller.setupSimulation(this.getSimulationType(), this.getNumberOfSteps(), this.getNumberOfThreads());
                    if(this.getShowViewFlag()){
                        this.controller.showView();
                    }
                    this.controller.attachListener(this);
                    this.controller.startSimulation();
                });
            } else {
                SwingUtilities.invokeLater(() -> {
                    updateViewWhenSimulationStart();
                    this.controller.startSimulation();
                });
            }
        } else if (e.getSource() == this.buttonReset) {
            SwingUtilities.invokeLater(() -> {
                this.clearTextArea();
                this.areaConsoleLog.setText("Console log");
                this.resetView();
                this.controller.setupSimulation(this.getSimulationType(), this.getNumberOfSteps(), this.getNumberOfThreads());
            });
        } else {
            SwingUtilities.invokeLater(() -> {
                this.buttonStart.setEnabled(true);
                this.buttonStop.setEnabled(false);
                this.buttonReset.setEnabled(true);
                this.controller.stopSimulation();
            });
        }
    }

    private void updateViewWhenSimulationStart() {
        this.buttonStart.setEnabled(false);
        this.buttonStop.setEnabled(true);
        this.buttonReset.setEnabled(false);
        this.buttonStart.setText("Restart Simulation");
        this.isStartedSimulation = true;
        this.clearTextArea();
    }

    @Override
    public void notifySimulationEnded() {
        resetView();
        this.updateView("[SIMULATION] Time: " + this.controller.getSimulationDuration() + " ms");
    }

    private void resetView() {
        this.buttonStart.setEnabled(true);
        this.buttonStop.setEnabled(false);
        this.buttonReset.setEnabled(true);
        this.buttonStart.setText("Start simulation");
        this.isStartedSimulation = false;
    }

    @Override
    public void notifyStat(double averageSpeed) {
        this.updateView("[STAT]: average speed: " + averageSpeed);
    }
}
