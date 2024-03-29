package pcd.ass01ridesign.passiveComponent.simulation;

import pcd.ass01ridesign.monitor.barrier.CyclicBarrier;
import pcd.ass01ridesign.monitor.state.SimulationState;
import pcd.ass01ridesign.passiveComponent.agent.AbstractCarAgent;
import pcd.ass01ridesign.passiveComponent.environment.Environment;
import pcd.ass01ridesign.passiveComponent.simulation.listeners.SimulationListener;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSimulation implements Simulation{

    private final SimulationState state;
    protected List<AbstractCarAgent> agents;
    private final List<SimulationListener> listeners = new ArrayList<>();
    private boolean toBeInSyncWithWallTime;
    protected Environment environment;

    /* logical time step */
    private int dt;

    /* initial logical time */
    private int t0;
    private int t;
    private long currentWallTime;
    private long cumulativeTimePerStep;
    private int nStepsPerSec;

    private int numSteps;
    private int numStepDone;

    private CyclicBarrier barrier1, barrier2;
    private long startWallTime;
    private long averageTimePerStep;
    private long endWallTime;

    public AbstractSimulation(){
        this.state = new SimulationState();
    }

    @Override
    public void setup(int numSteps, int numOfThread) {
        this.dt = this.setDelta();
        this.t = this.t0 = this.setInitialCondition();
        this.setupComponents();
        this.setupSimulation(numSteps, numOfThread);
    }

    private void setupSimulation(int numSteps, int numOfThread) {
        this.numSteps = numSteps;
        this.startWallTime = System.currentTimeMillis();
        this.barrier1 = new CyclicBarrier(numOfThread + 1);
        this.barrier2 = new CyclicBarrier(numOfThread + 1);
        new MasterWorkerHandler(numOfThread, agents.stream().map(AbstractCarAgent::getParallelAction).toList(), numSteps, barrier1, barrier2);
        this.notifyReset(this.t0, this.agents, this.environment);
    }

    private void setupComponents() {
        environment = createEnvironment();
        environment.setup(dt);
        agents = createAgents();
        for (var agent: agents) {
             agent.setup(dt);
        }
    }

    protected abstract List<AbstractCarAgent> createAgents();
    protected abstract Environment createEnvironment();
    protected abstract int setDelta();
    protected abstract int setInitialCondition();

    @Override
    public void addSimulationListener(SimulationListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void doStep() {
        if(this.numStepDone < this.numSteps) {
            this.numStepDone++;
            this.currentWallTime = System.currentTimeMillis();

            //todo fix
            environment.step();
            try {
                this.barrier1.hitAndWaitAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (var tasks: agents.stream().map(AbstractCarAgent::getSerialAction).toList()) {
                tasks.run();
            }
            this.t += this.dt;
            this.notifyNewStep(this.t, this.agents, this.environment);
            cumulativeTimePerStep+= System.currentTimeMillis() - this.currentWallTime;
            if (this.toBeInSyncWithWallTime) {
                this.syncWithWallTime();
            }
            try {
                barrier2.hitAndWaitAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(this.numStepDone == this.numSteps) {
            this.endWallTime = System.currentTimeMillis();
            this.averageTimePerStep = cumulativeTimePerStep / numSteps;
            this.state.stopSimulation();
        }
    }

    @Override
    public long getSimulationDuration() {
        return (endWallTime - startWallTime);
    }

    @Override
    public long getAverageTimePerCycle() {
        return (averageTimePerStep);
    }

    @Override
    public void syncWithTime(int nCyclesPerSec) {
        this.toBeInSyncWithWallTime = true;
        this.nStepsPerSec = nCyclesPerSec;
    }

    private void syncWithWallTime() {
        try {
            long newWallTime = System.currentTimeMillis();
            long delay = 1000 / this.nStepsPerSec;
            long wallTimeDT = newWallTime - this.currentWallTime;
            if (wallTimeDT < delay) {
                Thread.sleep(delay - wallTimeDT);
            }
        } catch (Exception ex) {}
    }

    //todo refactor when merge
    private void notifyReset(int t0, List<AbstractCarAgent> agents, Environment env) {
        for (var l: listeners) {
            l.notifyInit(t0, agents, env);
        }
    }

    @Override
    public SimulationState getState(){
        return this.state;
    }

    private void notifyNewStep(int t, List<AbstractCarAgent> agents, Environment env) {
        for (var l: listeners) {
            l.notifyStepDone(t, agents, env);
        }
    }
}
