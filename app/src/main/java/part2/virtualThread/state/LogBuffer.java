package part2.virtualThread.state;

import part2.virtualThread.monitor.Monitor;

import java.util.concurrent.atomic.AtomicReference;

public class LogBuffer {

    private final StringBuilder allLog = new StringBuilder();
    private final StringBuilder newLog = new StringBuilder();

    private final Monitor monitor;

    public LogBuffer(Monitor monitor){
        this.monitor = monitor;
    }

    public void append(String log){
        monitor.lock(() -> {allLog.append(log);newLog.append(log);});
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
