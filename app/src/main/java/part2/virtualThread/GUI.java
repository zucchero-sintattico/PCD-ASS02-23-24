package part2.virtualThread;

import part2.virtualThread.monitor.SafeCounter;
import part2.virtualThread.monitor.SafeSet;
import part2.virtualThread.search.SearchController;
import part2.virtualThread.search.SearchListener;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame implements SearchListener {
    private final static int DEFAULT_SIZE = 600;
    private JLabel labelAddress;
    private JLabel labelWord;
    private JLabel labelDepth;
    private JLabel labelLinkRequested;
    private JLabel labelWordFound;
    private JLabel labelThreadAlive;
    private JLabel labelOutput;
    private JTextField fieldAddress;
    private JTextField fieldWord;
    private JTextField fieldDepth;
    private JTextField fieldLinkRequested;
    private JTextField fieldWordFound;
    private JTextField fieldThreadAlive;
    private JTextArea areaOutput;
    private JScrollPane scroll;
    private JButton buttonStart;
    private JButton buttonStop;
    private JPanel container;
    private JPanel logContainer;
    private JPanel buttonContainer;
    private final SearchController searchController = new SearchController(this);
    public GUI(){
        super();

        this.setFrameProperties();

        this.setViewComponents();

        this.addAllComponentsIntoFrame();

        this.editAllComponentsProperties();

        this.addListeners();
        //temp setting
        this.fieldAddress.setText("https://www.unipg.it/");
        this.fieldWord.setText("ingegneria");
        this.fieldDepth.setText("3");
    }

    private void addListeners() {
        this.buttonStart.addActionListener(e -> {
            String address = this.fieldAddress.getText();
            String word = this.fieldWord.getText();
            int depth = Integer.parseInt(this.fieldDepth.getText());
            this.searchController.start(address, word, depth);
        });

        this.buttonStop.addActionListener(e -> {
            this.searchController.stop();
        });
    }

    private void editAllComponentsProperties() {
        this.areaOutput.setMargin(new Insets(10, 10, 10, 10));
        this.areaOutput.setEditable(false);
        this.labelAddress.setFont(new Font(getName(), Font.BOLD, 16));
        this.labelWord.setFont(new Font(getName(), Font.BOLD, 16));
        this.labelDepth.setFont(new Font(getName(), Font.BOLD, 16));
        this.labelOutput.setFont(new Font(getName(), Font.BOLD, 16));
        this.areaOutput.setFont(new Font(getName(), Font.PLAIN, 16));
        this.buttonStart.setFont(new Font(getName(), Font.PLAIN, 16));
        this.buttonStop.setFont(new Font(getName(), Font.PLAIN, 16));
        this.fieldAddress.setFont(new Font(getName(), Font.PLAIN, 16));
        this.fieldWord.setFont(new Font(getName(), Font.PLAIN, 16));
        this.fieldDepth.setFont(new Font(getName(), Font.PLAIN, 16));
        this.fieldLinkRequested.setFont(new Font(getName(), Font.PLAIN, 16));
        this.fieldWordFound.setFont(new Font(getName(), Font.PLAIN, 16));
        this.fieldThreadAlive.setFont(new Font(getName(), Font.PLAIN, 16));
        this.fieldLinkRequested.setEditable(false);
        this.fieldWordFound.setEditable(false);
        this.fieldThreadAlive.setEditable(false);
    }

    private void addAllComponentsIntoFrame() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(30, 16, 0, 16);
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        this.add(this.container, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(30, 16, 0, 16);
        constraints.fill = GridBagConstraints.NONE;
        this.add(this.logContainer, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.insets = new Insets(30, 16, 0, 16);
        constraints.fill = GridBagConstraints.NONE;
        this.add(this.labelOutput, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.insets = new Insets(16, 16, 0, 16);
        constraints.fill = GridBagConstraints.NONE;
        constraints.ipadx = 500;
        constraints.ipady = 300;
        this.add(this.scroll, constraints);

        constraints.gridx = 0;
        constraints.gridy = 10;
        constraints.ipady = 10;
        constraints.ipadx = 10;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        this.add(this.buttonContainer, constraints);
    }

    private void setViewComponents() {
        this.labelAddress = new JLabel("Address");
        this.labelWord = new JLabel("Word");
        this.labelDepth = new JLabel("Depth");
        this.labelOutput = new JLabel("Output");
        this.labelLinkRequested = new JLabel("Link Requested");
        this.labelWordFound = new JLabel("Word Found");
        this.labelThreadAlive = new JLabel("Thread Alive");
        this.fieldAddress = new JTextField();
        this.fieldWord = new JTextField();
        this.fieldDepth = new JTextField();
        this.fieldLinkRequested = new JTextField();
        this.fieldWordFound = new JTextField();
        this.fieldThreadAlive = new JTextField();
        this.areaOutput = new JTextArea("Output");
        this.scroll = new JScrollPane(this.areaOutput);
        this.buttonStart = new JButton("Start");
        this.buttonStop = new JButton("Stop");
        this.container = new JPanel(new GridLayout(2, 3, 16, 4));
        this.logContainer = new JPanel(new GridLayout(1, 6, 16, 4));
        this.buttonContainer = new JPanel(new GridLayout(1, 2, 16, 16));
        this.container.add(this.labelAddress);
        this.container.add(this.labelWord);
        this.container.add(this.labelDepth);
        this.container.add(this.fieldAddress);
        this.container.add(this.fieldWord);
        this.container.add(this.fieldDepth);
        this.logContainer.add(this.labelLinkRequested);
        this.logContainer.add(this.fieldLinkRequested);
        this.logContainer.add(this.labelWordFound);
        this.logContainer.add(this.fieldWordFound);
        this.logContainer.add(this.labelThreadAlive);
        this.logContainer.add(this.fieldThreadAlive);
        this.buttonContainer.add(this.buttonStart);
        this.buttonContainer.add(this.buttonStop);
    }

    private void setFrameProperties() {
        this.setLayout(new GridBagLayout());
        this.setTitle("Web Word Counter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(DEFAULT_SIZE, DEFAULT_SIZE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    public void display(){
        SwingUtilities.invokeLater(() -> this.setVisible(true));
    }

    @Override
    public void pageFound(String pageUrl) {
        SwingUtilities.invokeLater(() -> {
            this.areaOutput.append("Page found: " + pageUrl + "\n");
            this.areaOutput.setCaretPosition(this.areaOutput.getDocument().getLength());
        });
    }

    @Override
    public void pageRequested(String pageUrl, SafeSet totalPageRequested) {
        SwingUtilities.invokeLater(() -> {
            this.fieldLinkRequested.setText(String.valueOf(totalPageRequested.size()));
            this.areaOutput.append("Page requested: " + pageUrl + "\n");
            this.areaOutput.setCaretPosition(this.areaOutput.getDocument().getLength());
        });
    }

    @Override
    public void pageDown(String exceptionMessage, String pageUrl) {
        SwingUtilities.invokeLater(() -> {
            this.areaOutput.append("Page down: " + pageUrl + " Reason: " + exceptionMessage + "\n");
            this.areaOutput.setCaretPosition(this.areaOutput.getDocument().getLength());
        });
    }

    @Override
    public void countUpdated(int wordFound, String pageUrl, SafeCounter totalWordFound) {
        SwingUtilities.invokeLater(() -> {
            this.fieldWordFound.setText(String.valueOf(totalWordFound.getValue()));
            this.areaOutput.append("Total: " + totalWordFound.getValue() + " word occurrences from: " + pageUrl + "\n");
            this.areaOutput.setCaretPosition(this.areaOutput.getDocument().getLength());
        });
    }

    @Override
    public void searchEnded(int linkFound, int linkExplored, int linkDown, int wordFound) {
        SwingUtilities.invokeLater(() -> {
            this.areaOutput.append("Link Found: " + linkFound + "\n");
            this.areaOutput.append("Link Explored: " + linkExplored + "\n");
            this.areaOutput.append("Link Up: " + (linkExplored - linkDown) + "\n");
            this.areaOutput.append("Link Down: " + linkDown + "\n");
            this.areaOutput.append("Total Occurrences: " + wordFound + "\n");
            this.areaOutput.setCaretPosition(this.areaOutput.getDocument().getLength());
        });
    }

    @Override
    public void threadAliveUpdated(SafeCounter treadAlive) {
        SwingUtilities.invokeLater(() -> {
            this.fieldThreadAlive.setText(String.valueOf(treadAlive.getValue()));
        });
    }
}
