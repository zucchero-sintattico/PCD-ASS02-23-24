package mvc.view;

import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;


public class StatisticalView extends JFrame{
    private final static int DEFAULT_SIZE = 1000;
    private final JLabel labelNumberOfSteps;
    private final JTextField fieldNumberOfSteps;
    private final JLabel labelNumberOfThreads;
    private final JTextField fieldNumberOfThreads;
    private final JLabel labelConsoleLog;
    private final JTextArea areaConsoleLog;
    private final JButton buttonStart;
    private final JButton buttonStop;
    private final JPanel panel;
    private final JScrollPane scroll;

    public StatisticalView(){

        //Create frame
        super();
        this.setLayout(new GridLayout(8, 0, 16, 10));
        this.setTitle("StatisticalView");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(DEFAULT_SIZE, DEFAULT_SIZE);

        //Create components
        this.labelNumberOfSteps = new JLabel("Number of steps");
        this.fieldNumberOfSteps = new JTextField("200", 1);
        this.labelNumberOfThreads = new JLabel("Number of threads");
        this.fieldNumberOfThreads = new JTextField("18", 1);
        this.labelConsoleLog = new JLabel("Console log");
        this.areaConsoleLog = new JTextArea("Console log");
        this.buttonStart = new JButton("Start simulation");
        this.buttonStop = new JButton("Stop simulation");
        this.scroll = new JScrollPane(this.areaConsoleLog);
        this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.panel = new JPanel(new GridLayout(1, 1, 30, 30));

        //Add components on panel
        this.add(this.labelNumberOfSteps);
        this.add(this.fieldNumberOfSteps);
        this.add(this.labelNumberOfThreads);
        this.add(this.fieldNumberOfThreads);
        this.add(this.labelConsoleLog);
        this.add(this.scroll);
        this.panel.add(this.buttonStart);
        this.panel.add(this.buttonStop);
        this.add(this.panel);

        //Add properties
        this.areaConsoleLog.setMargin(new Insets(10, 10, 10, 10));
        this.areaConsoleLog.setEditable(false);
        this.fieldNumberOfSteps.setMargin(new Insets(10, 10, 10, 10));

        //Add container on frame
        this.setLocationRelativeTo(null);
        this.setResizable(false);

    }

    public void display(){
        SwingUtilities.invokeLater(() -> this.setVisible(true));
    }

    public void updateView(String message){
        this.areaConsoleLog.append(message + "\n");
        this.areaConsoleLog.setCaretPosition(this.areaConsoleLog.getDocument().getLength());
    }

    public int getNumberOfSteps(){
        return Integer.valueOf(this.fieldNumberOfSteps.getText());
    }

    public int getNumberOfThreads(){
        return Integer.valueOf(this.fieldNumberOfThreads.getText());
    }

    public JButton getStartButton(){
        return this.buttonStart;
    }

    public JButton getStopButton(){
        return this.buttonStop;
    }

    
    public void clearTextArea(){
        this.areaConsoleLog.setText("");
    }
}
