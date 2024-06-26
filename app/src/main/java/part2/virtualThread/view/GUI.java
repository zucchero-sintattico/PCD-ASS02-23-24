package part2.virtualThread.view;

import part2.virtualThread.search.SearchController;
import part2.virtualThread.search.SearchControllerImpl;
import part2.virtualThread.search.SearchListener;
import part2.virtualThread.state.SearchReport;
import part2.virtualThread.Configuration;
import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class GUI extends JFrame implements SearchListener {

    private final static int DEFAULT_SIZE = 700;
    private JLabel labelAddress;
    private JLabel labelWord;
    private JLabel labelDepth;
    private JLabel labelLinkRequested;
    private JLabel labelWordFound;
    private JLabel labelThreadAlive;
    private JLabel labelOutput;
    private JLabel labelLinkRequestedCount;
    private JLabel labelWordFoundCount;
    private JLabel labelThreadAliveCount;
    private JTextField fieldAddress;
    private JTextField fieldWord;
    private JTextField fieldDepth;
    private JTextArea areaOutput;
    private JScrollPane scroll;
    private JButton buttonStart;
    private JButton buttonStop;
    private JButton buttonBruteStop;
    private JPanel container;
    private JPanel logContainer;
    private JPanel buttonContainer;

    private final SearchController searchController = new SearchControllerImpl(this);

    private boolean bruteStopped;

    private final Timer updater = new Timer(Configuration.GUI_UPDATE_MS, e -> {
        Optional<SearchReport> info = this.searchController.getSearchInfo();
        info.ifPresent(this::updateView);
    });

    public GUI(){
        super();

        this.setFrameProperties();

        this.setViewComponents();

        this.addAllComponentsIntoFrame();

        this.editAllComponentsProperties();

        this.addListeners();

        if(Configuration.USE_DEFAULTS){//temp setting
            this.fieldAddress.setText(Configuration.defaultRoot());
            this.fieldWord.setText(Configuration.defaultWord());
            this.fieldDepth.setText(Configuration.defaultDepth());
        }
    }

    private void addListeners() {
        this.buttonStart.addActionListener(e -> {
            String address = this.fieldAddress.getText();
            String word = this.fieldWord.getText();
            int depth = Integer.parseInt(this.fieldDepth.getText());
            this.searchController.start(address, word, depth);
        });

        this.buttonStop.addActionListener(e -> {
            this.buttonStop.setEnabled(false);
            this.searchController.stop();
        });

        this.buttonBruteStop.addActionListener(e -> {
            this.buttonStop.setEnabled(false);
            this.buttonBruteStop.setEnabled(false);
            this.searchController.bruteStop();
            this.bruteStopped = true;
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
        this.buttonStop.setEnabled(false);
        this.buttonBruteStop.setFont(new Font(getName(), Font.PLAIN, 16));
        this.buttonBruteStop.setEnabled(false);
        this.fieldAddress.setFont(new Font(getName(), Font.PLAIN, 16));
        this.fieldWord.setFont(new Font(getName(), Font.PLAIN, 16));
        this.fieldDepth.setFont(new Font(getName(), Font.PLAIN, 16));
        this.labelLinkRequestedCount.setFont(new Font(getName(), Font.PLAIN, 16));
        this.labelWordFoundCount.setFont(new Font(getName(), Font.PLAIN, 16));
        this.labelThreadAliveCount.setFont(new Font(getName(), Font.PLAIN, 16));
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
        constraints.ipadx = 603;
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
        this.labelLinkRequestedCount = new JLabel("...");
        this.labelWordFoundCount = new JLabel("...");
        this.labelThreadAliveCount = new JLabel("...");
        this.areaOutput = new JTextArea("Output");
        this.scroll = new JScrollPane(this.areaOutput);
        this.buttonStart = new JButton("Start");
        this.buttonStop = new JButton("Stop");
        this.buttonBruteStop = new JButton("Brute Stop");
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
        this.logContainer.add(this.labelLinkRequestedCount);
        this.logContainer.add(this.labelWordFound);
        this.logContainer.add(this.labelWordFoundCount);
        this.logContainer.add(this.labelThreadAlive);
        this.logContainer.add(this.labelThreadAliveCount);
        this.buttonContainer.add(this.buttonStart);
        this.buttonContainer.add(this.buttonStop);
        this.buttonContainer.add(this.buttonBruteStop);
    }

    private void setFrameProperties() {
        this.setLayout(new GridBagLayout());
        this.setTitle("Web Word Counter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(DEFAULT_SIZE, DEFAULT_SIZE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    private void updateView(SearchReport info){
        this.labelThreadAliveCount.setText(String.valueOf(info.treadAlive()));
        this.labelWordFoundCount.setText(String.valueOf(info.totalWordFound()));
        this.labelLinkRequestedCount.setText(String.valueOf(info.totalPageRequested()));
        this.areaOutput.append(info.newLog().getNewLogAndReset());
        this.areaOutput.setCaretPosition(this.areaOutput.getDocument().getLength());
    }

    public void display(){
        SwingUtilities.invokeLater(() -> this.setVisible(true));
    }

    @Override
    public void searchStarted() {
        SwingUtilities.invokeLater(() -> {
            this.areaOutput.setText("Search Started:\n");
            this.labelWordFoundCount.setText("...");
            this.labelLinkRequestedCount.setText("...");
            this.labelThreadAliveCount.setText("...");
            this.buttonStart.setEnabled(false);
            this.buttonStop.setEnabled(true);
            this.buttonBruteStop.setEnabled(true);
            this.areaOutput.setCaretPosition(this.areaOutput.getDocument().getLength());
            this.updater.start();
        });
    }

    @Override
    public void searchEnded(int linkFound, int linkDown, SearchReport info) {
        SwingUtilities.invokeLater(() -> {
            this.updater.stop();
            this.updateView(info);
            this.areaOutput.append("Link Found: " + linkFound + "\n");
            this.areaOutput.append("Link Explored: " + info.totalPageRequested() + "\n");
            this.areaOutput.append("Link Up: " + (info.totalPageRequested() - linkDown) + "\n");
            this.areaOutput.append("Link Down: " + linkDown + "\n");
            this.areaOutput.append("Total Occurrences: " + info.totalWordFound() + "\n");
            this.areaOutput.setCaretPosition(this.areaOutput.getDocument().getLength());
            this.buttonStart.setEnabled(true);
            this.buttonStop.setEnabled(false);
            this.buttonBruteStop.setEnabled(false);
            if(this.bruteStopped){
                this.labelWordFoundCount.setText("-");
                this.labelLinkRequestedCount.setText("-");
                this.labelThreadAliveCount.setText("-");
                this.bruteStopped = false;
            }
        });
    }

}
