package part2.virtualThread.search;

import part2.virtualThread.state.SearchReport;
import java.util.Optional;

public interface SearchController {

    void start(String address, String word, int depth);

    void stop();

    void bruteStop();

    Optional<SearchReport> getSearchInfo();

}
