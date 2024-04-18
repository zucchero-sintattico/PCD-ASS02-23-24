package part2.virtualThread.search;

import part2.virtualThread.monitor.SafeSet;

public interface SearchListener {
    void pageFound(String pageUrl);
    void pageRequested(String pageUrl);
    void pageDown(String exceptionMessage, String pageUrl);
    void countUpdated(int wordFound, String pageUrl);
    void searchEnded(SafeSet linkFound, SafeSet linkExplored, SafeSet linkDown, int value);
}
