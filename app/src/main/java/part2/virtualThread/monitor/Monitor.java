package part2.virtualThread.monitor;

import java.util.concurrent.Callable;

public interface Monitor {

    void lock(Runnable function);

    <T> T lock(Callable<T> function);

}
