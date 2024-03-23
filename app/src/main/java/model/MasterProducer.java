package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MasterProducer {

    int numOfThread;

    List<Runnable> listOFAgent = new ArrayList<>();

    List<Integer> splitIndex = new ArrayList<>();
    public MasterProducer(int numOfThread, List<Runnable> listOFAgent){
        this.numOfThread = numOfThread;
        this.listOFAgent = listOFAgent;


        this.splitIndex = Stream.iterate(0, i -> i + listOFAgent.size()/numOfThread).toList();
        if (listOFAgent.size()%numOfThread != 0){
            this.splitIndex.set(0, this.splitIndex.get(0) + listOFAgent.size()%numOfThread);
        }

    }




}
