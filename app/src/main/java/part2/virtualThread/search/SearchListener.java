package part2.virtualThread.search;

import part2.virtualThread.state.SearchReport;

public interface SearchListener {

    void searchStarted();

    void searchEnded(int linkFound, int linkDown, SearchReport info);

}
