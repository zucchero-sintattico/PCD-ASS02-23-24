package part2.virtualThread.monitor;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SafeSet {

    private final Lock lock = new ReentrantLock();

    private final Set<String> set = new HashSet<>();

    public SafeSet() {}

    public SafeSet(String url) {this.add(url);}

    public void add(String value) {
        try {
            lock.lock();
            this.set.add(value);
        } finally {
            lock.unlock();
        }
    }

    public boolean contains(String value) {
        try {
            lock.lock();
            return this.set.contains(value);
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        try {
            lock.lock();
            return this.set.size();
        } finally {
            lock.unlock();
        }
    }

    public String toString() {
        try {
            lock.lock();
            return this.set.toString();
        } finally {
            lock.unlock();
        }
    }

}
