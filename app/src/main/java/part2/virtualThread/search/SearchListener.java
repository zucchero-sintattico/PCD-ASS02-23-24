package part2.virtualThread.search;

import part2.virtualThread.view.SearchInfo;

import java.util.Set;

public interface SearchListener {

    void searchStarted();

    void searchEnded(int linkFound, int linkDown, SearchInfo info);

}
