package part2.virtualThreadFuture.monitor;

import java.util.concurrent.Callable;

public interface Monitor {

    <T> T lock(Callable<T> function);

    void lock(Runnable function);

}
