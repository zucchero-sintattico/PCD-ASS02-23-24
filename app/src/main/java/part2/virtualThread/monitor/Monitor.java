package part2.virtualThread.monitor;

import java.util.concurrent.Callable;

public interface Monitor {

    <T> T lock(Callable<T> function);

    void lock(Runnable function);

}
