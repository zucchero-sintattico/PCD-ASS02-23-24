package part2.virtualThreadFuture.search;

import part2.virtualThreadFuture.state.SearchReport;

public interface SearchListener {

    void searchStarted();

    void searchEnded(int linkFound, int linkDown, SearchReport info);

}
