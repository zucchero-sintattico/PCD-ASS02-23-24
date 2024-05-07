package part2.virtualThread.monitor;

import part2.virtualThread.utils.Configuration;

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

    private boolean updating = false;

    public StatefulMonitor(Runnable onUpdate) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateState.set(true);
            }
        }, 0, Configuration.STATE_UPDATE_MS);
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
        if (!updating && updateState.compareAndSet(true, false)){
            updating = true;
            onUpdate.run();
            updating = false;
        }
    }

}
