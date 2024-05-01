package part2.virtualThread.monitor;

import part2.virtualThread.view.SearchInfo;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StatefulMonitor implements Monitor {

    private final Lock lock = new ReentrantLock();
    protected final AtomicBoolean updateState = new AtomicBoolean(true);
    private final Runnable onUpdate;

    public StatefulMonitor(Runnable onUpdate) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateState.set(true);
            }
        }, 0, 8);
        this.onUpdate = onUpdate;
    }

    public <T> T lock(Callable<T> function) {
        try {
            this.lock.lock();
            T result = function.call();
            this.update();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void lock(Runnable function) {
        try {
            this.lock.lock();
            function.run();
            this.update();
        } finally {
            lock.unlock();
        }
    }

    private void update() {
        if (updateState.compareAndSet(true, false)){
            onUpdate.run();
        }
    }

}
