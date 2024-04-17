package part2.virtualThread.monitor;

import java.util.HashSet;
import java.util.Set;

public class SafeSet {
    private final Set<String> set = new HashSet<>();
    public SafeSet() {}
    public SafeSet(String url) {this.add(url);}
    public synchronized void add(String value) {this.set.add(value);}
    public synchronized boolean contains(String value) {
        return this.set.contains(value);
    }
    public synchronized int size() {
        return this.set.size();
    }
    public synchronized String toString() {
        return this.set.toString();
    }
}
