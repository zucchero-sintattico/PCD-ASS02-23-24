package part2.virtualThread;

import part2.virtualThread.view.GUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Configuration.setup();
        SwingUtilities.invokeLater(() -> {
            new GUI().display();
        });
    }
}
