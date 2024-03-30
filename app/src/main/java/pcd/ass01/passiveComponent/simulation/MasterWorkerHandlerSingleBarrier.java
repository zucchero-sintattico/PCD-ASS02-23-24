package pcd.ass01.passiveComponent.simulation;

import pcd.ass01.activeComponent.SimulationWorkerSingleBarrier;
import pcd.ass01.monitor.barrier.CyclicBarrier;
import pcd.ass01.passiveComponent.agent.task.ParallelTask;

import java.util.ArrayList;
import java.util.List;

public class MasterWorkerHandlerSingleBarrier {

    public MasterWorkerHandlerSingleBarrier(int numOfThread, List<ParallelTask> listOFAgent, int numOfStep, CyclicBarrier barrier){

//        this.listOFAgent = new ArrayList<>(listOFAgent);

        int size = listOFAgent.size();
        int split = size/numOfThread;
        List<Integer> splitIndex = new ArrayList<>();
        for(int i = 1; i < numOfThread + 1 ; i++){
            splitIndex.add(i*split + size%numOfThread);
        }

        for (int i = 0; i < numOfThread; i++) {
            int start = i == 0 ? 0 : splitIndex.get(i-1);
            int end = splitIndex.get(i);
            List<ParallelTask> subList = listOFAgent.subList(start, end);
            new SimulationWorkerSingleBarrier(subList,numOfStep,barrier).start();

        }


    }


}
