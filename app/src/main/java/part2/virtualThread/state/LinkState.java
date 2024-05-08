package part2.virtualThread.state;

import part2.virtualThread.monitor.Monitor;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LinkState {

    private final Set<String> linkFound = new HashSet<>();
    private final Set<String> linkExplored = new HashSet<>();
    private final Set<String> linkDown = new HashSet<>();
    private final Monitor monitor;

    public LinkState(String url, Monitor monitor) {
        this.linkFound.add(url);
        this.monitor = monitor;
    }

    public Set<String> getLinkFound() {
        return monitor.lock(() -> Collections.unmodifiableSet(this.linkFound));
    }

    public Set<String> getLinkExplored() {
        return monitor.lock(() -> Collections.unmodifiableSet(this.linkExplored));
    }

    public Set<String> getLinkDown() {
        return monitor.lock(() -> Collections.unmodifiableSet(this.linkDown));
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
