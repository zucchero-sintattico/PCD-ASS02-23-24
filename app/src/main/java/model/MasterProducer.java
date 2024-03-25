package model;

import pcd.ass01.simengineseq.AbstractAgent;

import java.util.ArrayList;
import java.util.List;


public class MasterProducer {

    int numOfThread;

    int numOfStep;

    List<Runnable> listOFAgent;

    List<Integer> splitIndex = new ArrayList<>();


    public MasterProducer(int numOfThread, List<AbstractAgent> listOFAgent, int numOfStep){
        this.numOfThread = numOfThread;
        this.listOFAgent = new ArrayList<>(listOFAgent);
        this.numOfStep = numOfStep;


        int size = listOFAgent.size();
        int split = size/numOfThread;
        for(int i = 1; i < numOfThread + 1 ; i++){
            splitIndex.add(i*split + size%numOfThread);
        }

        for (int i = 0; i < numOfThread; i++) {
            int start = i == 0 ? 0 : splitIndex.get(i-1);
            int end = splitIndex.get(i);
            List<Runnable> subList = this.listOFAgent.subList(start, end);
            new Thread(() -> {
                for (int j = 0; j < numOfStep; j++) {
                    for (Runnable agent : subList) {
                        agent.run();
                    }
                }
            }).start();

        }


    }







}
