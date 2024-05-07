package part2.virtualThread.state;

import part2.virtualThread.monitor.Monitor;
import part2.virtualThread.utils.Configuration;

import java.util.Map;

public class LogBuffer {

    private final StringBuilder allLog = new StringBuilder();
    private final StringBuilder newLog = new StringBuilder();

    private final Monitor monitor;
    private final Map<LogType, Boolean> logPolicy;

    public LogBuffer(Monitor monitor){
        this.monitor = monitor;
        this.logPolicy = Configuration.getLogPolicy();
    }

    public void append(String log, LogType type){
        if(logPolicy.containsKey(type) && logPolicy.get(type)){
            monitor.lock(() -> {allLog.append(log);newLog.append(log);});
        }
    }

    public String getNewLogAndReset(){
        return monitor.lock(() -> {
            String log = newLog.toString();
            newLog.setLength(0);
            return log;
        });
    }

    public String getAllLog(){
        return monitor.lock(allLog::toString);
    }

}
