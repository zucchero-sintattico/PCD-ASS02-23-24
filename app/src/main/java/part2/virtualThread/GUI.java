package part2.virtualThread;

import part2.virtualThread.monitor.SafeSet;
import part2.virtualThread.search.SearchController;
import part2.virtualThread.search.SearchListener;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GUI extends JFrame implements SearchListener {
    private final static int DEFAULT_SIZE = 600;
    private JLabel labelAddress;
    private JLabel labelWord;
    private JLabel labelDepth;
    private JLabel labelOutput;
    private JTextField fieldAddress;
    private JTextField fieldWord;
    private JTextField fieldDepth;
    private JTextArea areaOutput;
    private JScrollPane scroll;
    private JButton buttonStart;
    private JButton buttonStop;
    private JPanel container;
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
        this.add(this.labelOutput, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
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
        this.fieldAddress = new JTextField();
        this.fieldWord = new JTextField();
        this.fieldDepth = new JTextField();
        this.areaOutput = new JTextArea("Output");
        this.scroll = new JScrollPane(this.areaOutput);
        this.buttonStart = new JButton("Start");
        this.buttonStop = new JButton("Stop");
        this.container = new JPanel(new GridLayout(2, 3, 16, 4));
        this.buttonContainer = new JPanel(new GridLayout(1, 2, 16, 16));
        this.container.add(this.labelAddress);
        this.container.add(this.labelWord);
        this.container.add(this.labelDepth);
        this.container.add(this.fieldAddress);
        this.container.add(this.fieldWord);
        this.container.add(this.fieldDepth);
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
    public void pageRequested(String pageUrl) {
        SwingUtilities.invokeLater(() -> {
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
    public void countUpdated(int wordFound, String pageUrl) {
        SwingUtilities.invokeLater(() -> {
            this.areaOutput.append("Total: " + wordFound + " word occurrences from: " + pageUrl + "\n");
            this.areaOutput.setCaretPosition(this.areaOutput.getDocument().getLength());
        });
    }

    @Override
    public void searchEnded(SafeSet linkFound, SafeSet linkExplored, SafeSet linkDown, int value) {
        SwingUtilities.invokeLater(() -> {
            this.areaOutput.append("Link Found: " + linkFound.size() + "\n");
            this.areaOutput.append("Link Explored: " + linkExplored.size() + "\n");
            this.areaOutput.append("Link Up: " + (linkExplored.size() - linkDown.size()) + "\n");
            this.areaOutput.append("Link Down: " + linkDown.size() + "\n");
            this.areaOutput.append("Total Occurrences: " + value + "\n");
            this.areaOutput.setCaretPosition(this.areaOutput.getDocument().getLength());
        });
    }
}
