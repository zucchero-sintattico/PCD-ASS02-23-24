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

public class StatisticalView{
    private final static int DEFAULT_SIZE = 500;
    private final JFrame frame;
    private final JLabel labelNumberOfSteps;
    private final JTextField fieldNumberOfSteps;
    private final JLabel labelConsoleLog;
    private final JTextArea areaConsoleLog;
    private final JButton buttonStart;
    private final JButton buttonStop;
    private final JPanel panel;
    private final JScrollPane scroll;

    public StatisticalView(){

        //Create frame
        this.frame = new JFrame();
        this.frame.setLayout(new GridLayout(6, 0, 16, 10));
        this.frame.setTitle("StatisticalView");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(DEFAULT_SIZE, DEFAULT_SIZE);

        //Create components
        this.labelNumberOfSteps = new JLabel("Number of steps");
        this.fieldNumberOfSteps = new JTextField("Insert number of steps...", 1);
        this.labelConsoleLog = new JLabel("Console log");
        this.areaConsoleLog = new JTextArea("Console log");
        this.buttonStart = new JButton("Start simulation");
        this.buttonStop = new JButton("Stop simulation");
        this.scroll = new JScrollPane(this.areaConsoleLog);
        this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.panel = new JPanel(new GridLayout(1, 1, 30, 30));

        //Add components on panel
        this.frame.add(this.labelNumberOfSteps);
        this.frame.add(this.fieldNumberOfSteps);
        this.frame.add(this.labelConsoleLog);
        this.frame.add(this.scroll);
        this.panel.add(this.buttonStart);
        this.panel.add(this.buttonStop);
        this.frame.add(this.panel);

        //Add properties
        this.areaConsoleLog.setMargin(new Insets(10, 10, 10, 10));

        //Add container on frame
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);

    }

    public void display(){
        SwingUtilities.invokeLater(() -> this.frame.setVisible(true));
    }
}
