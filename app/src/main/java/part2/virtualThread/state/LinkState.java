package part2.virtualThread.state;

import part2.virtualThread.monitor.Monitor;

import java.util.*;

public class LinkState {

    private final List<String> linkFound = new ArrayList<>();
    private final List<String> linkExplored = new ArrayList<>();
    private final List<String> linkDown = new ArrayList<>();
    private final Monitor monitor;

    public LinkState(String url, Monitor monitor) {
        this.linkFound.add(url);
        this.monitor = monitor;
    }

    public List<String> getLinkFound() {
        return monitor.lock(() -> Collections.unmodifiableList(this.linkFound));
    }

    public List<String> getLinkExplored() {
        return monitor.lock(() -> Collections.unmodifiableList(this.linkExplored));
    }

    public List<String> getLinkDown() {
        return monitor.lock(() -> Collections.unmodifiableList(this.linkDown));
    }

    public void addLinkDown(String urlString) {
        monitor.lock(() -> this.linkDown.add(urlString));
    }

    public void addLinkExplored(String urlString) {
        monitor.lock(() -> this.linkExplored.add(urlString));
    }

    public void addLinkFound(String line) {
        monitor.lock(() -> this.linkFound.add(line));
    }


}
