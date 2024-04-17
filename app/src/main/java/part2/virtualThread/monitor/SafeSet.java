package part2.virtualThread.monitor;

import java.util.HashSet;
import java.util.Set;

public class SafeSet {
    private final Set<String> foundset = new HashSet<>();

    public SafeSet(String url) {this.add(url);}
    public synchronized void add(String value) {
        foundset.add(value);
    }
    public synchronized boolean contains(String value) {
        return foundset.contains(value);
    }
    public synchronized int size() {
        return foundset.size();
    }
    public synchronized String toString() {
        return foundset.toString();
    }
}
