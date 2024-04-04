package view;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import logic.controller.Controller;
import logic.controller.ControllerImpl;
import logic.passiveComponent.agent.AbstractCarAgent;
import logic.passiveComponent.environment.Environment;
import logic.passiveComponent.simulation.SimulationType;
import logic.passiveComponent.simulation.listeners.SimulationListener;

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
    private JLabel labelBox;
    private JComboBox<SimulationType> comboBox;
    private JCheckBox checkBox;
    private JCheckBox checkBoxAvaiableProcessor;
    private JScrollPane scroll;
    private JPanel inputContainer;
    private JPanel buttonContainer;
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

        this.pack();

    }

    private void setFrameProperties() {
        this.setLayout(new GridBagLayout());
        this.setTitle("Car simulator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(DEFAULT_SIZE, DEFAULT_SIZE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    private void editAllComponentsProperties() {
        this.areaConsoleLog.setMargin(new Insets(10, 10, 10, 10));
        this.areaConsoleLog.setEditable(false);
        this.populateComboBox();
        this.buttonStop.setEnabled(false);
        this.buttonStart.addActionListener(this);
        this.buttonStop.addActionListener(this);
        this.buttonReset.addActionListener(this);
        this.checkBoxAvaiableProcessor.addActionListener(this);
        this.checkBoxAvaiableProcessor.setSelected(true);
        this.labelNumberOfSteps.setFont(new Font(getName(), Font.PLAIN, 16));
        this.labelNumberOfThreads.setFont(new Font(getName(), Font.PLAIN, 16));
        this.labelConsoleLog.setFont(new Font(getName(), Font.PLAIN, 16));
        this.labelBox.setFont(new Font(getName(), Font.PLAIN, 16));
        this.fieldNumberOfSteps.setFont(new Font(getName(), Font.PLAIN, 14));
        this.fieldNumberOfThreads.setFont(new Font(getName(), Font.PLAIN, 14));
        this.checkBox.setFont(new Font(getName(), Font.PLAIN, 14));
        this.checkBoxAvaiableProcessor.setFont(new Font(getName(), Font.PLAIN, 14));
        this.buttonStart.setFont(new Font(getName(), Font.PLAIN, 14));
        this.buttonStop.setFont(new Font(getName(), Font.PLAIN, 14));
        this.buttonReset.setFont(new Font(getName(), Font.PLAIN, 14));
        this.areaConsoleLog.setFont(new Font(getName(), Font.PLAIN, 14));
    }

    private void addAllComponentsIntoFrame() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(30, 16, 0, 16);
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        this.add(this.inputContainer, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.insets = new Insets(16, 16, 0, 16);
        constraints.fill = GridBagConstraints.NONE;
        constraints.fill = GridBagConstraints.NONE;
        this.add(this.checkBoxAvaiableProcessor, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.insets = new Insets(16, 16, 0, 16);
        constraints.fill = GridBagConstraints.NONE;
        constraints.fill = GridBagConstraints.NONE;
        this.add(this.labelBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.ipadx = 500;
        constraints.fill = GridBagConstraints.NONE;
        this.add(this.comboBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.ipadx = 0;
        constraints.fill = GridBagConstraints.NONE;
        this.add(this.checkBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.ipadx = 0;
        constraints.fill = GridBagConstraints.NONE;
        this.add(this.labelConsoleLog, constraints);

        constraints.gridx = 0;
        constraints.gridy = 9;
        constraints.ipady = 200;
        constraints.ipadx = 500;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        this.add(this.scroll, constraints);

        constraints.gridx = 0;
        constraints.gridy = 10;
        constraints.ipady = 10;
        constraints.ipadx = 10;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        this.add(this.buttonContainer, constraints);
    }

    private void setViewComponents() {
        this.labelNumberOfSteps = new JLabel("Number of steps");
        this.fieldNumberOfSteps = new JTextField("200", 1);
        this.labelNumberOfThreads = new JLabel("Number of threads");
        this.fieldNumberOfThreads = new JTextField(this.getProcessor(), 1);
        this.labelConsoleLog = new JLabel("Console log");
        this.areaConsoleLog = new JTextArea("Console log");
        this.buttonStart = new JButton("Start simulation");
        this.buttonReset = new JButton("Reset simulation");
        this.buttonStop = new JButton("Stop simulation");
        this.scroll = new JScrollPane(this.areaConsoleLog);
        this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.labelBox = new JLabel("Choise simulation");
        this.comboBox = new JComboBox<SimulationType>();
        this.checkBox = new JCheckBox("Display simulation view");
        this.fieldNumberOfThreads.setEnabled(false);
        this.checkBoxAvaiableProcessor = new JCheckBox("Use avaiable processor (max: " + getProcessor() + ")");
        this.inputContainer = new JPanel(new GridLayout(2, 2, 10, 10));
        this.inputContainer.add(this.labelNumberOfSteps);
        this.inputContainer.add(this.labelNumberOfThreads);
        this.inputContainer.add(this.fieldNumberOfSteps);
        this.inputContainer.add(this.fieldNumberOfThreads);
        this.buttonContainer = new JPanel(new FlowLayout());
        this.buttonContainer.add(this.buttonStart);
        this.buttonContainer.add(this.buttonStop);
        this.buttonContainer.add(this.buttonReset);
    }

    private String getProcessor() {
        return String.valueOf(this.controller.getAvailableProcessor());
    }

    public void display() {
        SwingUtilities.invokeLater(() -> this.setVisible(true));
    }

    public void updateView(String message) {
        this.areaConsoleLog.append(message + "\n");
        this.areaConsoleLog.setCaretPosition(this.areaConsoleLog.getDocument().getLength());
    }

    public Optional<Integer> getNumberOfSteps() {
        try {
            return Optional.of(Integer.valueOf(this.fieldNumberOfSteps.getText()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Integer> getNumberOfThreads() {
        try {
            return Optional.of(Integer.valueOf(this.fieldNumberOfThreads.getText()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean validateInput(){
        if(this.getNumberOfSteps().isEmpty()){
            displayMessageDialog("Number of steps isn't an integer");
            return false;
        }else if(this.getNumberOfThreads().isEmpty()){
            displayMessageDialog("Number of threads isn't an integer");
            return false;
        }else{
            return true;
        }
    }

    private void displayMessageDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
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
                    if(validateInput()){
                        updateViewWhenSimulationStart();
                        this.clearTextArea();
                        this.controller.setupSimulation(this.getSimulationType(), this.getNumberOfSteps().get(), this.getNumberOfThreads().get());
                        if(this.getShowViewFlag()){
                            this.controller.showView();
                        }
                        this.controller.attachListener(this);
                        this.controller.startSimulation();
                    }
                });
            } else {
                SwingUtilities.invokeLater(() -> {
                    updateViewWhenSimulationStart();
                    this.controller.startSimulation();
                });
            }
        } else if (e.getSource() == this.buttonReset) {
            SwingUtilities.invokeLater(() -> {
                if(validateInput()){
                    this.clearTextArea();
                    this.areaConsoleLog.setText("Console log");
                    this.resetView();
                    this.controller.setupSimulation(this.getSimulationType(), this.getNumberOfSteps().get(), this.getNumberOfThreads().get());
                }
            });
        }else if(e.getSource() == this.checkBoxAvaiableProcessor){
            if(this.checkBoxAvaiableProcessor.isSelected()){
                this.fieldNumberOfThreads.setEnabled(false);
                this.fieldNumberOfThreads.setText(this.getProcessor());
            }else{
                this.fieldNumberOfThreads.setEnabled(true);
                this.fieldNumberOfThreads.setText(this.getProcessor());
            }
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
