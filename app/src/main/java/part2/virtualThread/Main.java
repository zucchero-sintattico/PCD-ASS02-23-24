package part2.virtualThread;

import part2.virtualThread.search.SearchController;
import part2.virtualThread.utils.Configuration;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
        Configuration.setup();
        new GUI().display();
    }
}
