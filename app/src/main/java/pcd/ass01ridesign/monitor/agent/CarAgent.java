package pcd.ass01ridesign.monitor.agent;


import pcd.ass01ridesign.monitor.agent.task.ParallelTask;
import pcd.ass01ridesign.monitor.agent.task.SerialTask;
import pcd.ass01ridesign.monitor.environment.Environment;

public interface CarAgent {

    ParallelTask getParallelAction();

    SerialTask getSerialAction();

    String getAgentID();

    Environment getAgentEnvironment();

    double getCurrentSpeed();
}
