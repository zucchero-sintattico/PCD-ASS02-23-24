package part2.virtualThread.search;

import part2.virtualThread.monitor.SafeCounter;
import part2.virtualThread.monitor.SafeSet;

public interface SearchListener {
    void searchStarted();
    void searchEnded(SafeSet linkFound, SafeSet linkExplored, SafeSet linkDown, SafeCounter wordFound);
}
