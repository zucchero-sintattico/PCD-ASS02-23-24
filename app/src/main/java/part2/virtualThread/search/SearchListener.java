package part2.virtualThread.search;

import java.io.IOException;

public interface SearchListener {
    void pageFound(String pageUrl);
    void pageRequested(String pageUrl);
    void pageDown(IOException exception, String pageUrl);
    void countUpdated(int wordFound, String pageUrl);
}
