package part1.logic.simulation.examples;

import part1.logic.passiveComponent.environment.Environment;
import part1.logic.passiveComponent.environment.RoadsEnvironment;
import part1.logic.simulation.AbstractSimulation;

public abstract class AbstractTrafficSimulationExample extends AbstractSimulation {

    @Override
    protected Environment createEnvironment() {
        return new RoadsEnvironment();
    }

    @Override
    protected int setDelta() {
        return 1;
    }

    @Override
    protected int setInitialCondition() {
        return 0;
    }

}
