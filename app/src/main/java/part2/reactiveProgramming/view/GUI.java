package part2.reactiveProgramming.view;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.jetbrains.annotations.NotNull;
import part2.reactiveProgramming.controller.ControllerImpl;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class GUI extends JFrame implements Observer<Flowable<String>>{
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
    private ControllerImpl controller = new ControllerImpl();
    private boolean started = false;

    public GUI(){
        super();

        setFrameProperties();

        setViewComponents();

        addAllComponentsIntoFrame();

        editAllComponentsProperties();

        attachListeners();

        this.controller.attachSubscriberForNewLinks(this);
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

    public void attachListeners(){
        this.buttonStart.addActionListener(e -> {
            this.areaOutput.setText("");
            this.controller.start("https://en.wikipedia.org", "wikipedia", 1);
        });
    }

    public void updateGUI(String message){
        SwingUtilities.invokeLater(() -> {
            this.areaOutput.append(message + "\n");
            this.areaOutput.setCaretPosition(this.areaOutput.getDocument().getLength());
            this.started = true;
            this.toogleButton();
        });
    }

    private void toogleButton() {
        if(started){
            this.buttonStart.setEnabled(false);
        }else {
            this.buttonStart.setEnabled(true);
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull Flowable<String> stringFlowable) {
        stringFlowable.subscribe(element -> this.updateGUI("[New Link]" + element));
    }

    @Override
    public void onError(@NonNull Throwable e) {

    }

    @Override
    public void onComplete() {
        System.out.println("Complete");
    }
}
