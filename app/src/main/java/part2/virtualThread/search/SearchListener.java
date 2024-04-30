package part2.virtualThread.search;

import part2.virtualThread.monitor.SafeCounter;
import part2.virtualThread.monitor.SafeSet;

import java.util.Set;

public interface SearchListener {
    void searchStarted();
    void searchEnded(Set<String> linkFound, Set<String> linkExplored, Set<String> linkDown, int wordFound);
}
