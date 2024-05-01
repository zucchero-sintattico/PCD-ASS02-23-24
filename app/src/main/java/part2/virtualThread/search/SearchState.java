package part2.virtualThread.search;

import part2.virtualThread.monitor.Monitor;
import part2.virtualThread.monitor.StatefulMonitor;
import part2.virtualThread.view.SearchInfo;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SearchState {

    private final Monitor monitor = new StatefulMonitor(this::onUpdate);

    private final Set<String> linkFound = new HashSet<>();
    private final Set<String> linkExplored = new HashSet<>();
    private final Set<String> linkDown = new HashSet<>();
    private final Set<String> threadAlive = new HashSet<>();
    private int wordOccurrences = 0;


    private final StringBuilder allLog = new StringBuilder();
    private final StringBuilder newLog = new StringBuilder();

    private boolean searchEnded = false;
    private SearchListener listener;

    private final BlockingQueue<SearchInfo> searchInfoQueue = new ArrayBlockingQueue<>(1);

    public SearchState(String url) {
        this.linkFound.add(url);
    }


    protected void onUpdate() {
       SearchInfo info = new SearchInfo(linkExplored.size(), wordOccurrences, threadAlive.size(), getNewLog());
       searchInfoQueue.offer(info);
    }

    public Set<String> getLinkFound() {
        return monitor.lock(() -> Collections.unmodifiableSet(this.linkFound));
    }

    public String getNewLog() {
        return monitor.lock(() -> {
            String log = newLog.toString();
            newLog.setLength(0);
            return log;
        });
    }

    public Set<String> getLinkExplored() {
        return monitor.lock(() -> Collections.unmodifiableSet(this.linkExplored));
    }

    public Set<String> getLinkDown() {
        return monitor.lock(() -> Collections.unmodifiableSet(this.linkDown));
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

    public void log(String s) {
        monitor.lock(() -> {
            this.allLog.append(s);
            this.newLog.append(s);
        });
    }

    public void updateWordOccurrences(int count) {
        monitor.lock(() -> this.wordOccurrences += count);
    }

    public Optional<SearchListener> getListener() {
        return monitor.lock(() -> this.listener != null ? Optional.of(this.listener) : Optional.empty());
    }

    public void stopSimulation() {
        monitor.lock(() -> this.searchEnded = true);
    }

    public Optional<SearchInfo> getSearchInfo() {
        //TODO not monitored
        try {
            return Optional.ofNullable(searchInfoQueue.poll());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void addLinkDown(String urlString) {
        monitor.lock(() -> this.linkDown.add(urlString));
    }

    public void removeThreadAlive(String urlString) {
        monitor.lock(() -> this.threadAlive.remove(urlString));
    }

    public void addLinkExplored(String urlString) {
        monitor.lock(() -> this.linkExplored.add(urlString));
    }

    public void addLinkFound(String line) {
        monitor.lock(() -> this.linkFound.add(line));
    }




}
