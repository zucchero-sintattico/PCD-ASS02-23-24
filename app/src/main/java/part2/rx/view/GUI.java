package part2.rx.view;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import part2.rx.controller.SearchController;
import part2.rx.model.SearchReport;
import part2.virtualThread.view.SearchInfo;
import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private final static int DEFAULT_SIZE = 700;
    private JLabel labelAddress;
    private JLabel labelWord;
    private JLabel labelDepth;
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
    private JPanel container;
    private JPanel logContainer;
    private JPanel buttonContainer;
    private SearchController controller = new SearchController();

    public GUI(){
        super();

        this.setFrameProperties();

        this.setViewComponents();

        this.addAllComponentsIntoFrame();

        this.editAllComponentsProperties();

        this.addListeners();

        //temp setting
        this.fieldAddress.setText("https://www.google.com/");
        this.fieldWord.setText("google");
        this.fieldDepth.setText("3");
    }

    private void addListeners() {
        this.buttonStart.addActionListener(e -> {
            String address = this.fieldAddress.getText();
            String word = this.fieldWord.getText();
            int depth = Integer.parseInt(this.fieldDepth.getText());
            this.controller.attachObserver(new Observer<SearchReport>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull SearchReport r) {
                    updateGUI("[Link]: " + r.url() + "\n--->[Word count]: " + r.wordFind() + "\n--->[Depth]: " + r.depth());
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    updateGUI("[Error]: " + e.getMessage());
                }

                @Override
                public void onComplete() {
                    buttonStart.setEnabled(true);
                    buttonStop.setEnabled(false);
                }
            });
            this.buttonStart.setEnabled(false);
            this.controller.wordCount(address, word, depth);
        });

        this.buttonStop.addActionListener(e -> {
            this.buttonStop.setEnabled(false);
        });
    }

    private void updateGUI(String message){
        SwingUtilities.invokeLater(() -> {
            this.areaOutput.setText(message);
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
        this.container = new JPanel(new GridLayout(2, 3, 16, 4));
        this.logContainer = new JPanel(new GridLayout(1, 6, 16, 4));
        this.buttonContainer = new JPanel(new GridLayout(1, 2, 16, 16));
        this.container.add(this.labelAddress);
        this.container.add(this.labelWord);
        this.container.add(this.labelDepth);
        this.container.add(this.fieldAddress);
        this.container.add(this.fieldWord);
        this.container.add(this.fieldDepth);
        this.logContainer.add(this.labelLinkRequestedCount);
        this.logContainer.add(this.labelWordFoundCount);
        this.logContainer.add(this.labelThreadAliveCount);
        this.buttonContainer.add(this.buttonStart);
        this.buttonContainer.add(this.buttonStop);
    }

    private void setFrameProperties() {
        this.setLayout(new GridBagLayout());
        this.setTitle("Web Word Counter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(DEFAULT_SIZE, DEFAULT_SIZE);
        this.setLocationRelativeTo(null);
    }

    public void display(){
        SwingUtilities.invokeLater(() -> this.setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUI().setVisible(true);
        });
    }


}
