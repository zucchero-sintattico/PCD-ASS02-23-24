package part2.rx;

import part2.rx.view.GUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            new GUI().setVisible(true);
        });
    }
}
