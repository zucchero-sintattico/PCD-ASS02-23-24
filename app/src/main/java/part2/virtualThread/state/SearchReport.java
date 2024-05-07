package part2.virtualThread.state;

import part2.virtualThread.monitor.Monitor;
import part2.virtualThread.monitor.StatefulMonitor;
import part2.virtualThread.search.SearchListener;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SearchReport {

    private final Monitor monitor = new StatefulMonitor(this::onUpdate);

    private final LinkState linkState;

    private final Set<String> threadAlive = new HashSet<>();

    private int wordOccurrences = 0;

    private final LogBuffer logs = new LogBuffer(monitor);

    private boolean searchEnded = false;

    private SearchListener listener;

    private final BlockingQueue<SearchInfo> searchInfoQueue = new ArrayBlockingQueue<>(1);

    public SearchReport(String url) {
        this.linkState = new LinkState(url, monitor);
    }

    protected void onUpdate() {
       SearchInfo info = new SearchInfo(linkState.getLinkExplored().size(), wordOccurrences, threadAlive.size(), logs);
       searchInfoQueue.offer(info);
    }

    public int getWordOccurrences() {
        return monitor.lock(() -> this.wordOccurrences);
    }

    public Set<String> getThreadAlive() {
        return monitor.lock(() -> Collections.unmodifiableSet(this.threadAlive));
    }

    public void setListener(SearchListener listener) {
        monitor.lock(() -> this.listener = listener);
    }

    public void addThreadAlive(String urlString) {
        monitor.lock(() -> this.threadAlive.add(urlString));
    }

    public boolean isSimulationRunning() {
        return monitor.lock(() -> !this.searchEnded);
    }

    public void updateWordOccurrences(int count) {
        monitor.lock(() -> this.wordOccurrences += count);
    }

    public void stopSimulation() {
        monitor.lock(() -> this.searchEnded = true);
    }

    public Optional<SearchInfo> getSearchInfo() {
        try {
            return Optional.ofNullable(searchInfoQueue.poll());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public LinkState getLinkState() {
        return this.linkState;
    }

    public void removeThreadAlive(String urlString) {
        monitor.lock(() -> this.threadAlive.remove(urlString));
    }

    public LogBuffer getLogs() {
        return logs;
    }

    public Optional<SearchListener> removeListener() {
        return monitor.lock(() -> {
            SearchListener listener = this.listener;
            this.listener = null;
            return Optional.ofNullable(listener);
        });
    }
}