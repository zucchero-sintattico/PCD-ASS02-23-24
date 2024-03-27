package mvc;

import mvc.controller.Controller;
import mvc.view.StatisticalView;
import pcd.ass01.simtrafficexamples.TrafficSimulationSingleRoadTwoCars;

public class TestGUI {
    public static void main(String[] args) {
        TrafficSimulationSingleRoadTwoCars simulation = new TrafficSimulationSingleRoadTwoCars();
        simulation.setup();
        StatisticalView view = new StatisticalView();
        Controller controller = new Controller(view, simulation);
        controller.displayView();
    }
}
