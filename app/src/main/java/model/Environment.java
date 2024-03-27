package model;

import java.util.*;

public class Environment {

    private final List<Agent> agents;
    private int n;
    boolean timeToWrite;

    public Environment(){
        this.agents = new ArrayList<>();
    }

    public void addAgent(Agent agent){
        this.agents.add(agent);
    }

    private synchronized void _senseEntrance(Agent agent) throws InterruptedException {
        while(timeToWrite){
            wait();
        }
    }
    private synchronized void _senseExit(Agent agent) throws InterruptedException {
        n++;
        if(n == agents.size()){
            timeToWrite = true;
            n = 0;
            notifyAll();
        }
    }

    private synchronized void _actEntrance(Agent agent) throws InterruptedException {
        while(!timeToWrite){
            wait();
        }
    }
    private synchronized void _actExit(Agent agent) throws InterruptedException {
        n++;
        if(n == agents.size()){
            timeToWrite = false;
            n = 0;
            notifyAll();
        }
    }

    public void sense(Agent agent) throws InterruptedException {
        _senseEntrance(agent);
        System.out.println("agent " + agent.id + " sensed++++++++++++++++++++++++++++++++++++++++++++++++");
        _senseExit(agent);
    }

    public void act(Agent agent) throws InterruptedException {
        _actEntrance(agent);
        System.out.println("agent " + agent.id + " acted");
        _actExit(agent);
    }

//    public void decide(Agent agent) {
//        System.out.println("agent " + agent.id + " decided-------");
//    }
}
