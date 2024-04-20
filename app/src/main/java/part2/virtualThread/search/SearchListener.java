package part2.virtualThread.search;

import part2.virtualThread.monitor.SafeCounter;
import part2.virtualThread.monitor.SafeSet;

public interface SearchListener {
    void pageFound(String pageUrl);
    void pageRequested(String pageUrl, SafeSet totalPageRequested);
    void pageDown(String exceptionMessage, String pageUrl);
    void countUpdated(int wordFound, String pageUrl, SafeCounter totalWordFound);
    void searchEnded(SafeSet linkFound, SafeSet linkExplored, SafeSet linkDown, SafeCounter wordFound);
    void threadAliveUpdated(SafeCounter treadAlive);
}
