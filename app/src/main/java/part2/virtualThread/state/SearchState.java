package part2.virtualThread.state;

import part2.virtualThread.monitor.Monitor;
import part2.virtualThread.monitor.StatefulMonitor;
import part2.virtualThread.search.SearchListener;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SearchState {

    private final Monitor monitor = new StatefulMonitor(this::onUpdate);
    private final LinkState linkState;
    private final List<String> threadAlive = new ArrayList<>();
    private int wordOccurrences = 0;
    private final LogBuffer logs = new LogBuffer(monitor);
    private boolean searchEnded = false;
    private List<SearchListener> listener = new ArrayList<>();
    private final BlockingQueue<SearchReport> searchInfoQueue = new ArrayBlockingQueue<>(1);

    public SearchState(String url) {
        this.linkState = new LinkState(url, monitor);
    }

    protected void onUpdate() {
       SearchReport info = new SearchReport(linkState.getLinkExplored().size(), wordOccurrences, threadAlive.size(), logs);
       searchInfoQueue.offer(info);
    }

    public int getWordOccurrences() {
        return monitor.lock(() -> this.wordOccurrences);
    }

    public void updateWordOccurrences(int count) {
        monitor.lock(() -> this.wordOccurrences += count);
    }

    public void addThreadAlive(String urlString) {
        monitor.lock(() -> this.threadAlive.add(urlString));
    }

    public List<String> getThreadAlive() {
        return monitor.lock(() -> Collections.unmodifiableList(this.threadAlive));
    }

    public void removeThreadAlive(String urlString) {
        monitor.lock(() -> this.threadAlive.remove(urlString));
    }

    public void setListener(SearchListener listener) throws IllegalArgumentException {
        if(listener == null) throw new IllegalArgumentException("Listener cannot be null");
        monitor.lock(() -> this.listener.add(listener));
    }

    public List<SearchListener> removeListener() {
        return monitor.lock(() -> {
            List<SearchListener> listener = this.listener;
            this.listener = new ArrayList<>();
            return listener;
        });
    }

    public boolean isSimulationRunning() {
        return monitor.lock(() -> !this.searchEnded);
    }

    public void stopSimulation() {
        monitor.lock(() -> this.searchEnded = true);
    }

    public Optional<SearchReport> getSearchInfo() {
        try {
            return Optional.ofNullable(searchInfoQueue.poll());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public LinkState getLinkState() {
        return this.linkState;
    }

    public LogBuffer getLogs() {
        return logs;
    }

}
