package model.passiveComponent.simulation;

import model.activeComponent.SimulationWorker;
import model.passiveComponent.agent.task.ParallelTask;
import model.monitor.barrier.CyclicBarrier;

import java.util.ArrayList;
import java.util.List;

public class MasterWorkerHandler {

    // private List<Runnable> listOFAgent;

    public MasterWorkerHandler(int numOfThread, List<ParallelTask> listOFAgent, int numOfStep, CyclicBarrier barrier,
            CyclicBarrier barrier2) {

        // this.listOFAgent = new ArrayList<>(listOFAgent);

        int size = listOFAgent.size();
        int split = size / numOfThread;
        List<Integer> splitIndex = new ArrayList<>();
        for (int i = 1; i < numOfThread + 1; i++) {
            splitIndex.add(i * split + size % numOfThread);
        }

        for (int i = 0; i < numOfThread; i++) {
            int start = i == 0 ? 0 : splitIndex.get(i - 1);
            int end = splitIndex.get(i);
            List<ParallelTask> subList = listOFAgent.subList(start, end);
            new SimulationWorker(subList, numOfStep, barrier, barrier2).start();

        }

    }

}
