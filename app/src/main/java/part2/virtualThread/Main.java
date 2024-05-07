package part2.virtualThread;

import part2.virtualThread.utils.Configuration;
import part2.virtualThread.view.GUI;

public class Main {
    public static void main(String[] args) {
        Configuration.setup();
        new GUI().display();
    }
}
