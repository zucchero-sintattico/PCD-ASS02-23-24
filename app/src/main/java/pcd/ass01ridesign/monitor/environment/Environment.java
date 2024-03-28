package pcd.ass01ridesign.monitor.environment;

import pcd.ass01ridesign.monitor.agent.AbstractCarAgent;
import pcd.ass01ridesign.monitor.agent.action.Action;
import pcd.ass01ridesign.monitor.agent.perception.Percept;

public interface Environment {

    //todo refactor
    void registerNewCar(AbstractCarAgent abstractAgent, Road road, double initialPos);

    Percept getCurrentPercept(String agentID);

    void doAction(String agentID, Action selectedAction);
}
