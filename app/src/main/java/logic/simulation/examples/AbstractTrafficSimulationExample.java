package logic.simulation.examples;

import logic.passiveComponent.environment.Environment;
import logic.passiveComponent.environment.RoadsEnvironment;
import logic.simulation.AbstractSimulation;

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
